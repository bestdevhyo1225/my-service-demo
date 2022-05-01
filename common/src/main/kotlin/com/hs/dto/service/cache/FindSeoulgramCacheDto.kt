package com.hs.dto.service.cache

data class FindSeoulgramCacheDto(
    val seoulgramId: Long,
    val contents: String,
    val imageUrls: List<String>,
    val sortOrders: List<Int>,
)
