package com.hs.repository

import com.fasterxml.jackson.databind.ObjectMapper
import com.hs.product.repository.ProductCacheRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository
import java.util.concurrent.TimeUnit

@Repository
class ProductRedisRepository(
    private val objectMapper: ObjectMapper,
    private val redisTemplate: RedisTemplate<String, String?>
) : ProductCacheRepository {

    private val log: Logger = LoggerFactory.getLogger(this.javaClass)

    override fun <T : Any> set(key: String, value: T, expireTime: Long, timeUnit: TimeUnit) {
        log.debug("Redis set key={}, value={}", key, value)

        redisTemplate.opsForValue().set(key, objectMapper.writeValueAsString(value))
        redisTemplate.expire(key, expireTime, timeUnit)
    }

    override fun <T> get(key: String, clazz: Class<T>): T? {
        val value = redisTemplate.opsForValue().get(key)

        log.debug("Redis get value={}", value)

        if (value == null || value.isBlank()) {
            return null
        }

        return objectMapper.readValue(value, clazz)
    }

    override fun <T> getList(keys: List<String>): List<T?> {
        TODO("Not yet implemented")
    }
}
