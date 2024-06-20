package me.project.data.dataSource

import android.util.Log
import me.project.data.models.News
import me.project.data.utils.toNews
import me.project.data.utils.toResponseNews
import me.project.local.NewsDatabase
import me.project.network.services.NewsService
import me.project.network.utils.validateResponse
import me.project.shared.base.BaseResponse
import javax.inject.Inject

class NewsDataSource @Inject constructor(
    private val service: NewsService,
    private val database: NewsDatabase
) {

    suspend fun getHeadline(
        category: String,
        pageSize: Int?,
        page: Int?
    ): BaseResponse<List<News>> {
        return service.getHeadlineNews(
            category = category,
            pageSize = pageSize,
            page = page
        ).toResponseNews()
    }

    suspend fun getDetailNews(title: String): News? {
        var news: News? = null
        service.getDetailNews(title = title).validateResponse {
            news = it.articles.firstOrNull()?.toNews()
        }

        return news
    }

    suspend fun addBookmark(item: News): Boolean {
        return try {
            val add = database.bookmarkDao().add(item.toNewsEntity())
            add != -1L
        } catch (e: Exception) {
            throw Exception(e)
        }
    }

    suspend fun getBookmarks(): List<News> {
        return try {
            val items = database.bookmarkDao().getAll()
            items.map { it.toNews() }
        } catch (e: Exception) {
            Log.e("TAG_BOOKMARK_RES", "error: ${e.message}")
            throw Exception(e)
        }
    }

    suspend fun deleteBookmark(item: News): Boolean {
        return try {
            val find = database.bookmarkDao().find(item.title ?: "")
            val delete = find?.let { database.bookmarkDao().delete(it) }
            delete != -1
        } catch (e: Exception) {
            throw Exception(e)
        }
    }

    suspend fun isBookmarkExist(title: String): Boolean? {
        return try {
            val find = database.bookmarkDao().find(title)
            find != null
        } catch (e: Exception) {
            throw Exception(e)
        }
    }
}