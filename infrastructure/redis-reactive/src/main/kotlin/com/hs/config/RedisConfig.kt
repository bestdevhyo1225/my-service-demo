package com.hs.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
@EnableCaching(proxyTargetClass = true)
class RedisConfig(
    @Value("\${spring.redis.host}")
    private val host: String,

    @Value("\${spring.redis.port}")
    private val port: Int,
) {

    @Bean
    fun reactiveRedisConnectionFactory(): ReactiveRedisConnectionFactory {
        return LettuceConnectionFactory(host, port)
    }

    @Bean
    fun reactiveRedisTemplate(): ReactiveRedisTemplate<String, String?> {
        val stringRedisSerializer = StringRedisSerializer()
        val redisSerializationContext = RedisSerializationContext.newSerializationContext<String, String?>()
            .key(stringRedisSerializer)
            .value(stringRedisSerializer)
            .hashKey(stringRedisSerializer)
            .hashValue(stringRedisSerializer)
            .build()

        return ReactiveRedisTemplate(reactiveRedisConnectionFactory(), redisSerializationContext)
    }
}
