package com.hs

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableBatchProcessing
class InternalBatchApplication

fun main(args: Array<String>) {
    runApplication<InternalBatchApplication>(*args)
}
