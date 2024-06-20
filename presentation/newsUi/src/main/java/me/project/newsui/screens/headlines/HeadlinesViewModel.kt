package me.project.newsui.screens.headlines

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.project.domain.models.UiCategory
import me.project.domain.models.UiHeadlineBanner
import me.project.domain.models.UiNews
import me.project.domain.usecases.GetCategoryUseCase
import me.project.domain.usecases.GetHeadlineBannerUseCase
import me.project.domain.usecases.GetHeadlineNewsUseCase
import me.project.domain.utils.CategoryEnum
import me.project.shared.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class HeadlinesViewModel @Inject constructor(
    private val getHeadlineBannerUseCase: GetHeadlineBannerUseCase,
    private val getHeadlineNewsUseCase: GetHeadlineNewsUseCase,
    private val getCategoryUseCase: GetCategoryUseCase
) : BaseViewModel() {
    private val _headlineBanner = MutableStateFlow<List<UiHeadlineBanner>?>(null)
    val headlineBanner get() = _headlineBanner.asStateFlow()

    private val _selectedCategory = MutableStateFlow(CategoryEnum.GENERAL.category)
    val selectedCategory get() = _selectedCategory.asStateFlow()

    private val _headlineCategory = MutableStateFlow<List<UiCategory>>(emptyList())
    val headlineCategory get() = _headlineCategory.asStateFlow()

    private var _listPaging = MutableStateFlow<PagingData<UiNews>>(PagingData.empty())
    val listPaging get() = _listPaging.asStateFlow()

    init {
        setUiCategory()
        moveHeadlineCategory(CategoryEnum.GENERAL.category)
    }

    private fun setUiCategory() {
        viewModelScope.launch {
            getCategoryUseCase().collectLatest {
                _headlineCategory.emit(it)
            }
        }
    }

    fun getHeadlineNews(category: String) {
        viewModelScope.launch {
            getHeadlineNewsUseCase(category).cachedIn(this)
                .collect {
                    _listPaging.value = it
                }
        }
    }


    fun getHeadlineBanner(category: String) {
        viewModelScope.launch {
            getHeadlineBannerUseCase(category)
                .catch { errorMessage(it) }
                .collectLatest {
                    _headlineBanner.emit(it)
                }
        }
    }

    fun moveHeadlineCategory(categoryEnum: String) {
        viewModelScope.launch {
            _selectedCategory.emit(categoryEnum)
            _headlineCategory.update { it.map { mapp -> mapp.copy(isSelected = categoryEnum == mapp.category) } }
        }
    }
}