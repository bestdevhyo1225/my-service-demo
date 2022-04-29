package com.hs.product.document

import com.hs.utils.DateTimeFormatterUtils
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import java.time.LocalDateTime

@Document("products")
class ProductDocument private constructor(
    productId: Long,
    name: String,
    price: Int,
    stockQuantity: Int,
) {

    @Id
    var id: ObjectId = ObjectId.get()

    @Field
    final var productId: Long = productId
        private set

    @Field
    final var name: String = name
        private set

    @Field
    final var price: Int = price
        private set

    @Field
    final var stockQuantity: Int = stockQuantity
        private set

    @Field
    final var createdAt: String = DateTimeFormatterUtils.toStringDateTime(LocalDateTime.now())
        private set

    @Field
    final var updatedAt: String = DateTimeFormatterUtils.toStringDateTime(LocalDateTime.now())
        private set

    override fun hashCode(): Int = id.hashCode()
    override fun toString(): String = "ProductDocument(id=$id, productId=$productId, name=$name, price=$price," +
        "stockQuantity=$stockQuantity, createdAt=$createdAt, updatedAt=$updatedAt)"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        val otherProductDocument = (other as? ProductDocument) ?: return false
        return this.id == otherProductDocument.id
            && this.productId == otherProductDocument.productId
            && this.name == otherProductDocument.name
            && this.price == otherProductDocument.price
            && this.stockQuantity == otherProductDocument.stockQuantity
    }

    companion object {
        fun create(productId: Long, name: String, price: Int, stockQuantity: Int): ProductDocument {
            return ProductDocument(productId = productId, name = name, price = price, stockQuantity = stockQuantity)
        }
    }

    fun change(name: String, price: Int, stockQuantity: Int) {
        this.name = name
        this.price = price
        this.stockQuantity = stockQuantity
        this.updatedAt = DateTimeFormatterUtils.toStringDateTime(LocalDateTime.now())
    }
}
