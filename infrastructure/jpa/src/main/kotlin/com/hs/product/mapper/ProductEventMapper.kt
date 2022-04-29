package com.hs.product.mapper

import com.hs.product.entity.ProductEvent
import com.hs.product.entity.ProductEventJpaEntity

class ProductEventMapper {
    companion object {
        fun toJpaEntity(source: ProductEvent): ProductEventJpaEntity {
            return ProductEventJpaEntity(
                productId = source.productId,
                eventStatus = source.eventStatus,
                isPublished = source.isPublished
            )
        }

        fun toDomain(source: ProductEventJpaEntity): ProductEvent {
            return ProductEvent.create(
                id = source.id!!,
                productId = source.productId,
                eventStatus = source.eventStatus,
                isPublished = source.isPublished,
                publishedAt = source.publishedAt,
                createdAt = source.createdAt,
            )
        }

        fun reflectDomainProperty(source: ProductEventJpaEntity, target: ProductEvent) {
            target.reflectPropertyAfterStorageIsSaved(
                id = source.id!!,
                createdAt = source.createdAt
            )
        }
    }
}
