package com.hs

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class InternalEventWorkerApplication

fun main(args: Array<String>) {
    runApplication<InternalEventWorkerApplication>(*args)
}
