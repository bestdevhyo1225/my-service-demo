package com.hs.application.service.command

import com.hs.dto.service.input.UpdateProductDto
import com.hs.event.ProductApplicationEvent
import com.hs.product.entity.Product
import com.hs.product.entity.ProductEvent
import com.hs.product.entity.ProductEventStatus
import com.hs.product.repository.ProductEventRepository
import com.hs.product.repository.ProductRepository
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ProductUpdateCommand(
    private val productRepository: ProductRepository,
    private val productEventRepository: ProductEventRepository,
    private val applicationEventPublisher: ApplicationEventPublisher
) {

    fun execute(serviceDto: UpdateProductDto) {
        val product =
            Product.create(
                id = serviceDto.productId,
                name = serviceDto.name,
                price = serviceDto.price,
                stockQuantity = serviceDto.stockQuantity
            )
        val productEvent =
            ProductEvent.create(productId = serviceDto.productId, eventStatus = ProductEventStatus.UPDATE)

        updateProduct(product)
        saveProductEvent(productEvent)
        publishProductEvent(productEvent, product)
    }

    private fun updateProduct(product: Product) {
        productRepository.update(product = product)
    }

    private fun saveProductEvent(productEvent: ProductEvent) {
        productEventRepository.save(productEvent = productEvent)
    }

    private fun publishProductEvent(productEvent: ProductEvent, product: Product) {
        applicationEventPublisher.publishEvent(
            ProductApplicationEvent(
                productEventId = productEvent.id!!,
                productId = product.id!!,
            )
        )
    }
}
