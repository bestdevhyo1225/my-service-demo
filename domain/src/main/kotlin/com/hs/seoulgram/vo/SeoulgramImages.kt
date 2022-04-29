package com.hs.seoulgram.vo

import java.util.Objects

class SeoulgramImages private constructor(
    val seoulgramImages: List<SeoulgramImage>
) {

    override fun hashCode(): Int = Objects.hash(seoulgramImages)
    override fun toString(): String = "SeoulgramImages(seoulgramImages=$seoulgramImages)"
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        val otherSeoulgramImages = (other as? SeoulgramImages) ?: return false
        return this.seoulgramImages == otherSeoulgramImages.seoulgramImages
    }

    companion object {
        fun create(seoulgramImages: List<SeoulgramImage>): SeoulgramImages {
            validateSize(seoulgramImages = seoulgramImages)
            return SeoulgramImages(seoulgramImages = seoulgramImages)
        }

        private fun validateSize(seoulgramImages: List<SeoulgramImage>) {
            if (seoulgramImages.size >= 10) {
                throw IllegalArgumentException("이미지는 최대 10개까지 가능합니다.")
            }
        }
    }
}
