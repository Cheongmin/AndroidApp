package com.cheongmin.voicereader.model

data class Comment(
        val author: User,
        var content: String = "",
        var date: Long = 0,
        val article: Article
)