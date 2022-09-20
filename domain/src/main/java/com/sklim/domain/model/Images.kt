package com.sklim.domain.model

import com.sklim.domain.Constants

object Images {
    class RQ(var page: Int, var limit: Int = Constants.DEFAULT_LIMIT)

    class RS(var items: ArrayList<Item>)

    data class Item(
        var id: Int,
        var author: Int,
        var width: Int,
        var height: Int,
        var url: Int,
        var download_url: Int
    )
}