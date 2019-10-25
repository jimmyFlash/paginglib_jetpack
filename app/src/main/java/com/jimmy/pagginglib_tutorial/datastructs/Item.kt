package com.jimmy.pagginglib_tutorial.datastructs

data class Item(
    var owner: Owner? = null,
    var is_accepted: Boolean = false,
    var score: Int = 0,
    var last_activity_date: Long = 0,
    var creation_date: Long = 0,
    var answer_id: Long = 0,
    var question_id: Long = 0)
