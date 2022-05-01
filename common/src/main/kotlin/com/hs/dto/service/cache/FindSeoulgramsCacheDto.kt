package com.hs.dto.service.cache

data class FindSeoulgramsCacheDto(
    val productId: Long,
    val seoulgrams: List<FindSeoulgramCacheDto>
)
