package com.hs

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ExternalEventConsumerWorker

fun main(args: Array<String>) {
    runApplication<ExternalEventConsumerWorker>(*args)
}
