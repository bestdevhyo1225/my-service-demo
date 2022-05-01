package com.hs.application.service

import com.hs.constants.CacheInfo
import com.hs.dto.service.cache.FindProductCacheDto
import com.hs.dto.service.cache.FindSeoulgramsCacheDto
import com.hs.dto.service.output.FindProductMetaDto
import com.hs.product.entity.Product
import com.hs.product.repository.ProductCacheRepository
import com.hs.product.repository.ProductRepository
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

@Service
class ProductFindQuery(
    private val productCacheRepository: ProductCacheRepository,
    private val productDocumentRepository: ProductRepository,
) {

    private val log: Logger = LoggerFactory.getLogger(this.javaClass)

    fun execute(id: Long): FindProductMetaDto = runBlocking {
        val asyncProductCache: Deferred<FindProductCacheDto?> = async(Dispatchers.IO) { getProductCache(id) }
        // val asyncSeoulgramsCache: Deferred<FindSeoulgramsCacheDto?> = async(Dispatchers.IO) { getSeoulgramsCache(id) }

        val productCache = asyncProductCache.await()
        // val seoulgramsCache = asyncSeoulgramsCache.await()

        if (productCache != null) {
            log.info("Cache data is exist. (productId={})", id)

            return@runBlocking FindProductMetaDto(
                product = productCache,
                seoulgrams = listOf()
            )
        }

        log.info("Cache data is not exist. (productId={})", id)

        val product = productDocumentRepository.findById(productId = id)

        launch(Dispatchers.IO) { setProductCache(product) }

        FindProductMetaDto(
            product = FindProductCacheDto(
                productId = product.id!!,
                name = product.name,
                price = product.price,
                stockQuantity = product.stockQuantity
            ),
            seoulgrams = listOf()
        )
    }

    suspend fun getProductCache(id: Long): FindProductCacheDto? = productCacheRepository.get(
        key = CacheInfo.getProductKey(productId = id),
        clazz = FindProductCacheDto::class.java
    )

    suspend fun getSeoulgramsCache(id: Long): FindSeoulgramsCacheDto? = productCacheRepository.get(
        key = CacheInfo.getProductSeoulgramKey(productId = id),
        clazz = FindSeoulgramsCacheDto::class.java
    )

    suspend fun setProductCache(product: Product) {
        productCacheRepository.set(
            key = CacheInfo.getProductKey(productId = product.id!!),
            value = FindProductCacheDto(
                productId = product.id!!,
                name = product.name,
                price = product.price,
                stockQuantity = product.stockQuantity
            ),
            expireTime = CacheInfo.PRODUCT_CACHE_EXPIRE_TIME,
            timeUnit = TimeUnit.SECONDS,
        )
    }
}
