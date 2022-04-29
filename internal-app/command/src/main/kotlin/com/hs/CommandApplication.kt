package com.hs

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class CommandApplication

fun main(args: Array<String>) {
    runApplication<CommandApplication>(*args)
}
