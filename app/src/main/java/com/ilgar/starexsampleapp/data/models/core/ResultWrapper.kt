package com.ilgar.starexsampleapp.data.models.core

sealed class ResultWrapper<out T>{
    data class Success<out T>(val value: T): ResultWrapper<T>()
    data class Error(val message: Any): ResultWrapper<Nothing>()
}
