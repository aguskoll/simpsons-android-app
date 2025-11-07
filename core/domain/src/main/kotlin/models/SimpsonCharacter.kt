package com.aguskoll.domain.models

/**
 * Domain model representing a Simpsons character.
 *
 * Keep this UI-agnostic. Any URL building (e.g., prefixing image base URL) should be done
 * in the data layer mapper when converting from DTOs to this domain model.
 */
data class SimpsonCharacter(
    val id: Int,
    val name: String,
    val portraitPath: String?, // relative or absolute path depending on mapper
    val age: Int? = null,
    val birthdate: String? = null,
    val gender: String? = null,
    val occupation: String? = null,
    val phrases: List<String> = emptyList(),
    val status: String? = null,
)
