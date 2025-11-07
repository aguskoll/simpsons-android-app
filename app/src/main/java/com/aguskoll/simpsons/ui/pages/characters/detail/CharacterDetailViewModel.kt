package com.aguskoll.simpsons.ui.pages.characters.detail

import androidx.lifecycle.viewModelScope
import com.aguskoll.domain.models.SimpsonCharacterDetail
import com.aguskoll.simpsons.ui.base.BaseViewModel
import com.aguskoll.simpsons.ui.base.UiState
import com.aguskoll.usecases.GetCharacterByIdUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CharacterDetailViewModel(private val getCharacterByIdUseCase: GetCharacterByIdUseCase) :
    BaseViewModel<CharacterDetailUiState, CharacterDetailEvent>(
        CharacterDetailUiState()
    ){
    fun getCharacterById(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            updateUiState { it.copy(isLoading = true) }
            val result = getCharacterByIdUseCase(id)
            result
                .onSuccess {
                    updateUiState { state -> state.copy(isLoading = false, character = it) }
                }
                .onFailure { e ->
                    updateUiState { state -> state.copy(isLoading = false) }
                    pushEvent(
                        CharacterDetailEvent.Error
                    )
                }
        }
    }
}

data class CharacterDetailUiState(
    val character: SimpsonCharacterDetail? = null,
    val isLoading: Boolean = false,
) : UiState

sealed class CharacterDetailEvent {
    object Error : CharacterDetailEvent()
}
