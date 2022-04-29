package com.hs.dto.service.input

data class UpdateProductDto(
    val productId: Long,
    val name: String,
    val price: Int,
    val stockQuantity: Int
)
