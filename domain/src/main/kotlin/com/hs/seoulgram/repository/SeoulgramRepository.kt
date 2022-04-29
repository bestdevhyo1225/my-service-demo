package com.hs.seoulgram.repository

import com.hs.seoulgram.entity.Seoulgram

interface SeoulgramRepository {
    fun save(seoulgram: Seoulgram)
    fun update(seoulgram: Seoulgram)
    fun findById(seoulgramId: Long): Seoulgram
}
