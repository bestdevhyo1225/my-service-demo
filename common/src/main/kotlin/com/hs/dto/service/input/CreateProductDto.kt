package com.hs.dto.service.input

data class CreateProductDto(
    val name: String,
    val price: Int,
    val stockQuantity: Int
)
