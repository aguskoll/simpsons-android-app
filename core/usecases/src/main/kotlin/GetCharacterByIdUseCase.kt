package com.aguskoll.usecases

import com.aguskoll.domain.models.SimpsonCharacterDetail
import com.aguskoll.domain.repositories.CharactersRepository

class GetCharacterByIdUseCase(private val charactersRepository: CharactersRepository) {
    suspend operator fun invoke(id: String): Result<SimpsonCharacterDetail> {
        return try {
            charactersRepository.getCharacterById(id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}