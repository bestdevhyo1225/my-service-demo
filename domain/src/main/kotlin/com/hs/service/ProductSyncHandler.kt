package com.hs.service

interface ProductSyncHandler {
    fun execute(productId: Long): Any
}
