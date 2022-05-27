package com.hs.config

import com.mongodb.reactivestreams.client.MongoClient
import com.mongodb.reactivestreams.client.MongoClients
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.ReactiveMongoDatabaseFactory
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.SimpleReactiveMongoDatabaseFactory
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper
import org.springframework.data.mongodb.core.convert.MappingMongoConverter
import org.springframework.data.mongodb.core.mapping.MongoMappingContext

@Configuration
class MongoDBReactiveConfig(
    @Value("\${spring.data.mongodb.uri}")
    private val mongodbUri: String,

    @Value("\${spring.data.mongodb.database}")
    private val database: String
) {

    @Bean
    fun reactiveMongoClient(): MongoClient {
        return MongoClients.create(mongodbUri)
    }

    @Bean
    fun productReactiveMongoDatabaseFactory(): ReactiveMongoDatabaseFactory {
        return SimpleReactiveMongoDatabaseFactory(reactiveMongoClient(), database)
    }

    @Bean
    fun productMappingMongoConverter(): MappingMongoConverter {
        val converter = MappingMongoConverter(ReactiveMongoTemplate.NO_OP_REF_RESOLVER, MongoMappingContext())
        converter.setTypeMapper(DefaultMongoTypeMapper(null))
        return converter
    }

    @Bean
    fun productReactiveMongoTemplate(): ReactiveMongoTemplate {
        return ReactiveMongoTemplate(productReactiveMongoDatabaseFactory(), productMappingMongoConverter())
    }
}
