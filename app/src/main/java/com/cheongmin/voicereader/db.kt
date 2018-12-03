package com.cheongmin.voicereader

import com.cheongmin.voicereader.model.Article
import com.cheongmin.voicereader.model.Comment
import com.cheongmin.voicereader.model.User

object db {
    val users: MutableList<User> = mutableListOf()
    val articles: MutableList<Article> = mutableListOf()
    val comments: MutableList<Comment> = mutableListOf()
}