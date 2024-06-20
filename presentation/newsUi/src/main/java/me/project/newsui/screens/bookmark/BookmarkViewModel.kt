package me.project.newsui.screens.bookmark

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import me.project.domain.models.UiNews
import me.project.domain.usecases.GetBookmarkUseCase
import me.project.shared.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val bookmarkUseCase: GetBookmarkUseCase
) : BaseViewModel() {
    private val _bookmark = MutableStateFlow<List<UiNews>?>(null)
    val bookmark get() = _bookmark.asStateFlow()

    fun getBookmarks() {
        viewModelScope.launch {
            bookmarkUseCase()
                .catch {
                    errorMessage(it)
                }
                .collectLatest {
                    _bookmark.emit(it)
                }
        }
    }
}