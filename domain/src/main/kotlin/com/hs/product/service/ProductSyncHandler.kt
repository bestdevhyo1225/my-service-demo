package com.hs.product.service

interface ProductSyncHandler {
    fun execute(productId: Long): Any
}
