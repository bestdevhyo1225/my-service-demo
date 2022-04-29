package com.hs.product.entity

import java.time.LocalDateTime
import java.util.Objects

class Product private constructor(
    id: Long?, name: String, price: Int, stockQuantity: Int, createdAt: LocalDateTime?, updatedAt: LocalDateTime?
) {

    var id: Long? = id
        private set

    var name: String = name
        private set

    var price: Int = price
        private set

    var stockQuantity: Int = stockQuantity
        private set

    var createdAt: LocalDateTime? = createdAt
        private set

    var updatedAt: LocalDateTime? = updatedAt
        private set

    override fun hashCode(): Int = Objects.hash(id, name, price, stockQuantity, createdAt, updatedAt)
    override fun toString(): String =
        "Product(id=$id, name=$name, price=$price, stockQuantity=$stockQuantity, createdAt=$createdAt, updatedAt=$updatedAt)"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        val otherProduct = (other as? Product) ?: return false
        return this.id == otherProduct.id
            && this.name == otherProduct.name
            && this.price == otherProduct.price
            && this.stockQuantity == otherProduct.stockQuantity
            && this.createdAt == otherProduct.createdAt
            && this.updatedAt == otherProduct.updatedAt
    }

    companion object {
        fun create(name: String, price: Int, stockQuantity: Int): Product {
            return Product(
                id = null,
                name = name,
                price = price,
                stockQuantity = stockQuantity,
                createdAt = null,
                updatedAt = null,
            )
        }

        fun create(id: Long, name: String, price: Int, stockQuantity: Int): Product {
            return Product(
                id = id,
                name = name,
                price = price,
                stockQuantity = stockQuantity,
                createdAt = null,
                updatedAt = null,
            )
        }

        fun create(
            id: Long, name: String, price: Int, stockQuantity: Int, createdAt: LocalDateTime, updatedAt: LocalDateTime
        ): Product {
            return Product(
                id = id,
                name = name,
                price = price,
                stockQuantity = stockQuantity,
                createdAt = createdAt,
                updatedAt = updatedAt
            )
        }
    }

    fun reflectPropertyAfterStorageIsSaved(id: Long, createdAt: LocalDateTime, updatedAt: LocalDateTime) {
        this.id = id
        this.createdAt = createdAt
        this.updatedAt = updatedAt
    }
}
