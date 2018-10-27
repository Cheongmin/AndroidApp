package com.cheongmin.voicereader.Models

data class Lecture(
        val id: Number,
        val title: String,
        val description: String,
        val chapters: List<Chapter>
)