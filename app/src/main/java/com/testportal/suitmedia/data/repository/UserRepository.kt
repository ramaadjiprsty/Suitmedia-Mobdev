package com.testportal.suitmedia.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.asFlow
import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.testportal.suitmedia.data.remote.Result
import com.testportal.suitmedia.data.remote.UserPagingSource
import com.testportal.suitmedia.data.remote.response.DataItem
import com.testportal.suitmedia.data.remote.retrofit.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first

class UserRepository private constructor(
    private var apiService: ApiService
){
    fun getListUsers(page: Int, perPage: Int): LiveData<Result<PagingData<DataItem>>> = liveData(Dispatchers.IO) {
        emit(Result.Loading)
        try {
            val pager = Pager(
                config = PagingConfig(
                    pageSize = perPage
                ),
                pagingSourceFactory = {
                    UserPagingSource(apiService, page)
                }
            ).liveData
            val pagingData = pager.asFlow().first()
            emit(Result.Success(pagingData))
        } catch (error: Exception) {
            emit(Result.Error(error.message.toString()))
        }
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null

        fun getInstance(
            apiService: ApiService,
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(apiService)
            }.also { instance = it }
    }
}
