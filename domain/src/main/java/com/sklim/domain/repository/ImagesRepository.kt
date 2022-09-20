package com.sklim.domain.repository

import com.sklim.domain.model.Images
import retrofit2.Response

interface ImagesRepository {
    suspend fun images(rq: Images.RQ): Response<Array<Images.RS>>
}