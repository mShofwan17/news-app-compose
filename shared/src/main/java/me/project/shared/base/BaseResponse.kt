package me.project.shared.base

data class BaseResponse<T>(
    val status: String = "",
    val totalResults: Int = 0,
    val articles: T
)
