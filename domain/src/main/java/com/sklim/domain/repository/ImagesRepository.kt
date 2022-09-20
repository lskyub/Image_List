package com.sklim.domain.repository

import com.sklim.domain.model.Images

interface ImagesRepository {
    fun images(rq: Images.RQ)
}