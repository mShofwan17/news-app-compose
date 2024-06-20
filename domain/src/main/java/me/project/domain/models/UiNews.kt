package me.project.domain.models

data class UiNews(
    val author: String?,
    val title: String,
    val description: String?,
    val urlToImage: String?,
    val publishedAt: String?
)