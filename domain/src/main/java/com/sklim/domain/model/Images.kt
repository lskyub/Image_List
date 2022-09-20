package com.sklim.domain.model

object Images {
    class RQ(var value: Int)

    class RS(
        var id: Int,
        var author: String,
        var width: Int,
        var height: Int,
        var url: String,
        var download_url: String
    )
}