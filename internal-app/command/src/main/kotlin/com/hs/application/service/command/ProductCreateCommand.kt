package com.hs.application.service.command

import com.hs.dto.service.input.CreateProductDto
import com.hs.dto.service.output.CreateProductResultDto
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
class ProductCreateCommand(
    private val productRepository: ProductRepository,
    private val productEventRepository: ProductEventRepository,
    private val applicationEventPublisher: ApplicationEventPublisher
) {

    fun execute(serviceDto: CreateProductDto): CreateProductResultDto {
        val product =
            Product.create(name = serviceDto.name, price = serviceDto.price, stockQuantity = serviceDto.stockQuantity)

        saveProduct(product)

        val productEvent = ProductEvent.create(productId = product.id!!, eventStatus = ProductEventStatus.CREATE)

        saveProductEvent(productEvent)
        publishProductEvent(productEvent, product)

        return CreateProductResultDto(productId = product.id!!)
    }

    private fun saveProduct(product: Product) {
        productRepository.save(product = product)
    }

    private fun saveProductEvent(productEvent: ProductEvent) {
        productEventRepository.save(productEvent = productEvent)
    }

    private fun publishProductEvent(
        productEvent: ProductEvent,
        product: Product
    ) {
        applicationEventPublisher.publishEvent(
            ProductApplicationEvent(
                productEventId = productEvent.id!!,
                productId = product.id!!,
            )
        )
    }
}
