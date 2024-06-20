package me.project.data.repositories

import me.project.data.models.News
import me.project.shared.base.BaseResponse

interface NewsRepository {
    suspend fun getHeadlineNews(category: String,pageSize: Int? = null, page: Int? = null): BaseResponse<List<News>>
    suspend fun getDetailNews(title: String): News?

    suspend fun addBookmark(news: News): Boolean
    suspend fun getBookmarks(): List<News>
    suspend fun deleteBookmark(news: News): Boolean
    suspend fun isBookmarkExist(title: String): Boolean?
}