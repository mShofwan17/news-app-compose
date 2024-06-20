package me.project.domain.usecases

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import me.project.data.repositories.NewsRepository
import me.project.domain.utils.toUiNews
import me.project.shared.base.BasePagingSource
import javax.inject.Inject

class GetHeadlineNewsUseCase @Inject constructor(
    private val repository: NewsRepository
) {
    operator fun invoke(category: String) = Pager(
        config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = true
        ),

        pagingSourceFactory = {
            BasePagingSource(
                loadResult = { params ->
                    val pageIndex = params.key ?: 1
                    try {
                        val response = repository.getHeadlineNews(
                            category = category,
                            page = pageIndex,
                            pageSize = 20
                        )

                        val result = response.articles
                        PagingSource.LoadResult.Page(
                            data = result.map { it.toUiNews() }
                                .filterNot { it.title == "[Removed]" },
                            prevKey = if (pageIndex == 1) null else pageIndex - 1,
                            nextKey = if (result.isEmpty()) null else pageIndex + 1
                        )
                    } catch (e: Exception) {
                        PagingSource.LoadResult.Error(e)
                    }
                }
            )
        }
    ).flow
}