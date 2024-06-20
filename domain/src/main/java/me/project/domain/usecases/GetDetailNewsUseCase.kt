package me.project.domain.usecases

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import me.project.data.repositories.NewsRepository
import me.project.domain.models.UiDetailNews
import me.project.domain.utils.toUiDetailNews
import javax.inject.Inject

class GetDetailNewsUseCase @Inject constructor(
    private val repository: NewsRepository
) {
    suspend operator fun invoke(title: String): Flow<UiDetailNews?> {
        return flow {
            val result = repository.getDetailNews(title)
                ?.toUiDetailNews()
            emit(result)
        }.flowOn(Dispatchers.IO)
    }
}
