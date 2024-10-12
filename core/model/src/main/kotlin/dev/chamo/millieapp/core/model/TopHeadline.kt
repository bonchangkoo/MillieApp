package dev.chamo.millieapp.core.model

data class TopHeadline(
    val sourceName: String,
    val author: String,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: String,
    val content: String,
    val isSelected: Boolean = false
)