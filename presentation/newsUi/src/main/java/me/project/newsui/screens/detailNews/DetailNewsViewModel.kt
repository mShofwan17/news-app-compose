package me.project.newsui.screens.detailNews

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import me.project.domain.models.UiDetailNews
import me.project.domain.usecases.AddBookmarkUseCase
import me.project.domain.usecases.DeleteBookmarkUseCase
import me.project.domain.usecases.GetDetailNewsUseCase
import me.project.domain.usecases.IsBookmarkExistUseCase
import me.project.navigation.NavConstant
import me.project.shared.base.BaseViewModel
import java.net.URLDecoder
import java.nio.charset.StandardCharsets
import javax.inject.Inject

@HiltViewModel
class DetailNewsViewModel @Inject constructor(
    private val getDetailNewsUseCase: GetDetailNewsUseCase,
    private val addBookmarkUseCase: AddBookmarkUseCase,
    private val deleteBookmarkUseCase: DeleteBookmarkUseCase,
    private val isBookmarkExistUseCase: IsBookmarkExistUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val _imageUrl = MutableStateFlow("")
    val imageUrl get() = _imageUrl.asStateFlow()

    private val _newsDetail = MutableStateFlow<UiDetailNews?>(null)
    val newsDetail get() = _newsDetail.asStateFlow()

    private val _isBookmarkExist = MutableStateFlow<Boolean?>(null)
    val isBookmarkExist get() = _isBookmarkExist.asStateFlow()

    private val _delete = MutableStateFlow<Boolean?>(null)
    val delete get() = _isBookmarkExist.asStateFlow()

    private val _add = MutableStateFlow<Boolean?>(null)
    val add get() = _isBookmarkExist.asStateFlow()


    init {
        val title: String? = savedStateHandle[NavConstant.DETAIL_TITLE_ARG]
        val imageUrl: String? = savedStateHandle[NavConstant.DETAIL_IMAGE_ARG]
        imageUrl?.let { _imageUrl.value = URLDecoder.decode(it, StandardCharsets.UTF_8.toString()) }
        title?.let {
            getDetailNews(it)
            isBookmarkExist(it)
        }
    }

    private fun getDetailNews(title: String) {
        viewModelScope.launch {
            showLoading()
            getDetailNewsUseCase(title)
                .catch {
                    resetBaseState()
                    errorMessage(it)
                }
                .collectLatest {
                    resetBaseState()
                    it?.let {
                        _newsDetail.emit(it)
                    } ?: kotlin.run { errorMessage(Throwable("No Content")) }

                }
        }
    }

    private fun isBookmarkExist(title: String) {
        viewModelScope.launch {
            isBookmarkExistUseCase(title)
                .catch {
                    _isBookmarkExist.emit(false)
                    errorMessage(it)
                    Log.e("TAG_error_error", "isBookmarkExist: ${it.message}")
                }
                .collectLatest { _isBookmarkExist.emit(it) }
        }
    }

    fun deleteBookmark(item: UiDetailNews) {
        viewModelScope.launch {
            deleteBookmarkUseCase(item)
                .catch {
                    _delete.emit(false)
                    errorMessage(it)
                }
                .collectLatest {
                    _delete.emit(it)
                    _isBookmarkExist.emit(false)
                }
        }
    }

    fun addBookmark(item: UiDetailNews) {
        viewModelScope.launch {
            addBookmarkUseCase(item)
                .catch {
                    _add.emit(false)
                    errorMessage(it)
                    Log.e("TAG_error_error", "isBookmarkExist: ${it.message}")
                }
                .collectLatest {
                    _add.emit(it)
                    _isBookmarkExist.emit(true)
                }
        }
    }

    fun resetState() {
        viewModelScope.launch {
            _add.emit(null)
            _delete.emit(null)
        }
    }
}