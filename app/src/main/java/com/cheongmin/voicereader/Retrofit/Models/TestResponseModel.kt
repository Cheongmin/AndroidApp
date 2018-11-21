package com.cheongmin.voicereader.Retrofit.Models

import com.google.gson.annotations.SerializedName

class TestResponseModel {
    data class TestResponse(var message: String,
                                 var errors: List<ErrorModel>,
                                 var documentation_url: String)

    data class ErrorModel(var resource: String,
                    var field: String,
                    var code: String)
}
