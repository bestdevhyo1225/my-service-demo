package com.hs.product.repository

import com.hs.product.entity.ProductEventJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ProductEventJpaRepository : JpaRepository<ProductEventJpaEntity, Long>
