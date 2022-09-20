package com.sklim.data.repository

import android.content.Context
import android.util.Log
import com.sklim.data.api.ApiService
import com.sklim.domain.model.Images
import com.sklim.domain.repository.ImagesRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Response
import javax.inject.Inject

class ImagesRepositoryImpl @Inject constructor(
    @ApplicationContext
    val applicationContext: Context,
    private val api: ApiService
) : ImagesRepository {
    override suspend fun images(rq: Images.RQ): Response<Array<Images.RS>> {
        return api.images(rq.page, rq.limit)
    }
}