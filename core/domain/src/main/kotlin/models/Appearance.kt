package com.aguskoll.domain.models

data class Appearance(
    val id: Int,
    val airdate: String,
    val description: String? = null,
    val episodeNumber: Int,
    val imagePath: String? = null,
    val name: String,
    val season: Int,
    val synopsis: String? = null,
)
