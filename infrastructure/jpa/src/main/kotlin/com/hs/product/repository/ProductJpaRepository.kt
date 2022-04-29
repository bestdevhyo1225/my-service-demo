package com.hs.product.repository

import com.hs.product.entity.ProductJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ProductJpaRepository : JpaRepository<ProductJpaEntity, Long>
