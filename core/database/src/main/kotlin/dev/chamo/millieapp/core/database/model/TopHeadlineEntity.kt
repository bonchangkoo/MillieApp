package dev.chamo.millieapp.core.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.chamo.millieapp.core.model.TopHeadline

@Entity(
    tableName = "top_headlines",
)
data class TopHeadlineEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val sourceName: String,
    val author: String,
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

fun List<TopHeadlineEntity>.asExternalModel(
    selectedTitles: List<String> = emptyList()
): List<TopHeadline>{
    return map {
        it.copy(
          isSelected = selectedTitles.contains(it.title)
        ).asExternalModel()
    }
}