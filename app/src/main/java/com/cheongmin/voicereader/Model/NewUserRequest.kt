package com.cheongmin.voicereader.Model

import com.google.gson.annotations.SerializedName

data class NewUserRequest(
        @SerializedName("display_name")
        var display_name: String
)