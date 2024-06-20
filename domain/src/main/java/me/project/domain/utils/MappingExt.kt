package me.project.domain.utils

import me.project.data.models.News
import me.project.domain.models.UiDetailNews
import me.project.domain.models.UiHeadlineBanner
import me.project.domain.models.UiNews
import me.project.shared.extension.getDaysAgo
import me.project.shared.extension.ifNull

fun News.toUiNews(isFromDb: Boolean = false): UiNews {
    return UiNews(
        author,
        title ?: "",
        description,
        urlToImage,
        publishedAt?.getDaysAgo(isFromDb),
    )
}

fun News.toUiHeadlineBanner(): UiHeadlineBanner {
    return UiHeadlineBanner(
        title,
        urlToImage,
        publishedAt,
    )
}

fun News.toUiDetailNews(): UiDetailNews {
    return UiDetailNews(
        author,
        title,
        description.ifNull(),
        url,
        urlToImage,
        publishedAt?.getDaysAgo(false),
        content.ifNull(),
    )
}