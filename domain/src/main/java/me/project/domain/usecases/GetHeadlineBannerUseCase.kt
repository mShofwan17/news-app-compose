package me.project.domain.usecases

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import me.project.data.repositories.NewsRepository
import me.project.domain.models.UiHeadlineBanner
import me.project.domain.utils.toUiHeadlineBanner
import javax.inject.Inject

class GetHeadlineBannerUseCase @Inject constructor(
    private val repository: NewsRepository
) {
    operator fun invoke(category: String): Flow<List<UiHeadlineBanner>> {
        return flow {
            val response = repository.getHeadlineNews(category = category)
                .articles.map { it.toUiHeadlineBanner() }
                .filterNot { it.urlToImage.isNullOrEmpty() }
                .takeLast(4)
            emit(response)
        }.flowOn(Dispatchers.IO)
    }
}