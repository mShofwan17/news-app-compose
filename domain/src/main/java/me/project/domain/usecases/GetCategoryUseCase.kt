package me.project.domain.usecases

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import me.project.domain.models.UiCategory
import me.project.domain.utils.CategoryEnum
import javax.inject.Inject

class GetCategoryUseCase @Inject constructor() {
    suspend operator fun invoke(): Flow<List<UiCategory>> {
        return flow {
            val result = mutableListOf<UiCategory>()
            CategoryEnum.entries.forEach {
                result.add(
                    UiCategory(
                        it.category,
                        it.category == CategoryEnum.GENERAL.category
                    )
                )
            }
            emit(result)
        }.flowOn(Dispatchers.IO)
    }
}
