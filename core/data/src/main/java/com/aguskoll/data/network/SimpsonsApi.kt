package com.aguskoll.data.network

import com.aguskoll.domain.models.SimpsonCharacter
import com.aguskoll.domain.models.SimpsonCharacterDetail
import com.aguskoll.domain.models.Appearance
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import retrofit2.http.GET
import retrofit2.http.Path

interface SimpsonsApi {

    @GET("characters")
    suspend fun getCharacters(): CharactersResponse

    @GET("characters/{id}")
    suspend fun getCharacterById(@Path("id") id: Int): CharacterDetailDto
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
        portraitPath = portraitPath?.let { path ->
           com.aguskoll.data.BuildConfig.API_IMAGES_URL +"200"+ path
        },
        age = age,
        birthdate = birthdate,
        gender = gender,
        occupation = occupation,
        phrases = phrases ?: emptyList(),
        status = status
    )
}

/**
 * Complete Character DTO (detail) with nested first appearance objects.
 */
@Serializable
data class CharacterDetailDto(
    @SerialName("id") val id: Int,
    @SerialName("age") val age: Int? = null,
    @SerialName("birthdate") val birthdate: String? = null,
    @SerialName("description") val description: String? = null,
    @SerialName("first_appearance_ep_id") val firstAppearanceEpId: Int? = null,
    @SerialName("first_appearance_sh_id") val firstAppearanceShId: Int? = null,
    @SerialName("gender") val gender: String? = null,
    @SerialName("name") val name: String,
    @SerialName("occupation") val occupation: String? = null,
    @SerialName("phrases") val phrases: List<String> = emptyList(),
    @SerialName("portrait_path") val portraitPath: String? = null,
    @SerialName("status") val status: String? = null,
    @SerialName("first_appearance_ep") val firstAppearanceEp: AppearanceDto? = null,
    @SerialName("first_appearance_sh") val firstAppearanceSh: AppearanceDto? = null,
)

@Serializable
data class AppearanceDto(
    @SerialName("id") val id: Int,
    @SerialName("airdate") val airdate: String,
    @SerialName("description") val description: String? = null,
    @SerialName("episode_number") val episodeNumber: Int,
    @SerialName("image_path") val imagePath: String? = null,
    @SerialName("name") val name: String,
    @SerialName("season") val season: Int,
    @SerialName("synopsis") val synopsis: String? = null,
)

fun CharacterDetailDto.toDetailDomain(): SimpsonCharacterDetail = SimpsonCharacterDetail(
    id = id,
    name = name,
    portraitPath = portraitPath?.let { path ->
        com.aguskoll.data.BuildConfig.API_IMAGES_URL + "200" + path
    },
    age = age,
    birthdate = birthdate,
    gender = gender,
    occupation = occupation,
    phrases = phrases,
    status = status,
    description = description,
    firstAppearanceEpisode = firstAppearanceEp?.toDomain(),
    firstAppearanceShort = firstAppearanceSh?.toDomain(),
)

fun AppearanceDto.toDomain(): Appearance = Appearance(
    id = id,
    airdate = airdate,
    description = description,
    episodeNumber = episodeNumber,
    imagePath = imagePath?.let { path ->
        com.aguskoll.data.BuildConfig.API_IMAGES_URL + "200" + path
    },
    name = name,
    season = season,
    synopsis = synopsis,
)

