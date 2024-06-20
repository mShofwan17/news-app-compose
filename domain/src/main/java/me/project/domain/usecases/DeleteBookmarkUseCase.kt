package me.project.domain.usecases

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import me.project.data.repositories.NewsRepository
import me.project.domain.models.UiDetailNews
import javax.inject.Inject

class DeleteBookmarkUseCase @Inject constructor(
    private val repository: NewsRepository
) {
    suspend operator fun invoke(news: UiDetailNews): Flow<Boolean> {
        return flow {
            val result = repository.deleteBookmark(news.toNews())
            emit(result)
        }.flowOn(Dispatchers.IO)
    }
}
