package com.aguskoll.simpsons.ui.pages.characters.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.aguskoll.domain.models.SimpsonCharacter
import com.aguskoll.domain.models.SimpsonCharacterDetail
import org.koin.androidx.compose.koinViewModel

@Composable
fun CharacterDetailPage(characterId: String) {
    val viewModel: CharacterDetailViewModel = koinViewModel()
    val uiState by viewModel.uiStateFlow.collectAsStateWithLifecycle()
    val event by viewModel.eventFlow.collectAsStateWithLifecycle(null)

    LaunchedEffect(true) {
        viewModel.getCharacterById(characterId)
    }

    Column() {
        if (uiState.isLoading) {
            CircularProgressIndicator()
        }
        if(uiState.character != null) {
            HeroImage(character = uiState.character!!)
        }
    }
}

@Composable
fun HeroImage(character: SimpsonCharacterDetail) {
    Column {
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(height = 300.dp)) {
            AsyncImage(
                model = character.portraitPath,
                contentDescription = character.name,
                modifier = Modifier.fillMaxSize()
            )
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .height(height = 100.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color(0xB8000000)
                            )
                        )
                    )
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