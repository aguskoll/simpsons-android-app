package com.aguskoll.simpsons.ui.pages.characters.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.AssistChip
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
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
    Scaffold(modifier = Modifier.fillMaxSize().testTag("CHARACTER_DETAIL_PAGE")) {
        Column() {
            if (uiState.isLoading) {
                CircularProgressIndicator()
            }
            if(uiState.character != null) {
                HeroImage(character = uiState.character!!)
                CharacterDetail(character = uiState.character!!)
            }
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
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = MaterialTheme.colorScheme.inversePrimary,
                        fontWeight = FontWeight.Bold
                    ),
                )
            }
        }
    }
}

@Composable
fun CharacterDetail(character: SimpsonCharacterDetail) {
    Column {
        MetaChips(
            age = character.age,
            birthdate = character.birthdate,
            gender = character.gender,
            occupation = character.occupation,
            status = character.status
        )
        if (!character.occupation.isNullOrBlank()) {
            Spacer(Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Outlined.Info,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(Modifier.width(6.dp))
                Text(
                    text = character.occupation!!,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

    }
}

@Composable
private fun MetaChips(
    age: Int?,
    birthdate: String?,
    gender: String?,
    occupation: String?,
    status: String?
) {
    FlowRowMainAxisSpaced {
        if (age != null) MetaChip("Edad: $age")
        if (!birthdate.isNullOrBlank()) MetaChip("Nac.: $birthdate", Icons.Outlined.CalendarMonth)
        if (!gender.isNullOrBlank()) MetaChip("Género: $gender")
        if (!status.isNullOrBlank()) MetaChip("Estado: $status")
        if (!occupation.isNullOrBlank()) MetaChip("Rol: $occupation")
    }
}

@Composable
private fun MetaChip(text: String, icon: androidx.compose.ui.graphics.vector.ImageVector? = null) {
    AssistChip(
        onClick = {},
        label = { Text(text) },
        leadingIcon = icon?.let {
            { Icon(imageVector = it, contentDescription = null) }
        }
    )
}

@Composable
private fun FlowRowMainAxisSpaced(
    spacing: Dp = 8.dp,
    content: @Composable () -> Unit
) {
    // Implementación básica con Row + Wrap: alternativa liviana.
    // Si preferís algo más sofisticado, podés usar Accompanist FlowRow.
    Layout(content = content) { measurables, constraints ->
        val placeables = measurables.map { it.measure(constraints) }
        val maxWidth = constraints.maxWidth

        var x = 0
        var y = 0
        var rowHeight = 0
        val positions = mutableListOf<Pair<Int, Int>>()

        placeables.forEach { p ->
            if (x + p.width > maxWidth) {
                x = 0
                y += rowHeight + spacing.roundToPx()
                rowHeight = 0
            }
            positions += x to y
            x += p.width + spacing.roundToPx()
            rowHeight = maxOf(rowHeight, p.height)
        }

        val height = y + rowHeight
        layout(width = maxWidth, height = height) {
            placeables.forEachIndexed { i, p ->
                val (px, py) = positions[i]
                p.place(px, py)
            }
        }
    }
}