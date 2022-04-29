package com.hs.dto

data class FindAllPageableDto<T>(
    val items: List<T>,
    val start: Long,
    val count: Long,
    val total: Long,
)
