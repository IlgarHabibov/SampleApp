package com.ilgar.starexsampleapp.ui.features.posts.state

import com.ilgar.starexsampleapp.data.models.posts.PostItem
import com.ilgar.starexsampleapp.ui.core.CoreViewState

sealed class PostsViewState: CoreViewState{
    data class OnPostsSuccess(var posts: ArrayList<PostItem>): PostsViewState()
    object OnPostsEmpty: PostsViewState()
    object OnPostsError: PostsViewState()
    data class OnValidationFail(var failType: MainInputValidationTypes): PostsViewState()
    object Loading: PostsViewState()
    object None: PostsViewState()
}
