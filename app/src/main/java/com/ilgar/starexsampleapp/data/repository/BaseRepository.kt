package com.ilgar.starexsampleapp.data.repository

import com.google.gson.Gson
import com.ilgar.starexsampleapp.data.models.core.ResultWrapper
import retrofit2.Response

open class BaseRepository {

    suspend fun <T> apiCall(
        apiCall: suspend () -> Response<T>?
    ): ResultWrapper<Response<T>>{
        return try {
            val result = apiCall()
            if (result?.isSuccessful == true){
                ResultWrapper.Success(result)
            }else {
                val errorBody = Gson().fromJson(result?.errorBody()?.string(), Any::class.java)
                ResultWrapper.Error(errorBody)
            }

        }catch (throwable: Throwable){
            throwable.printStackTrace()
            ResultWrapper.Error(message = "Error")
        }

    }
}