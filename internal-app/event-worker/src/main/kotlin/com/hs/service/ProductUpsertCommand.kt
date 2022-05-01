package com.hs.service

import com.hs.constants.CacheInfo
import com.hs.dto.service.cache.FindProductCacheDto
import com.hs.dto.service.output.FindProductResultDto
import com.hs.product.entity.Product
import com.hs.product.repository.ProductCacheRepository
import com.hs.product.repository.ProductEventRepository
import com.hs.product.repository.ProductRepository
import com.hs.product.service.ProductSyncHandler
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

@Service
class ProductUpsertCommand(
    private val productSyncHandler: ProductSyncHandler,
    @Qualifier(value = "productDocumentRepositoryAdapter")
    private val productDocumentRepository: ProductRepository,
    private val productEventRepository: ProductEventRepository,
    private val productCacheRepository: ProductCacheRepository,
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

        productCacheRepository.set(
            key = CacheInfo.getProductKey(productId = productId),
            value = FindProductCacheDto(
                productId = productToSync.productId,
                name = productToSync.name,
                price = productToSync.price,
                stockQuantity = productToSync.stockQuantity
            ),
            expireTime = CacheInfo.PRODUCT_CACHE_EXPIRE_TIME,
            timeUnit = TimeUnit.SECONDS,
        )
        productEventRepository.updatePublishedStatus(productEventId = productEventId, productId = productId)
    }
}
