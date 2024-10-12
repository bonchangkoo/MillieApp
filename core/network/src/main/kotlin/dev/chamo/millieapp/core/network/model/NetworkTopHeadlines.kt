package dev.chamo.millieapp.core.network.model

import kotlinx.serialization.Serializable

@Serializable
data class NetworkTopHeadlines(
    val articles: List<NetworkArticle>,
    val status: String,
    val totalResults: Int
)
