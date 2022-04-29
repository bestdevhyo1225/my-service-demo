package com.hs.config

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.hs.accessor.CommandApiAccessor
import io.github.resilience4j.circuitbreaker.CircuitBreaker
import io.github.resilience4j.retrofit.CircuitBreakerCallAdapter
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.TimeUnit

@Configuration
class Retrofit2Config(
    private val commandApiCircuitBreaker: CircuitBreaker,

    @Value("\${retrofit.connectionTimeoutSeconds}")
    private val connectionTimeoutSeconds: Long,

    @Value("\${retrofit.writeTimeoutSeconds}")
    private val writeTimeoutSeconds: Long,

    @Value("\${retrofit.readTimeoutSeconds}")
    private val readTimeoutSeconds: Long,

    @Value("\${retrofit.debugMode}")
    private val debugMode: Boolean,

    @Value("\${api.command.host}")
    private val commandApiHost: String,
) {

    @Bean
    fun okHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(connectionTimeoutSeconds, TimeUnit.SECONDS)
            .writeTimeout(writeTimeoutSeconds, TimeUnit.SECONDS)
            .readTimeout(readTimeoutSeconds, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor())
            .build()
    }

    private fun httpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor()
            .setLevel(if (debugMode) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE)
    }

    @Bean(name = ["commandApiRetrofit"])
    fun commandApiRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("$commandApiHost/")
            .addConverterFactory(JacksonConverterFactory.create(jacksonObjectMapper()))
            .addCallAdapterFactory(CircuitBreakerCallAdapter.of(commandApiCircuitBreaker))
            .client(okHttpClient())
            .build()
    }

    @Bean(name = ["commandApiAccessor"])
    fun commandApiAccessor(): CommandApiAccessor {
        return commandApiRetrofit().create(CommandApiAccessor::class.java)
    }
}
