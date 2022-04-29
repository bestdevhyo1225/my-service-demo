package com.hs.application.handler

import com.hs.event.ProductApplicationEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class ProductEventHandler(
    private val productRabbitMqPublisher: ProductRabbitMqPublisher,
) {

    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun handle(event: ProductApplicationEvent) = runBlocking {
        log.info("After commit transaction, [ProductApplicationEvent] is executed. (productId={})", event.productId)

        launch(Dispatchers.IO) { publishProductEvent(event = event) }
    }

    suspend fun publishProductEvent(event: ProductApplicationEvent) {
        productRabbitMqPublisher.publish(event = event)
    }
}
