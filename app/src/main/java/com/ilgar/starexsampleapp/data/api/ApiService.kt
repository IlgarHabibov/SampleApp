package com.ilgar.starexsampleapp.data.api

import com.ilgar.starexsampleapp.data.models.posts.PostItem
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("posts")
    suspend fun getPosts(): Response<ArrayList<PostItem>>

}