package com.sklim.data.api

import com.sklim.domain.model.Images
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("v2/list?")
    suspend fun images(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
    ): Response<List<Images.RS>>

    @GET("id/{id}/info")
    suspend fun image(
        @Path("id") id: Int
    ): Response<Images.RS>
}