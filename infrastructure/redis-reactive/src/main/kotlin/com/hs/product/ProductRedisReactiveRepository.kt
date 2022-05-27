package com.hs.product

import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import java.time.Duration

@Repository
class ProductRedisReactiveRepository(
    private val reactiveRedisTemplate: ReactiveRedisTemplate<String, String?>
) {

    fun save(key: String, value: String, expireTimeSeconds: Long): Mono<Boolean> {
        return reactiveRedisTemplate.opsForValue()
            .set(key, value, Duration.ofSeconds(expireTimeSeconds))
    }

    fun findByKey(key: String): Mono<String?> {
        return reactiveRedisTemplate.opsForValue()
            .get(key)
    }

    fun findAllByKeys(keys: Flux<String>): Flux<String?> {
        return keys.flatMap { key -> findByKey(key = key).publishOn(Schedulers.parallel()) }
    }

    fun delete(key: String): Mono<Boolean> {
        return reactiveRedisTemplate.opsForValue()
            .delete(key)
    }
}
