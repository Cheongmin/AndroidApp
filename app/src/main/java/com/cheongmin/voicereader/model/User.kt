package com.cheongmin.voicereader.model

data class User(
        var name: String = "",
        var location: String = "",
        var profileUri: String = "",
        var articles: List<Article> = listOf(),
        var comments: List<Comment> = listOf()
)