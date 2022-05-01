package com.hs.constants

object CacheInfo {
    const val PRODUCT_CACHE_EXPIRE_TIME: Long = 60 * 10 // 10분

    fun getProductKey(productId: Long) = "products:${productId}"
    fun getProductSeoulgramKey(productId: Long) = "products:${productId}:seoulgrams"
}
