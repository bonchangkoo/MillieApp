package dev.chamo.millieapp.core.network.model

import kotlinx.serialization.Serializable

@Serializable
data class NetworkArticle(
    val source: NetworkArticleSource,
    val author: String?,
    val title: String,
    val description: String?,
    val url: String,
    val urlToImage: String?,
    val publishedAt: String,
    val content: String?
)

