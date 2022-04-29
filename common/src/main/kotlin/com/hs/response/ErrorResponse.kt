package com.hs.response

data class ErrorResponse(
    val status: String = "error",
    val message: String,
)
