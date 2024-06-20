package me.project.data.repositories

import me.project.data.dataSource.NewsDataSource
import me.project.data.models.News
import me.project.shared.base.BaseResponse
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val dataSource: NewsDataSource
) : NewsRepository {

    override suspend fun getHeadlineNews(
        category: String,
        pageSize: Int?,
        page: Int?
    ): BaseResponse<List<News>> {
        return dataSource.getHeadline(category, pageSize, page)
    }

    override suspend fun getDetailNews(title: String): News? {
        return dataSource.getDetailNews(title)
    }

    override suspend fun addBookmark(news: News): Boolean {
        return dataSource.addBookmark(news)
    }

    override suspend fun getBookmarks(): List<News> {
        return dataSource.getBookmarks()
    }

    override suspend fun deleteBookmark(news: News): Boolean {
        return dataSource.deleteBookmark(news)
    }

    override suspend fun isBookmarkExist(title: String): Boolean? {
        return dataSource.isBookmarkExist(title)
    }
}