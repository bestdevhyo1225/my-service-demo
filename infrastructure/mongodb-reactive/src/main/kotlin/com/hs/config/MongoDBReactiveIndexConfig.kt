package com.hs.config

import org.bson.Document
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.index.CompoundIndexDefinition
import javax.annotation.PostConstruct

@Configuration
class MongoDBReactiveIndexConfig(
    private val productReactiveMongoTemplate: ReactiveMongoTemplate
) {

    @PostConstruct
    fun ensureIndexes() {
        // 단일 조회를 위한 Index 설정
        productReactiveMongoTemplate
            .indexOps("products")
            .ensureIndex(CompoundIndexDefinition(Document().append("productId", 1)))
            .subscribe()
    }
}
