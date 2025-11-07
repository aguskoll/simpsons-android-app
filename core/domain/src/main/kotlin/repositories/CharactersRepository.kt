package com.aguskoll.domain.repositories

import com.aguskoll.domain.models.SimpsonCharacter

interface CharactersRepository {
    suspend fun getCharacters(): Result<List<SimpsonCharacter>>
}