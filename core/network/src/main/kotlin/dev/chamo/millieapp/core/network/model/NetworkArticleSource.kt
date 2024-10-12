package dev.chamo.millieapp.core.network.model

import kotlinx.serialization.Serializable

@Serializable
data class NetworkArticleSource(
    val id: String?,
    val name: String
)