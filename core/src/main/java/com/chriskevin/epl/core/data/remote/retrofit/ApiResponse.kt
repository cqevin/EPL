package com.chriskevin.epl.core.data.remote.retrofit

import retrofit2.Response
import timber.log.Timber

sealed class ApiResponse<out T> {
    companion object {
        fun <T> call(response: Response<T>): ApiResponse<T> {
            return if (response.isSuccessful) {
                val body = response.body()
                if (body == null || response.code() == 204) Empty
                else Success(body)
            } else {
                val msg = response.errorBody()?.string()
                val errorMsg = if (msg.isNullOrEmpty()) response.message() else msg
                Timber.tag("RemoteDataSource").e(errorMsg ?: "unknown error")
                Error(errorMsg ?: "unknown error")
            }
        }
    }

    data class Success<out T>(val data: T) : ApiResponse<T>()
    data class Error(val errorMessage: String) : ApiResponse<Nothing>()
    object Empty : ApiResponse<Nothing>()
}