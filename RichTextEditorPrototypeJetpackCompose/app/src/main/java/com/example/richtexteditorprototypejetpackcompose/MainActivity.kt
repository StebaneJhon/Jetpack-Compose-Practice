package com.example.richtexteditorprototypejetpackcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.richtexteditorprototypejetpackcompose.ui.AppViewModel
import com.example.richtexteditorprototypejetpackcompose.ui.Card
import com.example.richtexteditorprototypejetpackcompose.ui.CardContent
import com.example.richtexteditorprototypejetpackcompose.ui.TextFormatStyle
import com.example.richtexteditorprototypejetpackcompose.ui.theme.RichTextEditorPrototypeJetpackComposeTheme
import com.mohamedrejeb.richeditor.model.RichTextState
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditor

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RichTextEditorPrototypeJetpackComposeTheme {

                val appViewModel: AppViewModel = viewModel()
                val uiState by appViewModel.uiState.collectAsStateWithLifecycle()

                val contentList = uiState.formingCard?.contentList ?: emptyList()

                val fieldStateList = rememberSaveable(
                    saver = Saver<SnapshotStateMap<String, RichTextState>, Any>(
                        save = { map ->
                            map.entries.map { (id, state) ->
                                listOf(id, state.toHtml(), state.selection.start, state.selection.end)
                            }
                        },
                        restore = { restored ->
                            val map = mutableStateMapOf<String, RichTextState>()
                            (restored as? List<List<Any>>)?.forEach { item ->
                                val id = item[0] as String
                                val html = item[1] as String
                                val selStart = item[2] as Int
                                val selEnd = item[3] as Int
                                map[id] = RichTextState().apply {
                                    setHtml(html)
                                    selection = TextRange(selStart, selEnd)
                                }
                            }
                            map
                        }
                    )
                ) {
                    mutableStateMapOf()
                }

                var focusedFieldId by remember { mutableStateOf<String?>(null) }
                val focusedState = focusedFieldId?.let { fieldStateList[it] }
                val currentSpanStyle = focusedState?.currentSpanStyle ?: SpanStyle()

                LaunchedEffect(contentList) {
                    // ADD new field
                    contentList.forEach { content ->
                        if (!fieldStateList.containsKey(content.id)) {
                            fieldStateList[content.id] = RichTextState().setHtml(content.text)
                        }
                    }
                    // Deleted old field
                    val currentIds = contentList.map { it.id }.toSet()
                    fieldStateList.keys.retainAll(currentIds)
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        MyBottomAppBar (
                            isBold = currentSpanStyle.fontWeight == FontWeight.Bold,
                            isItalic = currentSpanStyle.fontStyle == FontStyle.Italic,
                            isUnderlined = currentSpanStyle.textDecoration == TextDecoration.Underline || currentSpanStyle.textDecoration?.contains(TextDecoration.Underline) == true,
                            isStrikedThrought = currentSpanStyle.textDecoration == TextDecoration.LineThrough || currentSpanStyle.textDecoration?.contains(TextDecoration.LineThrough) == true,
                            onFormat = { textFormatStyle ->
                                focusedState?.let { state ->
                                    when (textFormatStyle) {
                                        TextFormatStyle.BOLD -> state.toggleSpanStyle(SpanStyle(fontWeight = FontWeight.Bold))
                                        TextFormatStyle.ITALIC -> state.toggleSpanStyle(SpanStyle(fontStyle = FontStyle.Italic))
                                        TextFormatStyle.UNDERLINE -> state.toggleSpanStyle(SpanStyle(textDecoration = TextDecoration.Underline))
                                        TextFormatStyle.STRIKETHROUGH -> state.toggleSpanStyle(SpanStyle(textDecoration = TextDecoration.LineThrough))
                                    }
                                }
                            },
                            onAdd = {
                                val currentContent = contentList.map { content ->
                                    val html = fieldStateList[content.id]?.toHtml() ?: ""
                                    CardContent(id = content.id, text = html)
                                }
                                val newCard = Card.generateCard(contentList = currentContent)
                                appViewModel.addCard(newCard)
                            }
                        )
                    }
                ) { innerPadding ->
                    Column (
                        modifier = Modifier
                            .padding(innerPadding)
                            .padding(16.dp)
                    ) {

                        LazyColumn(
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            items(items = uiState.cardList, key = {it.id}) { card ->
                                CardComponent(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    card = card,
                                )
                            }
                        }

                        contentList.forEach { content ->
                            fieldStateList[content.id]?.let { fieldState ->
                                RichTextEditor(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .onFocusChanged { focusState ->
                                            if (focusState.isFocused) {
                                                focusedFieldId = content.id
                                            }
                                        },
                                    state = fieldState,
                                )
                            }
                        }


                        TextButton(
                            onClick = {
                                appViewModel.addField()
                            }
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(painter = painterResource(R.drawable.ic_add), contentDescription = null)
                                Text(
                                    text = "More field",
                                    style = MaterialTheme.typography.bodySmall,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MyBottomAppBar(
    modifier: Modifier = Modifier,
    isBold: Boolean,
    isItalic: Boolean,
    isUnderlined: Boolean,
    isStrikedThrought: Boolean,
    onFormat: (TextFormatStyle) -> Unit,
    onAdd: () -> Unit,
) {
    BottomAppBar(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.background,
        actions = {
            IconButton(
                onClick = { onFormat(TextFormatStyle.BOLD) },
                colors = IconButtonDefaults.iconButtonColors(
                    contentColor = if (isBold) MaterialTheme.colorScheme.background else MaterialTheme.colorScheme.onSurface,
                    containerColor = if (isBold) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.background,
                )
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_format_bold),
                    contentDescription = null,
                )
            }
            IconButton(
                onClick = { onFormat(TextFormatStyle.ITALIC) },
                colors = IconButtonDefaults.iconButtonColors(
                    contentColor = if (isItalic) MaterialTheme.colorScheme.background else MaterialTheme.colorScheme.onSurface,
                    containerColor = if (isItalic) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.background,
                )
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_format_italic),
                    contentDescription = null
                )
            }
            IconButton(
                onClick = { onFormat(TextFormatStyle.UNDERLINE) },
                colors = IconButtonDefaults.iconButtonColors(
                    contentColor = if (isUnderlined) MaterialTheme.colorScheme.background else MaterialTheme.colorScheme.onSurface,
                    containerColor = if (isUnderlined) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.background,
                )
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_format_underlined),
                    contentDescription = null
                )
            }
            IconButton(
                onClick = { onFormat(TextFormatStyle.STRIKETHROUGH) },
                colors = IconButtonDefaults.iconButtonColors(
                    contentColor = if (isStrikedThrought) MaterialTheme.colorScheme.background else MaterialTheme.colorScheme.onSurface,
                    containerColor = if (isStrikedThrought) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.background,
                )
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_format_strikethrough),
                    contentDescription = null,
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAdd,
                containerColor = MaterialTheme.colorScheme.primary,
                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
            ) {
                Icon(painterResource(R.drawable.ic_add), "Localized description")
            }
        },
    )
}

@Composable
fun CardComponent(
    card: Card,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.padding(bottom = 16.dp)
    ) {
        Column(
            modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
        ) {
            card.contentList.forEach { content ->
                val annotatedString = AnnotatedString.fromHtml(content.text).trim()
                Text(
                    text = annotatedString,
                    modifier = modifier.padding(
                        bottom = 4.dp,
                        start = 12.dp,
                        end = 12.dp
                    )
                        .fillMaxWidth()
                )
            }
        }
    }
}

fun AnnotatedString.trim(): AnnotatedString {
    val startIndex = text.indexOfFirst { !it.isWhitespace() }
    val endIndex = text.indexOfLast { !it.isWhitespace() } + 1

    if (startIndex == -1 || startIndex >= endIndex) {
        return AnnotatedString("")
    }

    return this.subSequence(startIndex, endIndex)
}

@Preview(showBackground = true)
@Composable
fun CardComponentPreview1() {
    MaterialTheme() {
        CardComponent(
            card = Card(
                id = "aa",
                contentList = listOf(CardContent("00", "Content"))
            ),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CardComponentPreview() {
    MaterialTheme() {
        CardComponent(
            card = Card(
                id = "aa",
                contentList = listOf(CardContent("00", "Content"), CardContent("01", "Content 2"))
            ),
        )
    }
}
