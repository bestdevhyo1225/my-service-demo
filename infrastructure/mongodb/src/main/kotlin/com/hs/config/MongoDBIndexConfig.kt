package com.hs.config

import org.bson.Document
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.index.CompoundIndexDefinition
import javax.annotation.PostConstruct

@Configuration
class MongoDBIndexConfig(
    private val mongoTemplate: MongoTemplate
) {

    @PostConstruct
    fun ensureIndexes() {
        // 단일 조회를 위한 Index 설정
        mongoTemplate
            .indexOps("products")
            .ensureIndex(CompoundIndexDefinition(Document().append("productId", 1)))
    }
}
