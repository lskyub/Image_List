package com.sklim.domain.model

import com.sklim.domain.Constants

object Images {
    class RQ(var page: Int, var limit: Int = Constants.DEFAULT_LIMIT)

    class RS(
        var id: Int,
        var author: String,
        var width: Int,
        var height: Int,
        var url: String,
        var download_url: String
    )
}