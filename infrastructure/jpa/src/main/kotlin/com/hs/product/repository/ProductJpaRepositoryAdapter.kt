package com.hs.product.repository

import com.hs.product.entity.Product
import com.hs.product.entity.ProductJpaEntity
import com.hs.product.entity.QProductJpaEntity.productJpaEntity
import com.hs.product.mapper.ProductMapper
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional(readOnly = true)
class ProductJpaRepositoryAdapter(
    private val queryFactory: JPAQueryFactory,
    private val productJpaRepository: ProductJpaRepository
) : ProductRepository {

    @Transactional
    override fun save(product: Product) {
        val productJpaEntity = ProductMapper.toJpaEntity(source = product)
        productJpaRepository.save(productJpaEntity)

        ProductMapper.reflectDomainProperty(source = productJpaEntity, target = product)
    }

    @Transactional
    override fun update(product: Product) {
        val productJpaEntity = findJpaEntityById(productId = product.id!!)
        productJpaEntity.change(name = product.name, price = product.price, stockQuantity = product.stockQuantity)

        ProductMapper.reflectDomainProperty(source = productJpaEntity, target = product)
    }

    override fun findById(productId: Long): Product =
        ProductMapper.toDomain(source = findJpaEntityById(productId))

    override fun findAllByPageable(offset: Long, limit: Long): Pair<List<Product>, Long> {
        TODO("Not yet implemented")
    }

    private fun findJpaEntityById(productId: Long): ProductJpaEntity {
        return queryFactory
            .selectFrom(productJpaEntity)
            .where(productJpaEntityIdEq(productId))
            .fetchOne() ?: throw NoSuchElementException("해당 상품이 존재하지 않습니다.")
    }

    private fun productJpaEntityIdEq(productId: Long): BooleanExpression = productJpaEntity.id.eq(productId)
}

