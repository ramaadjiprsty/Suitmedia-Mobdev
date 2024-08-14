package com.testportal.suitmedia.data.remote.retrofit

import com.testportal.suitmedia.data.remote.response.UserResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("users")
    suspend fun getListUsers(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ) : UserResponse
}