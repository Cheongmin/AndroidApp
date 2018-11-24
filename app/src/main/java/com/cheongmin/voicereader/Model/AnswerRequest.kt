package com.cheongmin.voicereader.Model

import com.google.gson.annotations.SerializedName

data class AnswerRequest(
        @SerializedName("contents")
        var contents: String
)