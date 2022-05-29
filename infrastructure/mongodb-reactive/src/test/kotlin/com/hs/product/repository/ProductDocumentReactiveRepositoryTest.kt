package com.hs.product.repository

import com.hs.config.MongoDBReactiveConfig
import com.hs.product.entity.Product
import io.kotest.core.extensions.Extension
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.test.context.ContextConfiguration
import java.time.LocalDateTime

@DataMongoTest
@EnableAutoConfiguration
@ContextConfiguration(classes = [MongoDBReactiveConfig::class, ProductDocumentReactiveRepository::class])
internal class ProductDocumentReactiveRepositoryTest : DescribeSpec() {

    override fun extensions(): List<Extension> = listOf(SpringExtension)
    override fun isolationMode(): IsolationMode = IsolationMode.InstancePerLeaf

    @Autowired
    private lateinit var productDocumentReactiveRepository: ProductDocumentReactiveRepository

    init {
        describe("save 메서드는") {
            it("Product를 저장한다.") {
                // given
                val product = Product.create(
                    id = 1L,
                    name = "상품1",
                    price = 30_000,
                    stockQuantity = 10,
                    createdAt = LocalDateTime.now().withNano(0),
                    updatedAt = LocalDateTime.now().withNano(0)
                )

                // when
                productDocumentReactiveRepository.save(product = product)

                // then
                productDocumentReactiveRepository
                    .deleteById(productId = product.id!!)
                    .subscribe { consumer -> consumer.shouldBe(1) }
            }
        }

        describe("update 메서드는") {
            it("Product를 수정한다.") {
                // given
                val saveProduct = Product.create(
                    id = 1L,
                    name = "상품1",
                    price = 30_000,
                    stockQuantity = 10,
                    createdAt = LocalDateTime.now().withNano(0),
                    updatedAt = LocalDateTime.now().withNano(0)
                )
                productDocumentReactiveRepository.save(product = saveProduct)

                val updateProduct = Product.create(
                    id = saveProduct.id!!,
                    name = "상품 수정",
                    price = 35_000,
                    stockQuantity = 15,
                    createdAt = LocalDateTime.now().withNano(0),
                    updatedAt = LocalDateTime.now().withNano(0)
                )

                // when
                productDocumentReactiveRepository.update(product = updateProduct)

                // then
                val monoFindProduct = productDocumentReactiveRepository.findById(productId = updateProduct.id!!)

                monoFindProduct.subscribe { consumer ->
                    consumer.shouldNotBeNull()
                    consumer.id.shouldNotBeNull()
                    consumer.id!!.shouldBe(updateProduct.id!!)
                    consumer.name.shouldBe(updateProduct.name)
                    consumer.price.shouldBe(updateProduct.price)
                    consumer.createdAt.shouldNotBeNull()
                    consumer.createdAt!!.shouldBe(updateProduct.createdAt!!)
                    consumer.updatedAt.shouldNotBeNull()
                    consumer.updatedAt!!.shouldBe(updateProduct.updatedAt!!)
                }

                productDocumentReactiveRepository
                    .deleteById(productId = updateProduct.id!!)
                    .subscribe { consumer -> consumer.shouldBe(1) }
            }
        }

        describe("findById 메서드는") {
            it("Product를 반환한다.") {
                // given
                val product = Product.create(
                    id = 1L,
                    name = "상품1",
                    price = 30_000,
                    stockQuantity = 10,
                    createdAt = LocalDateTime.now().withNano(0),
                    updatedAt = LocalDateTime.now().withNano(0)
                )
                productDocumentReactiveRepository.save(product = product)

                // when
                val monoFindProduct = productDocumentReactiveRepository.findById(productId = product.id!!)

                // then
                monoFindProduct.subscribe { consumer ->
                    consumer.shouldNotBeNull()
                    consumer.id.shouldNotBeNull()
                    consumer.id!!.shouldBe(product.id!!)
                    consumer.name.shouldBe(product.name)
                    consumer.price.shouldBe(product.price)
                    consumer.createdAt.shouldNotBeNull()
                    consumer.createdAt!!.shouldBe(product.createdAt!!)
                    consumer.updatedAt.shouldNotBeNull()
                    consumer.updatedAt!!.shouldBe(product.updatedAt!!)
                }

                productDocumentReactiveRepository
                    .deleteById(productId = product.id!!)
                    .subscribe { consumer -> consumer.shouldBe(1) }
            }

            context("Product가 존재하지 않는 경우") {
                it("NoSuchElementException 예외를 던진다.") {
                    // given
                    val productId = 1L

                    // when
                    val monoFindProduct = productDocumentReactiveRepository
                        .findById(productId = productId)
                        .log()

                    // then
                    monoFindProduct.subscribe(
                        {},
                        { errorConsumer ->
                            errorConsumer.shouldBeInstanceOf<NoSuchElementException>()
                            errorConsumer.localizedMessage.shouldBe("product entity is not exist.")
                        }
                    )
                }
            }
        }

        describe("deleteById 메서드는") {
            it("ProductDocument를 삭제한다.") {
                // given
                val product = Product.create(
                    id = 1L,
                    name = "상품1",
                    price = 30_000,
                    stockQuantity = 10,
                    createdAt = LocalDateTime.now().withNano(0),
                    updatedAt = LocalDateTime.now().withNano(0)
                )
                productDocumentReactiveRepository.save(product = product)

                // when
                val monoDeleteProductDocument = productDocumentReactiveRepository
                    .deleteById(productId = product.id!!)

                // then
                monoDeleteProductDocument.subscribe { consumer -> consumer.shouldBe(1) }
            }
        }
    }
}
