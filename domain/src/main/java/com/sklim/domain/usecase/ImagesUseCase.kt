package com.sklim.domain.usecase

import com.sklim.domain.model.Images
import com.sklim.domain.repository.ImagesRepository
import javax.inject.Inject

class ImagesUseCase @Inject constructor(private val repository: ImagesRepository) {
    fun execute(rq: Images.RQ) = repository.images(rq)
}