package dev.chamo.millieapp.core.data.model

import dev.chamo.millieapp.core.database.model.TopHeadlineEntity
import dev.chamo.millieapp.core.model.TopHeadline
import dev.chamo.millieapp.core.network.model.NetworkTopHeadlines
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

fun NetworkTopHeadlines?.asExternalModel(
    selectedTitles: List<String> = emptyList()
): List<TopHeadline> {
    return this?.articles?.filterNot {
        it.title == "[Removed]"
    }?.map {
        TopHeadline(
            sourceName = it.source.name,
            author = it.author ?: "",
            title = it.title,
            description = it.description ?: "",
            url = it.url,
            urlToImage = it.urlToImage ?: "",
            publishedAt = it.publishedAt.calculatePublishedAt(),
            content = it.content ?: "",
            isSelected = selectedTitles.contains(it.title)
        )
    } ?: emptyList()
}

fun NetworkTopHeadlines?.asEntity(): List<TopHeadlineEntity> {
    return this?.articles?.filterNot {
        it.title == "[Removed]"
    }?.map {
        TopHeadlineEntity(
            sourceName = it.source.name,
            author = it.author ?: "",
            title = it.title,
            description = it.description ?: "",
            url = it.url,
            urlToImage = it.urlToImage ?: "",
            publishedAt = it.publishedAt.calculatePublishedAt(),
            content = it.content ?: "",
            isSelected = false
        )
    } ?: emptyList()
}

fun TopHeadline.asEntity(): TopHeadlineEntity {
    return TopHeadlineEntity(
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
}

fun String.calculatePublishedAt(): String {
    val seoulZone = TimeZone.of("Asia/Seoul")
    val currentTime = Clock.System.now().toLocalDateTime(seoulZone)
    val targetTime = Instant.parse(this).toLocalDateTime(seoulZone)

    val duration = currentTime.toInstant(seoulZone) - targetTime.toInstant(seoulZone)

    val hoursPassed = duration.inWholeHours
    val daysPassed = duration.inWholeDays

    return if (hoursPassed < 24) {
        "${hoursPassed}시간 전"
    } else {
        "${daysPassed}일 전"
    }
}