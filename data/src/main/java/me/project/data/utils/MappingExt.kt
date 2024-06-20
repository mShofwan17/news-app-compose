package me.project.data.utils

import android.util.Log
import me.project.data.models.News
import me.project.local.entity.BookmarkNewsEntity
import me.project.network.models.NewsDto
import me.project.network.utils.validateBaseResponse
import me.project.shared.base.BaseResponse
import retrofit2.Response

fun NewsDto.toNews(): News {
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

fun BookmarkNewsEntity.toNews(): News {
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

suspend fun Response<BaseResponse<List<NewsDto>>>.toResponseNews(): BaseResponse<List<News>> {
    var baseResponse: BaseResponse<List<News>> = BaseResponse(articles = emptyList())

    this.validateBaseResponse {
        Log.i("TAG_baseResponse", "toResponseNews: $this")
       baseResponse = BaseResponse(
            articles = it.articles.map { articles -> articles.toNews() },
            totalResults = it.totalResults,
            status = it.status
        )

        Log.i("TAG_baseResponse", "baseResponse: $baseResponse")
    }

    Log.i("TAG_baseResponse", "baseResponse: $baseResponse")
    return baseResponse
}