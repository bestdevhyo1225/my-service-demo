package com.hs.application.service.query

import com.hs.dto.FindAllPageableDto
import com.hs.dto.service.output.FindProductEventResultDto
import com.hs.product.repository.ProductEventRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ProductEventFindQuery(
    private val productEventRepository: ProductEventRepository
) {

    fun execute(productId: Long, start: Long, count: Long): FindAllPageableDto<FindProductEventResultDto> {
        val productEvents =
            productEventRepository.findAllByProductId(productId = productId, offset = start, limit = count)

        return FindAllPageableDto(
            items = productEvents.first.map {
                FindProductEventResultDto(
                    productEventId = it.id!!,
                    productId = it.productId,
                    eventStatus = it.eventStatus.name,
                    isPublished = it.isPublished
                )
            },
            start = start,
            count = count,
            total = productEvents.second
        )
    }
}
