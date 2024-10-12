package dev.chamo.millieapp.core.data.model

import dev.chamo.millieapp.core.model.TopHeadLine
import dev.chamo.millieapp.core.network.model.NetworkTopHeadlines

fun NetworkTopHeadlines?.asExternalModel(): List<TopHeadLine> {
    return this?.articles?.map {
        TopHeadLine(
            sourceName = it.source.name,
            author = it.author,
            title = it.title,
            description = it.description,
            url = it.url,
            urlToImage = it.urlToImage,
            publishedAt = it.publishedAt,
            content = it.content
        )
    } ?: emptyList()
}