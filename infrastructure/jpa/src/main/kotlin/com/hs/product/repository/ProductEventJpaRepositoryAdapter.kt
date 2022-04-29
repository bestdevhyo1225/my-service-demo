package com.hs.product.repository

import com.hs.product.entity.ProductEvent
import com.hs.product.entity.ProductEventJpaEntity
import com.hs.product.entity.QProductEventJpaEntity.productEventJpaEntity
import com.hs.product.mapper.ProductEventMapper
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional(readOnly = true)
class ProductEventJpaRepositoryAdapter(
    private val queryFactory: JPAQueryFactory,
    private val productEventJpaRepository: ProductEventJpaRepository
) : ProductEventRepository {

    @Transactional
    override fun save(productEvent: ProductEvent) {
        val productEventJpaEntity = ProductEventMapper.toJpaEntity(source = productEvent)
        productEventJpaRepository.save(productEventJpaEntity)

        ProductEventMapper.reflectDomainProperty(source = productEventJpaEntity, target = productEvent)
    }

    @Transactional
    override fun updatePublishedStatus(productEventId: Long, productId: Long) {
        val productEventJpaEntity =
            findJpaEntityByIdAndProductId(productEventId = productEventId, productId = productId)
        productEventJpaEntity.changePublishedStatus()
    }

    override fun findAllByProductId(productId: Long, offset: Long, limit: Long): Pair<List<ProductEvent>, Long> {
        val productJpaEntities = queryFactory
            .selectFrom(productEventJpaEntity)
            .where(productEventProductIdEq(productId))
            .offset(offset)
            .limit(limit)
            .fetch()

        val productJpaEntityCount = queryFactory
            .selectFrom(productEventJpaEntity)
            .where(productEventProductIdEq(productId))
            .fetch()
            .size.toLong()

        return Pair(
            first = productJpaEntities.map { ProductEventMapper.toDomain(source = it) },
            second = productJpaEntityCount
        )
    }

    private fun findJpaEntityByIdAndProductId(productEventId: Long, productId: Long): ProductEventJpaEntity {
        return queryFactory
            .selectFrom(productEventJpaEntity)
            .where(
                productEventIdEq(productEventId),
                productEventProductIdEq(productId)
            )
            .fetchOne() ?: throw NoSuchElementException("해당 상품 이벤트가 존재하지 않습니다.")
    }

    private fun productEventIdEq(productEventId: Long): BooleanExpression = productEventJpaEntity.id.eq(productEventId)

    private fun productEventProductIdEq(productId: Long): BooleanExpression =
        productEventJpaEntity.productId.eq(productId)
}
