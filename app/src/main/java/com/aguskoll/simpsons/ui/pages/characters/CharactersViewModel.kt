package com.aguskoll.simpsons.ui.pages.characters

import androidx.lifecycle.viewModelScope
import com.aguskoll.domain.models.SimpsonCharacter
import com.aguskoll.simpsons.ui.base.BaseViewModel
import com.aguskoll.simpsons.ui.base.UiState
import com.aguskoll.usecases.GetCharactersUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CharactersViewModel(val getCharactersUseCase: GetCharactersUseCase) :
    BaseViewModel<CharactersUiState, CharactersEvent>(CharactersUiState()){

    init {
        getCharacters()
    }

    private fun getCharacters() {
        viewModelScope.launch(Dispatchers.IO) {
            updateUiState { it.copy(isLoading = true) }
            val result = getCharactersUseCase()
            result
                .onSuccess {
                    updateUiState { state -> state.copy(isLoading = false, characters = it) }
                }
                .onFailure { e ->
                    updateUiState { state -> state.copy(isLoading = false) }
                    pushEvent(
                        CharactersEvent.NetworkError
                    )
                }
        }
    }

    fun onCharacterClick(character: SimpsonCharacter) {
        pushEvent(CharactersEvent.CharacterClick(character))
    }
}

data class CharactersUiState(
    val isLoading: Boolean = false,
    val characters: List<SimpsonCharacter> = emptyList(),
    val error: String? = null
) : UiState

sealed class CharactersEvent {
    object NetworkError : CharactersEvent()
    data class CharacterClick(val character: SimpsonCharacter) : CharactersEvent()
}
