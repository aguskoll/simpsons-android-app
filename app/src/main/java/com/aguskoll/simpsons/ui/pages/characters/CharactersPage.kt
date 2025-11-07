package com.aguskoll.simpsons.ui.pages.characters

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aguskoll.domain.models.SimpsonCharacter
import org.koin.androidx.compose.koinViewModel

@Composable
fun CharactersPage() {
    val viewModel: CharactersViewModel = koinViewModel()
    val uiState by viewModel.uiStateFlow.collectAsStateWithLifecycle()
    val event by viewModel.eventFlow.collectAsStateWithLifecycle(null)

    LaunchedEffect(event) {
        when (event) {
            is CharactersEvent.NetworkError -> {

            }

            else -> {

            }
        }
    }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CharactersList(characters = uiState.characters)
        if (uiState.isLoading) {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun CharactersList(characters: List<SimpsonCharacter>) {
    LazyColumn {
        items(characters) { character ->
            Text(character.name, style = TextStyle(
                color = MaterialTheme.colorScheme.onSurface,
            ))
        }
    }
}


@Composable
@Preview
fun CharactersPreview(){
    CharactersList(
        characters = listOf(
            SimpsonCharacter(
                id = 1,
                name = "Homer Simpson",
                portraitPath = "https://upload.wikimedia.org/wikipedia/en/0/02/Homer_Simpson_2006.png",
            )
        )
    )
}