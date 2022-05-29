package com.hs.product

import com.fasterxml.jackson.databind.ObjectMapper
import com.hs.config.RedisConfig
import io.kotest.core.extensions.Extension
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest
import org.springframework.test.context.ContextConfiguration
import reactor.core.publisher.Flux
import reactor.test.StepVerifier

@DataRedisTest
@EnableAutoConfiguration
@ContextConfiguration(classes = [RedisConfig::class, ObjectMapper::class, ProductRedisReactiveRepository::class])
internal class ProductRedisReactiveRepositoryTest : DescribeSpec() {

    override fun extensions(): List<Extension> = listOf(SpringExtension)
    override fun isolationMode(): IsolationMode = IsolationMode.InstancePerLeaf

    @Autowired
    private lateinit var productRedisReactiveRepository: ProductRedisReactiveRepository

    init {
        describe("save 메서드는") {
            it("key와 value를 저장한다.") {
                // given
                val key = "testKey"
                val value = "testValue"
                val expireTimeSeconds = 60L

                // when
                val monoSaveResult = productRedisReactiveRepository.save(
                    key = key,
                    value = value,
                    expireTimeSeconds = expireTimeSeconds
                )

                // then
                monoSaveResult.subscribe { consumer -> consumer.shouldBeTrue() }

                productRedisReactiveRepository
                    .findByKey(key = key)
                    .subscribe { consumer -> consumer.shouldBe(value) }

                productRedisReactiveRepository.delete(key = key)
                    .subscribe { consumer -> consumer.shouldBeTrue() }
            }
        }

        describe("findByKey 메서드는") {
            context("value가 존재하지 않으면") {
                it("null 값을 반환한다.") {
                    // given
                    val key = "testKey"

                    // when
                    val monoValue = productRedisReactiveRepository
                        .findByKey(key = key)
                        .log()

                    // then
                    monoValue.subscribe { consumer -> consumer.shouldBeNull() }
                }
            }

            context("key를 통해") {
                it("value를 반환한다") {
                    // given
                    val key = "testKey"
                    val value = "testValue"
                    val expireTimeSeconds = 60L

                    productRedisReactiveRepository
                        .save(
                            key = key,
                            value = value,
                            expireTimeSeconds = expireTimeSeconds
                        ).subscribe()

                    // when
                    val monoValue = productRedisReactiveRepository
                        .findByKey(key = key)
                        .log()

                    // then
                    monoValue.subscribe { consumer -> consumer.shouldBe(value) }
                }
            }
        }

        describe("findAllByKeys 메서드는") {
            it("keys에 해당되는 values를 가져온다.") {
                // given
                val keysAndValues =
                    listOf(Pair("key1", "value1"), Pair("key2", "value2"), Pair("key3", "value3"))
                val expireTimeSeconds = 60L

                keysAndValues.forEach {
                    productRedisReactiveRepository
                        .save(
                            key = it.first,
                            value = it.second,
                            expireTimeSeconds = expireTimeSeconds
                        ).subscribe { consumer -> consumer.shouldBeTrue() }
                }

                // when
                val fluxValues = productRedisReactiveRepository
                    .findAllByKeys(keys = Flux.fromIterable(keysAndValues.map { it.first }))
                    .log()

                // then
                val values = keysAndValues.map { it.second }

                StepVerifier.create(fluxValues)
                    .assertNext { value ->
                        value.shouldNotBeNull()
                        value.shouldBe(values[0])
                    }
                    .assertNext { value ->
                        value.shouldNotBeNull()
                        value.shouldBe(values[1])
                    }
                    .assertNext { value ->
                        value.shouldNotBeNull()
                        value.shouldBe(values[2])
                    }
                    .verifyComplete()

                keysAndValues.forEach {
                    productRedisReactiveRepository.delete(key = it.first)
                        .subscribe { consumer -> consumer.shouldBeTrue() }
                }
            }
        }

        describe("delete 메서드는") {
            context("value가 존재하지 않으면") {
                it("삭제 명령을 수행하지 않는다.") {
                    // given
                    val key = "testKey"

                    // when
                    val monoDeleteResult = productRedisReactiveRepository.delete(key = key)

                    // then
                    monoDeleteResult.subscribe { consumer -> consumer.shouldBeFalse() }
                }
            }
        }
    }
}
