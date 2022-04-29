package com.hs.product.repository

import com.hs.product.entity.ProductEvent

interface ProductEventRepository {
    fun save(productEvent: ProductEvent)
    fun updatePublishedStatus(productEventId: Long, productId: Long)
    fun findAllByProductId(productId: Long, offset: Long, limit: Long): Pair<List<ProductEvent>, Long>
}
