package com.hs.listener

import com.hs.constants.InternalQueueErrorHandler
import com.hs.constants.InternalQueueId
import com.hs.constants.InternalQueueName
import com.hs.constants.InternalQueueRetry
import com.hs.event.ProductApplicationEvent
import com.hs.service.ProductUpsertCommand
import com.rabbitmq.client.Channel
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.amqp.core.Message
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Component

@Component
class ProductQueueListener(
    private val rabbitTemplate: RabbitTemplate,
    private val productUpsertCommand: ProductUpsertCommand,
) {

    private val log: Logger = LoggerFactory.getLogger(this.javaClass)

    @RabbitListener(
        id = InternalQueueId.PRODUCT,
        queues = [InternalQueueName.PRODUCT],
        errorHandler = InternalQueueErrorHandler.PRODUCT
    )
    fun consumeQueue(event: ProductApplicationEvent, channel: Channel, message: Message) {
        log.info("message : {}", message)

        productUpsertCommand.execute(productEventId = event.productEventId, productId = event.productId)

        channel.basicAck(message.messageProperties.deliveryTag, false)
    }

    @RabbitListener(id = InternalQueueId.PRODUCT_DLQ, queues = [InternalQueueName.PRODUCT_DLQ])
    fun consumeDeadLetterQueue(event: ProductApplicationEvent, channel: Channel, message: Message) {
        val deliveryTag: Long = message.messageProperties.deliveryTag
        val xDeathHeader: Map<String, *> = message.messageProperties.xDeathHeader.first()
        val xDeathCount = xDeathHeader["count"] as Long
        val queue = xDeathHeader["queue"] as String

        if (xDeathCount >= InternalQueueRetry.MAX_ATTEMPT) {
            log.error("Message exceed retry max attempt (message : {})", message)

            channel.basicReject(deliveryTag, false)
        } else {
            log.warn("Send from ProductDeadLetterQueue to ProductQueue (xDeathCount: {})", xDeathCount)

            rabbitTemplate.send(queue, message)

            channel.basicAck(deliveryTag, false)
        }
    }
}
