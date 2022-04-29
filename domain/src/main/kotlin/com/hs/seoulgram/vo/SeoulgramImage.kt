package com.hs.seoulgram.vo

import java.util.Objects

class SeoulgramImage private constructor(
    seoulgramImageId: Long,
    imageUrl: String,
    sortOrder: Int,
) {

    var seoulgramImageId: Long = seoulgramImageId
        private set

    var imageUrl: String = imageUrl
        private set

    var sortOrder: Int = sortOrder
        private set

    override fun hashCode(): Int = Objects.hash(seoulgramImageId, imageUrl, sortOrder)
    override fun toString(): String =
        "SeoulgramImage(seoulgramImageId=$seoulgramImageId, imageUrl=$imageUrl, sortOrder=$sortOrder)"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        val otherSeoulgramImage = (other as? SeoulgramImage) ?: return false
        return this.seoulgramImageId == otherSeoulgramImage.seoulgramImageId
            && this.imageUrl == otherSeoulgramImage.imageUrl
            && this.sortOrder == otherSeoulgramImage.sortOrder
    }

    companion object {
        fun create(seoulgramImageId: Long, imageUrl: String, sortOrder: Int): SeoulgramImage {
            return SeoulgramImage(seoulgramImageId = seoulgramImageId, imageUrl = imageUrl, sortOrder = sortOrder)
        }
    }
}
