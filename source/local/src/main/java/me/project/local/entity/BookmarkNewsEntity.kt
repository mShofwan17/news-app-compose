package me.project.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tb_bookmark")
data class BookmarkNewsEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAt: String?,
    val content: String?,
)
