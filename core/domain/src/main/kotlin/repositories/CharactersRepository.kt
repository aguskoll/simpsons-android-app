package com.aguskoll.domain.repositories

import com.aguskoll.domain.models.SimpsonCharacter
import com.aguskoll.domain.models.SimpsonCharacterDetail

interface CharactersRepository {
    suspend fun getCharacters(): Result<List<SimpsonCharacter>>
    suspend fun getCharacterById(id: String): Result<SimpsonCharacterDetail>
}