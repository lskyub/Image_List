package com.sklim.domain.usecase

import androidx.paging.PagingData
import com.sklim.domain.model.Images
import com.sklim.domain.repository.ImagesRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class ImagesUseCase @Inject constructor(private val repository: ImagesRepository) {
    suspend fun images(rq: Images.RQ): Response<List<Images.RS>> {
        return repository.images(rq)
    }

    fun fetchImageList(): Flow<PagingData<Images.RS>> {
        return repository.fetchImageList()
    }

    suspend fun image(rq: Images.RQ): Response<Images.RS> {
        return repository.image(rq)
    }
}