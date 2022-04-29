package com.hs.application.handler

import com.fasterxml.jackson.databind.ObjectMapper
import com.hs.constants.InternalQueueName
import com.hs.event.ProductApplicationEvent
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.amqp.core.MessageBuilder
import org.springframework.amqp.core.MessagePropertiesBuilder
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Component

@Component
class ProductRabbitMqPublisher(
    private val objectMapper: ObjectMapper,
    private val rabbitTemplate: RabbitTemplate
) {

    private val log: Logger = LoggerFactory.getLogger(this.javaClass)

    fun publish(event: ProductApplicationEvent) {
        log.info("publish() is executed. (productId={})", event.productId)

        val properties = MessagePropertiesBuilder.newInstance()
            .setContentType("application/json")
            .build()

        val body = objectMapper.writeValueAsBytes(event)

        val message = MessageBuilder
            .withBody(body)
            .andProperties(properties)
            .build()

        rabbitTemplate.convertAndSend(InternalQueueName.PRODUCT, message)
    }
}
