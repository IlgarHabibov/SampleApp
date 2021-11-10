package com.ilgar.starexsampleapp.ui.features.posts

import androidx.lifecycle.viewModelScope
import com.ilgar.starexsampleapp.data.models.core.ResultWrapper
import com.ilgar.starexsampleapp.data.repository.PostsRepository
import com.ilgar.starexsampleapp.ui.core.CoreViewModel
import com.ilgar.starexsampleapp.ui.features.posts.event.PostsEvent
import com.ilgar.starexsampleapp.ui.features.posts.state.MainInputValidationTypes
import com.ilgar.starexsampleapp.ui.features.posts.state.PostsViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(
    private val postsRepository: PostsRepository
) : CoreViewModel<PostsEvent>() {

    private val _viewState = MutableStateFlow<PostsViewState>(PostsViewState.None)
    val viewState: StateFlow<PostsViewState> = _viewState

    override fun obtainEvent(event: PostsEvent) {
        when (event) {
            is PostsEvent.InputValidation -> checkTextValidation(event.text)
        }
    }

    private fun getPosts() {
        viewModelScope.launch {
            _viewState.emit(PostsViewState.Loading)
            when (val response = postsRepository.getPosts()) {
                is ResultWrapper.Success -> {
                    val posts = response.value.body()
                    if (posts.isNullOrEmpty()) {
                        _viewState.emit(PostsViewState.OnPostsEmpty)
                    } else {
                        _viewState.emit(PostsViewState.OnPostsSuccess(posts))
                    }
                }
                is ResultWrapper.Error -> _viewState.emit(PostsViewState.OnPostsError)
            }
        }
    }

    private fun checkTextValidation(text: String?) {
        val trimmedText = text?.trim()
        viewModelScope.launch {
            when {
                trimmedText.isNullOrEmpty() -> _viewState.emit(
                    PostsViewState.OnValidationFail(
                        MainInputValidationTypes.EMPTY
                    )
                )
                trimmedText.length < 3 -> _viewState.emit(
                    PostsViewState.OnValidationFail(
                        MainInputValidationTypes.LENGTH
                    )
                )
                else -> getPosts()
            }
        }
    }
}