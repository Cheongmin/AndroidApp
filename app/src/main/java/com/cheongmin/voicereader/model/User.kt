package com.cheongmin.voicereader.model

import com.google.gson.annotations.SerializedName

data class User(
        @SerializedName("_id")
        var id: String,
        @SerializedName("created_date")
        var createdDate: Long,
        @SerializedName("display_name")
        var display_name: String,
        @SerializedName("email")
        var email: String,
        @SerializedName("fcm_uid")
        var fcm_uid: String
)