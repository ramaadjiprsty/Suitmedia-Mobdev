package com.testportal.suitmedia.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.testportal.suitmedia.data.remote.response.DataItem
import com.testportal.suitmedia.data.remote.retrofit.ApiService

class UserPagingSource(
    private val apiService: ApiService,
    private val page: Int
) : PagingSource<Int, DataItem>() {

    override fun getRefreshKey(state: PagingState<Int, DataItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DataItem> =
        try {
            val nextPage = params.key ?: page
            val response = apiService.getListUsers(nextPage, params.loadSize)
            val maxPage = response.totalPages

            LoadResult.Page(
                data = response.data,
                prevKey = if (nextPage == page) null else nextPage - 1,
                nextKey = if (nextPage == maxPage) null else nextPage + 1
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }


}