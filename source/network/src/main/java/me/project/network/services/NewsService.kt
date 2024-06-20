package me.project.network.services

import me.project.network.BuildConfig
import me.project.network.models.NewsDto
import me.project.shared.base.BaseResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {
    @GET("top-headlines")
    suspend fun getHeadlineNews(
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY,
        @Query("category") category: String,
        @Query("country") country: String = "us",
        @Query("pageSize") pageSize: Int? = 20,
        @Query("page") page: Int? = 1
    ): Response<BaseResponse<List<NewsDto>>>

    @GET("everything")
    suspend fun getDetailNews(
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY,
        @Query("qInTitle") title: String
    ): Response<BaseResponse<List<NewsDto>>>
}