package com.jimmy.pagginglib_tutorial.datastructs

class StackApiResponse(
    var items: MutableList<Item>? = null,
    var has_more: Boolean = false,
    var quota_max: Int = 0,
    var quota_remaining: Int = 0)
