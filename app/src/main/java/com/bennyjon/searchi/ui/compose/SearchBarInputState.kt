package com.bennyjon.searchi.ui.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

class SearchBarInputState(val placeholder: String, initialText: String) {

    var text by mutableStateOf(initialText)

    companion object {
        val Saver: Saver<SearchBarInputState, *> = listSaver(
            save = { listOf(it.placeholder, it.text) },
            restore = {
                SearchBarInputState(placeholder = it[0], initialText = it[1])
            }
        )
    }
}

@Composable
fun rememberSearchBarInputState(placeholder: String, initialText: String): SearchBarInputState =
    rememberSaveable(key = placeholder, saver = SearchBarInputState.Saver) {
        SearchBarInputState(placeholder = placeholder, initialText = initialText)
    }
