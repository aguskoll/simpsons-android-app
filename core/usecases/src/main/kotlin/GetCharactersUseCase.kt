
package com.aguskoll.usecases

import com.aguskoll.domain.models.SimpsonCharacter
import com.aguskoll.domain.repositories.CharactersRepository

class GetCharactersUseCase (
    private val charactersRepository: CharactersRepository
) {
    suspend operator fun invoke(): Result<List<SimpsonCharacter>> {
        return try {
            charactersRepository.getCharacters()
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}