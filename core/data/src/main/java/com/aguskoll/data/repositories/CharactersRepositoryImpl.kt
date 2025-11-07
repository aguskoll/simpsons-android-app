package com.aguskoll.data.repositories

import com.aguskoll.data.network.ApiProvider
import com.aguskoll.domain.models.SimpsonCharacter
import com.aguskoll.domain.repositories.CharactersRepository
import kotlin.runCatching

class CharactersRepositoryImpl(val apiProvider: ApiProvider) : CharactersRepository {
    override suspend fun getCharacters(): Result<List<SimpsonCharacter>> = runCatching {
        val response = apiProvider.api.getCharacters()
        response.results.map { characterDto ->
            characterDto.toDomain()
        }
    }
}