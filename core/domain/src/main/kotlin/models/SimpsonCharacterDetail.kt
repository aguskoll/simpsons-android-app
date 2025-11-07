package com.aguskoll.domain.models

/**
 * Detailed domain model for a Simpsons character, including description and
 * first appearance references. UI can choose between this and the lighter
 * SimpsonCharacter depending on needs.
 */
 data class SimpsonCharacterDetail(
    val id: Int,
    val name: String,
    val portraitPath: String?,
    val age: Int? = null,
    val birthdate: String? = null,
    val gender: String? = null,
    val occupation: String? = null,
    val phrases: List<String> = emptyList(),
    val status: String? = null,
    val description: String? = null,
    val firstAppearanceEpisode: Appearance? = null,
    val firstAppearanceShort: Appearance? = null,
 )
