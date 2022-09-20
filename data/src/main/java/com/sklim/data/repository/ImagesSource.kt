package com.sklim.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sklim.data.api.ApiService
import com.sklim.domain.Constants
import com.sklim.domain.model.Images

class ImagesSource(private val api: ApiService) :
    PagingSource<Int, Images.RS>() {
    override fun getRefreshKey(state: PagingState<Int, Images.RS>): Int {
        return 0
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Images.RS> {
        return try {
            val page = params.key ?: 1
            val results = api.images(page, Constants.DEFAULT_LIMIT).body()?.toMutableList()
            results?.run {
                val nextPage = if (count() == Constants.DEFAULT_LIMIT) page + 1 else null
                LoadResult.Page(data = this, nextKey = nextPage, prevKey = null)
            } ?: LoadResult.Error(NullPointerException())
        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(e)
        }
    }
}