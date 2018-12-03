package com.cheongmin.voicereader.model

data class Article(
        var author: User,
        var title: String = "",
        var content: String = "",
        var date: Long = 0,
        var comments: MutableList<Comment> = mutableListOf()
)