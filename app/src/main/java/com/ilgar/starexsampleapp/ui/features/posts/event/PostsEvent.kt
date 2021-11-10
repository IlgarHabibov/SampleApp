package com.ilgar.starexsampleapp.ui.features.posts.event

import com.ilgar.starexsampleapp.ui.core.CoreEvent

sealed class PostsEvent: CoreEvent{
    data class InputValidation(var text: String?): PostsEvent()
}
