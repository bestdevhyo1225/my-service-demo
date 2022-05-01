package com.hs.product.mapper

import com.hs.product.entity.Product
import com.hs.product.entity.ProductJpaEntity

object ProductMapper {

    fun toJpaEntity(source: Product): ProductJpaEntity {
        return ProductJpaEntity(
            name = source.name,
            price = source.price,
            stockQuantity = source.stockQuantity,
        )
    }

    fun toDomain(source: ProductJpaEntity): Product {
        return Product.create(
            id = source.id!!,
            name = source.name,
            price = source.price,
            stockQuantity = source.stockQuantity,
            createdAt = source.createdAt,
            updatedAt = source.updatedAt
        )
    }

    fun reflectDomainProperty(source: ProductJpaEntity, target: Product) {
        target.reflectPropertyAfterStorageIsSaved(
            id = source.id!!,
            createdAt = source.createdAt,
            updatedAt = source.updatedAt
        )
    }
}
