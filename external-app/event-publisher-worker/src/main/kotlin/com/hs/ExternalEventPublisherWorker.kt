package com.hs

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ExternalEventPublisherWorker

fun main(args: Array<String>) {
    runApplication<ExternalEventPublisherWorker>(*args)
}
