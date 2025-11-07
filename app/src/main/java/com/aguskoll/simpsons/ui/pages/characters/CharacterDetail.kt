package com.aguskoll.simpsons.ui.pages.characters

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.aguskoll.domain.models.SimpsonCharacter

@Composable
fun CharacterDetailPage(characterId: String) {
    Column() {
        Text(characterId)
    }
}

@Composable
fun HeroImage(character: SimpsonCharacter) {
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