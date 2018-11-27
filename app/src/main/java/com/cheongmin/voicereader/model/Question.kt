package com.cheongmin.voicereader.model

import com.google.gson.annotations.SerializedName

data class Question(
        @SerializedName("_id")
        var id: String,

        @SerializedName("contents")
        var contents: String,

        @SerializedName("created_date")
        var createdDate: Long,

        @SerializedName("sound_url")
        var soundUri: String,

        @SerializedName("subtitles")
        var subtitle: String,

        @SerializedName("writer_id")
        var writerId: String
)