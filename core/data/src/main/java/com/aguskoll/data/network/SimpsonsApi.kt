package com.aguskoll.data.network

import com.aguskoll.domain.models.SimpsonCharacter
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import retrofit2.http.GET

interface SimpsonsApi {

    @GET("characters")
    suspend fun getCharacters(): CharactersResponse
}

@Serializable
data class CharactersResponse(
    @SerialName("count") val count: Int,
    @SerialName("next") val next: String?,
    @SerialName("prev") val prev: String?,
    @SerialName("pages") val pages: Int,
    @SerialName("results") val results: List<CharacterDto>
)

@Serializable
data class CharacterDto(
    @SerialName("id") val id: Int,
    @SerialName("age") val age: Int? = null,
    @SerialName("birthdate") val birthdate: String? = null,
    @SerialName("gender") val gender: String? = null,
    @SerialName("name") val name: String,
    @SerialName("occupation") val occupation: String? = null,
    @SerialName("portrait_path") val portraitPath: String? = null,
    @SerialName("phrases") val phrases: List<String>? = null,
    @SerialName("status") val status: String? = null
) {
    fun toDomain(): SimpsonCharacter = SimpsonCharacter(
        id = id,
        name = name,
        portraitPath = portraitPath,
        age = age,
        birthdate = birthdate,
        gender = gender,
        occupation = occupation,
        phrases = phrases ?: emptyList(),
        status = status
    )
}

