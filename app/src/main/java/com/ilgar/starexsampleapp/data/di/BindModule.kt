package com.ilgar.starexsampleapp.data.di

import com.ilgar.starexsampleapp.data.repository.PostsRepository
import com.ilgar.starexsampleapp.data.repository.PostsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class BindModule {

    @Singleton
    @Binds
    abstract fun bindsPostsRepository(postsRepositoryImpl: PostsRepositoryImpl): PostsRepository
}