package com.hs.dto.service.output

import com.hs.dto.service.cache.FindProductCacheDto
import com.hs.dto.service.cache.FindSeoulgramCacheDto

data class FindProductMetaDto(
    val product: FindProductCacheDto,
    val seoulgrams: List<FindSeoulgramCacheDto>
)
