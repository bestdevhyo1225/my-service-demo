package com.hs.response

data class FailResponse(
    val status: String = "fail",
    val message: String,
)
