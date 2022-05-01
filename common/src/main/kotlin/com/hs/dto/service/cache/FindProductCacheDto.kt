package com.hs.dto.service.cache

data class FindProductCacheDto(
    val productId: Long,
    val name: String,
    val price: Int,
    val stockQuantity: Int,
)
