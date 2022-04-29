package com.hs.dto.service.output

data class FindProductEventResultDto(
    val productEventId: Long,
    val productId: Long,
    val eventStatus: String,
    val isPublished: Boolean,
)
