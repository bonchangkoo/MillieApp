package dev.chamo.millieapp.core.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.chamo.millieapp.core.model.TopHeadline

@Entity(
    tableName = "top_headlines",
)
data class TopHeadlineEntity (
    val sourceName: String,
    val author: String,
    @PrimaryKey
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: String,
    val content: String,
    val isSelected: Boolean
)

fun TopHeadlineEntity.asExternalModel() = TopHeadline(
    sourceName = sourceName,
    author = author,
    title = title,
    description = description,
    url = url,
    urlToImage = urlToImage,
    publishedAt = publishedAt,
    content = content,
    isSelected = isSelected
)
