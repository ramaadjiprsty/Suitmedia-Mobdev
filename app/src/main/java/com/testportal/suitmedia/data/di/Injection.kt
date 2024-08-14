package com.testportal.suitmedia.data.di

import android.content.Context
import com.testportal.suitmedia.data.remote.retrofit.ApiConfig
import com.testportal.suitmedia.data.repository.UserRepository

object Injection {
    fun provideRepository(): UserRepository {
        val apiService = ApiConfig.getApiService()
        return UserRepository.getInstance(apiService)

    }
}