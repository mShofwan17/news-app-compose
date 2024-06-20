package me.project.domain.usecases

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import me.project.data.repositories.NewsRepository
import me.project.domain.models.UiDetailNews
import me.project.domain.models.UiNews
import me.project.domain.utils.toUiDetailNews
import me.project.domain.utils.toUiNews
import javax.inject.Inject

class GetBookmarkUseCase @Inject constructor(
    private val repository: NewsRepository
) {
    suspend operator fun invoke(): Flow<List<UiNews>> {
        return flow {
            val result = repository.getBookmarks()
                .map { it.toUiNews(true) }
            Log.i("TAG_BOOKMARK_RES", "invoke: $result")
            emit(result)
        }.flowOn(Dispatchers.IO)
    }
}
