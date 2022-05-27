package com.hs.product.mapper

import com.hs.product.document.ProductDocument
import com.hs.product.entity.Product
import com.hs.utils.DateTimeFormatterUtils

object ProductDocumentMapper {

    fun toDocument(product: Product): ProductDocument {
        return ProductDocument.create(
            productId = product.id!!,
            name = product.name,
            price = product.price,
            stockQuantity = product.stockQuantity
        )
    }

    fun toDomain(source: ProductDocument): Product {
        return Product.create(
            id = source.productId,
            name = source.name,
            price = source.price,
            stockQuantity = source.stockQuantity,
            createdAt = DateTimeFormatterUtils.toLocalDateTime(stringDateTime = source.createdAt),
            updatedAt = DateTimeFormatterUtils.toLocalDateTime(stringDateTime = source.updatedAt)
        )
    }

    fun reflectDomainProperty(source: ProductDocument, target: Product) {
        target.reflectPropertyAfterStorageIsSaved(
            id = source.productId,
            createdAt = DateTimeFormatterUtils.toLocalDateTime(stringDateTime = source.createdAt),
            updatedAt = DateTimeFormatterUtils.toLocalDateTime(stringDateTime = source.updatedAt),
        )
    }
}
