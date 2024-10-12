package dev.chamo.millieapp.core.data.model

import dev.chamo.millieapp.core.model.TopHeadLine
import dev.chamo.millieapp.core.network.model.NetworkTopHeadlines
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

fun NetworkTopHeadlines?.asExternalModel(): List<TopHeadLine> {
    return this?.articles?.filterNot {
        it.title == "[Removed]"
    }?.map {
        TopHeadLine(
            sourceName = it.source.name,
            author = it.author ?: "",
            title = it.title,
            description = it.description ?: "",
            url = it.url,
            urlToImage = it.urlToImage ?: "",
            publishedAt = it.publishedAt.calculatePublishedAt(),
            content = it.content ?: ""
        )
    } ?: emptyList()
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