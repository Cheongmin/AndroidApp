package com.cheongmin.voicereader.Model

import com.google.gson.annotations.SerializedName

data class Answer(
        @SerializedName("_id")
        var id: String,
        @SerializedName("contents")
        var contents: String,
        @SerializedName("created_date")
        var createdDate: Long,
        @SerializedName("question_id")
        var soundUri: String,
        @SerializedName("writer_id")
        var writerId: String
)