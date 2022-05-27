package com.hs.application.service

import com.hs.config.RedisExpiryTime
import com.hs.config.RedisKey
import com.hs.dto.service.cache.FindProductCacheDto
import com.hs.dto.service.output.FindProductMetaDto
import com.hs.product.ProductRedisReactiveRepository
import com.hs.product.repository.ProductDocumentReactiveRepository
import com.hs.utils.JacksonObjectMapperUtils
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import reactor.kotlin.core.publisher.switchIfEmpty

@Service
class ProductFindReactiveQuery(
    private val productDocumentReactiveRepository: ProductDocumentReactiveRepository,
    private val productRedisReactiveRepository: ProductRedisReactiveRepository
) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    fun execute(id: Long): Mono<FindProductMetaDto> {
        return findProductCache(key = RedisKey.getProductKey(id = id))
            .flatMap {
                Mono.just(
                    FindProductMetaDto(
                        product = JacksonObjectMapperUtils.convertObject(
                            value = it!!,
                            clazz = FindProductCacheDto::class.java
                        ),
                        seoulgrams = listOf()
                    )
                )
            }
            .switchIfEmpty {
                logger.info("cache of product meta dto is not exist.")
                findProduct(id = id)
            }
    }

    private fun findProductCache(key: String): Mono<String?> = productRedisReactiveRepository.findByKey(key = key)

    private fun findProduct(id: Long): Mono<FindProductMetaDto> {
        return productDocumentReactiveRepository
            .findById(productId = id)
            .flatMap {
                val findProductCacheDto = FindProductCacheDto(
                    productId = it.id!!,
                    name = it.name,
                    price = it.price,
                    stockQuantity = it.stockQuantity
                )
                createProductCache(findProductCacheDto = findProductCacheDto)
                Mono.just(FindProductMetaDto(product = findProductCacheDto, seoulgrams = listOf()))
            }
    }

    private fun createProductCache(findProductCacheDto: FindProductCacheDto) {
        productRedisReactiveRepository
            .save(
                key = RedisKey.getProductKey(id = findProductCacheDto.productId),
                value = JacksonObjectMapperUtils.convertString(data = findProductCacheDto),
                expireTimeSeconds = RedisExpiryTime.PRODUCT
            )
            .publishOn(Schedulers.boundedElastic())
            .subscribe { logger.info("saved product metadata (isSaved: {})", it) }
    }
}
