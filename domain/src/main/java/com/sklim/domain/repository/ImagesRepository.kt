package com.sklim.domain.repository

import androidx.paging.PagingData
import com.sklim.domain.model.Images
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface ImagesRepository {
    suspend fun images(rq: Images.RQ): Response<List<Images.RS>>
    fun fetchImageList(): Flow<PagingData<Images.RS>>
}