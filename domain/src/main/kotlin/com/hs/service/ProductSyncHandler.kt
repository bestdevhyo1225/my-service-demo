package com.hs.service

interface ProductSyncHandler {
    fun request(productId: Long): Any
}
