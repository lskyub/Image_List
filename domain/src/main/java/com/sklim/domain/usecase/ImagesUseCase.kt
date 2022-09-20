package com.sklim.domain.usecase

import com.sklim.domain.model.Images
import com.sklim.domain.repository.ImagesRepository
import retrofit2.Response
import javax.inject.Inject

class ImagesUseCase @Inject constructor(private val repository: ImagesRepository) {
    suspend fun execute(rq: Images.RQ): Response<Array<Images.RS>> {
        return repository.images(rq)
    }
}