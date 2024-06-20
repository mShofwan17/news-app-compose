package me.project.domain.models

import me.project.data.models.News

data class UiDetailNews(
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAt: String?,
    val content: String?,
) {
    fun toNews(): News {
        return News(
            author,
            title,
            description,
            url,
            urlToImage,
            publishedAt,
            content,
        )
    }
}