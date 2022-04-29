package com.hs.product.repository

import com.hs.product.entity.Product

interface ProductRepository {
    fun save(product: Product)
    fun update(product: Product)
    fun findById(productId: Long): Product
    fun findAllByPageable(offset: Long, limit: Long): Pair<List<Product>, Long>
}
