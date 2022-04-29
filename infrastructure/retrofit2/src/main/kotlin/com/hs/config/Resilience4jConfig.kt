package com.hs.config

import io.github.resilience4j.circuitbreaker.CircuitBreaker
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.SlidingWindowType
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.io.IOException
import java.net.ConnectException
import java.time.Duration
import java.util.concurrent.TimeoutException

@Configuration
class Resilience4jConfig(
    @Value("\${resilience4j.circuitbreaker.custom.slidingWindowType}")
    private val slidingWindowType: SlidingWindowType,

    @Value("\${resilience4j.circuitbreaker.custom.slidingWindowSize}")
    private val slidingWindowSize: Int,

    @Value("\${resilience4j.circuitbreaker.custom.minimumNumberOfCalls}")
    private val minimumNumberOfCalls: Int,

    @Value("\${resilience4j.circuitbreaker.custom.failureRateThreshold}")
    private val failureRateThreshold: Float,

    @Value("\${resilience4j.circuitbreaker.custom.waitDurationInOpenState}")
    private val waitDurationInOpenState: Long,
) {

    object Api {
        const val COMMAND = "CommandApi"
    }

    @Bean
    fun circuitBreakerRegistry(): CircuitBreakerRegistry {
        return CircuitBreakerRegistry.of(
            CircuitBreakerConfig.custom()
                .slidingWindowType(slidingWindowType)
                .slidingWindowSize(slidingWindowSize)
                .minimumNumberOfCalls(minimumNumberOfCalls)
                .failureRateThreshold(failureRateThreshold)
                .waitDurationInOpenState(Duration.ofMillis(waitDurationInOpenState))
                .recordExceptions(IOException::class.java, ConnectException::class.java, TimeoutException::class.java)
                .build()
        )
    }

    @Bean(name = ["commandApiCircuitBreaker"])
    fun commandApiCircuitBreaker(): CircuitBreaker {
        return circuitBreakerRegistry().circuitBreaker(Api.COMMAND)
    }
}
