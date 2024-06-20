package me.project.data.models

import me.project.local.entity.BookmarkNewsEntity

data class News(
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAt: String?,
    val content: String?,
){
    fun toNewsEntity(): BookmarkNewsEntity{
        return BookmarkNewsEntity(
            id = 0L,
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