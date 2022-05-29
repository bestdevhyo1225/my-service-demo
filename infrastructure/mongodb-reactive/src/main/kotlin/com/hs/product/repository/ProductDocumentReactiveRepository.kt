package com.hs.product.repository

import com.hs.product.document.ProductDocument
import com.hs.product.entity.Product
import com.hs.product.mapper.ProductDocumentMapper
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.isEqualTo
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty

@Repository
class ProductDocumentReactiveRepository(
    private val productReactiveMongoTemplate: ReactiveMongoTemplate
) {

    fun save(product: Product) {
        productReactiveMongoTemplate
            .insert(ProductDocumentMapper.toDocument(product = product))
            .flatMap { Mono.just(ProductDocumentMapper.reflectDomainProperty(source = it, target = product)) }
            .subscribe()
    }

    fun update(product: Product) {
        productReactiveMongoTemplate.save(ProductDocumentMapper.toDocument(product = product))
            .flatMap { Mono.just(ProductDocumentMapper.reflectDomainProperty(source = it, target = product)) }
            .subscribe()
    }

    fun findById(productId: Long): Mono<Product> {
        return findProductDocumentByProductId(productId = productId)
            .flatMap { Mono.just(ProductDocumentMapper.toDomain(source = it)) }
    }

    fun deleteById(productId: Long): Mono<Long> {
        return productReactiveMongoTemplate
            .remove(Query(getCriteriaProductId(productId)), ProductDocument::class.java)
            .flatMap { Mono.just(it.deletedCount) }
    }

    private fun findProductDocumentByProductId(productId: Long): Mono<ProductDocument> {
        return productReactiveMongoTemplate
            .findOne(Query(getCriteriaProductId(productId)), ProductDocument::class.java)
            .flatMap { Mono.just(it) }
            .switchIfEmpty { Mono.error { NoSuchElementException("product document is not exist.") } }
    }

    private fun getCriteriaProductId(productId: Long): Criteria {
        return Criteria.where("productId").isEqualTo(productId)
    }
}
