package com.sklim.data.repository

import android.content.Context
import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sklim.data.api.ApiService
import com.sklim.domain.Constants
import com.sklim.domain.model.Images
import com.sklim.domain.repository.ImagesRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class ImagesRepositoryImpl @Inject constructor(
    @ApplicationContext
    val applicationContext: Context,
    private val api: ApiService
) : ImagesRepository {
    override suspend fun images(rq: Images.RQ): Response<List<Images.RS>> {
        return api.images(rq.page, Constants.DEFAULT_LIMIT)
    }

    override fun fetchImageList(): Flow<PagingData<Images.RS>> {
        return Pager(
            config = PagingConfig(Constants.DEFAULT_LIMIT, enablePlaceholders = false),
            pagingSourceFactory = { ImagesSource(api) }
        ).flow
    }
}