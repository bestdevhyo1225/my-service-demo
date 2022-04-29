package com.hs.seoulgram.entity

import com.hs.seoulgram.vo.SeoulgramImages
import java.util.Objects

class Seoulgram private constructor(
    id: String?,
    seoulgramId: Long,
    contents: String,
    seoulgramImages: SeoulgramImages,
    productId: Long,
) {

    var id: String? = id
        private set

    var seoulgramId: Long? = seoulgramId
        private set

    var contents: String = contents
        private set

    var seoulgramImages: SeoulgramImages = seoulgramImages
        private set

    var productId: Long = productId
        private set

    override fun hashCode(): Int = Objects.hash(id, seoulgramId, contents, seoulgramImages, productId)
    override fun toString(): String =
        "Seoulgram(id=$id, seoulgramId=$seoulgramId, contents=$contents, seoulgramImages=$seoulgramImages, productId=$productId)"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        val otherSeoulgram = (other as? Seoulgram) ?: return false
        return this.id == otherSeoulgram.id
            && this.seoulgramId == otherSeoulgram.seoulgramId
            && this.contents == otherSeoulgram.contents
            && this.productId == otherSeoulgram.productId
    }

    companion object {
        fun create(seoulgramId: Long, contents: String, seoulgramImages: SeoulgramImages, productId: Long): Seoulgram {
            return Seoulgram(
                id = null,
                seoulgramId = seoulgramId,
                contents = contents,
                seoulgramImages = seoulgramImages,
                productId = productId
            )
        }

        fun create(
            id: String,
            seoulgramId: Long,
            contents: String,
            seoulgramImages: SeoulgramImages,
            productId: Long
        ): Seoulgram {
            return Seoulgram(
                id = id,
                seoulgramId = seoulgramId,
                contents = contents,
                seoulgramImages = seoulgramImages,
                productId = productId
            )
        }
    }
}
