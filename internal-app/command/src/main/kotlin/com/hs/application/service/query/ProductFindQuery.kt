package com.hs.application.service.query

import com.hs.dto.service.output.FindProductResultDto
import com.hs.product.repository.ProductRepository
import com.hs.utils.DateTimeFormatterUtils
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ProductFindQuery(
    private val productRepository: ProductRepository
) {

    fun execute(productId: Long): FindProductResultDto {
        val product = productRepository.findById(productId = productId)

        return FindProductResultDto(
            productId = product.id!!,
            name = product.name,
            price = product.price,
            stockQuantity = product.stockQuantity,
            createdAt = DateTimeFormatterUtils.toStringDateTime(localDateTime = product.createdAt!!),
            updatedAt = DateTimeFormatterUtils.toStringDateTime(localDateTime = product.updatedAt!!)
        )
    }
}
