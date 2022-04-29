package com.hs.dto.service.output

import java.time.LocalDateTime

data class FindProductResultDto(
    val productId: Long,
    val name: String,
    val price: Int,
    val stockQuantity: Int,
    var createdAt: String,
    var updatedAt: String,
)
