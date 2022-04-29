package com.hs.service

import com.hs.dto.service.output.FindProductResultDto
import com.hs.product.entity.Product
import com.hs.product.repository.ProductEventRepository
import com.hs.product.repository.ProductRepository
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

@Service
class ProductUpsertCommand(
    private val productSyncHandler: ProductSyncHandler,
    @Qualifier(value = "productDocumentRepositoryAdapter")
    private val productDocumentRepository: ProductRepository,
    private val productEventRepository: ProductEventRepository,
) {

    fun execute(productEventId: Long, productId: Long) {
        val productToSync = productSyncHandler.execute(productId = productId) as FindProductResultDto
        val product = Product.create(
            id = productToSync.productId,
            name = productToSync.name,
            price = productToSync.price,
            stockQuantity = productToSync.stockQuantity
        )

        try {
            productDocumentRepository.update(product = product)
        } catch (exception: NoSuchElementException) {
            productDocumentRepository.save(product = product)
        }

        // Redis 데이터 갱신

        productEventRepository.updatePublishedStatus(productEventId = productEventId, productId = productId)
    }
}
