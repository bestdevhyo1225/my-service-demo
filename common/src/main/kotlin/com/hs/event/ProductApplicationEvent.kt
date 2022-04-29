package com.hs.event

data class ProductApplicationEvent(
    val productEventId: Long,
    val productId: Long,
)
