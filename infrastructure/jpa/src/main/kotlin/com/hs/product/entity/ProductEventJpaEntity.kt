package com.hs.product.entity

import org.hibernate.annotations.DynamicUpdate
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "product_event")
@DynamicUpdate
class ProductEventJpaEntity(
    productId: Long,
    eventStatus: ProductEventStatus,
    isPublished: Boolean,
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @Column(nullable = false)
    var productId: Long = productId
        protected set

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var eventStatus: ProductEventStatus = eventStatus
        protected set

    @Column(nullable = false)
    var isPublished: Boolean = isPublished
        protected set

    @Column(columnDefinition = "DATETIME")
    var publishedAt: LocalDateTime? = null
        protected set

    @Column(nullable = false, columnDefinition = "DATETIME")
    var createdAt: LocalDateTime = LocalDateTime.now().withNano(0)
        protected set

    fun changePublishedStatus() {
        this.isPublished = true
        this.publishedAt = LocalDateTime.now().withNano(0)
    }
}
