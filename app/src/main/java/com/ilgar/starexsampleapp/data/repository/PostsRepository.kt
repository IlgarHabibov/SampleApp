package com.ilgar.starexsampleapp.data.repository

import com.ilgar.starexsampleapp.data.api.ApiService
import com.ilgar.starexsampleapp.data.models.core.ResultWrapper
import com.ilgar.starexsampleapp.data.models.posts.PostItem
import retrofit2.Response
import javax.inject.Inject

interface PostsRepository {
    suspend fun getPosts(): ResultWrapper<Response<ArrayList<PostItem>>>
}

class PostsRepositoryImpl @Inject constructor(
    private val apiService: ApiService
): BaseRepository(), PostsRepository{

    override suspend fun getPosts(): ResultWrapper<Response<ArrayList<PostItem>>> {
        return apiCall {
            apiService.getPosts()
        }
    }

}