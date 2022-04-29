package com.hs.product.entity

import java.time.LocalDateTime
import java.util.Objects

class ProductEvent private constructor(
    id: Long?,
    productId: Long,
    eventStatus: ProductEventStatus,
    isPublished: Boolean,
    publishedAt: LocalDateTime?,
    createdAt: LocalDateTime?
) {

    var id: Long? = id
        private set

    var productId: Long = productId
        private set

    var eventStatus: ProductEventStatus = eventStatus
        private set

    var isPublished: Boolean = isPublished
        private set

    var publishedAt: LocalDateTime? = publishedAt
        private set

    var createdAt: LocalDateTime? = createdAt
        private set

    override fun hashCode(): Int = Objects.hash(id, productId, eventStatus, isPublished, publishedAt, createdAt)
    override fun toString(): String =
        "ProductEvent(id=$id, productId=$productId, eventStatus=$eventStatus, isPublished=$isPublished, " +
            "publishedAt=$publishedAt, createdAt=$createdAt)"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        val otherProductEvent = (other as? ProductEvent) ?: return false
        return this.id == otherProductEvent.id
            && this.productId == otherProductEvent.productId
            && this.eventStatus == otherProductEvent.eventStatus
            && this.isPublished == otherProductEvent.isPublished
            && this.publishedAt == otherProductEvent.publishedAt
            && this.createdAt == otherProductEvent.createdAt
    }

    companion object {
        fun create(productId: Long, eventStatus: ProductEventStatus): ProductEvent {
            return ProductEvent(
                id = null,
                productId = productId,
                eventStatus = eventStatus,
                isPublished = false,
                publishedAt = null,
                createdAt = null
            )
        }

        fun create(
            id: Long,
            productId: Long,
            eventStatus: ProductEventStatus,
            isPublished: Boolean,
            publishedAt: LocalDateTime?,
            createdAt: LocalDateTime
        ): ProductEvent {
            return ProductEvent(
                id = id,
                productId = productId,
                eventStatus = eventStatus,
                isPublished = isPublished,
                publishedAt = publishedAt,
                createdAt = createdAt
            )
        }
    }

    fun reflectPropertyAfterStorageIsSaved(id: Long, createdAt: LocalDateTime) {
        this.id = id
        this.createdAt = createdAt
    }
}
