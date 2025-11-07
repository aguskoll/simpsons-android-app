package com.aguskoll.simpsons.ui.pages.characters

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
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
    LazyRow(
        modifier = Modifier
            .testTag("CHARACTERS_LIST")
            .fillMaxWidth()
    ) {
        items(characters) { character ->
            CharacterItem(character)
        }
    }
}

@Composable
fun CharacterItem(character: SimpsonCharacter) {
    Card(
        modifier = Modifier
            .testTag("CHARACTER_ITEM")
            .width(width = 250.dp)
            .height(height = 300.dp)
            .padding(horizontal = 16.dp)

    ) {
        Box {
            AsyncImage(
                model = character.portraitPath,
                contentDescription = character.name,
                modifier = Modifier
                    .fillMaxSize()
                    .testTag("CHARACTER_IMAGE")
                    .padding(4.dp)
            )
            // Gradient scrim between image and text to improve text visibility
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color(0xB8000000)
                            )
                        )
                    )
            )
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
            ) {
                Text(
                    character.name,
                    style = MaterialTheme.typography.headlineMedium.copy(
                        color = MaterialTheme.colorScheme.inversePrimary,
                        fontWeight = FontWeight.Bold
                    ),
                )
            }
        }
    }
}

@Composable
@Preview
fun CharactersPreview() {
    CharactersList(
        characters = listOf(
            SimpsonCharacter(
                id = 1,
                name = "Homer Simpson",
                portraitPath = "https://upload.wikimedia.org/wikipedia/en/0/02/Homer_Simpson_2006.png",
            ),
            SimpsonCharacter(
                id = 2,
                name = "Marge Simpson",
                portraitPath = "https://upload.wikimedia.org/wikipedia/en/0/02/Homer_Simpson_2006.png",
            )
        )
    )
}