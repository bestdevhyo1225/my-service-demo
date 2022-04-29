package com.hs.product.repository

import com.hs.product.document.ProductDocument
import com.hs.product.entity.Product
import com.hs.product.mapper.ProductDocumentMapper
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.isEqualTo
import org.springframework.stereotype.Repository

@Repository
class ProductDocumentRepositoryAdapter(private val mongoTemplate: MongoTemplate) : ProductRepository {

    override fun save(product: Product) {
        val productDocument = mongoTemplate.insert(ProductDocumentMapper.toDocument(product = product))
        ProductDocumentMapper.reflectDomainProperty(source = productDocument, target = product)
    }

    override fun update(product: Product) {
        val productDocument = findProductDocumentByProductId(productId = product.id!!)
        productDocument.change(name = product.name, price = product.price, stockQuantity = product.stockQuantity)
        mongoTemplate.save(productDocument)
        ProductDocumentMapper.reflectDomainProperty(source = productDocument, target = product)
    }

    override fun findById(productId: Long): Product =
        ProductDocumentMapper.toDomain(source = findProductDocumentByProductId(productId = productId))

    override fun findAllByPageable(offset: Long, limit: Long): Pair<List<Product>, Long> {
        TODO("Not yet implemented")
    }

    private fun findProductDocumentByProductId(productId: Long): ProductDocument {
        val criteria = Criteria.where("productId").isEqualTo(productId)
        return mongoTemplate.findOne(Query(criteria), ProductDocument::class.java)
            ?: throw NoSuchElementException("Product가 존재하지 않습니다.")
    }
}
