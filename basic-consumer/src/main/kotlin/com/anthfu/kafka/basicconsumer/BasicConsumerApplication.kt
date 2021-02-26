package com.anthfu.kafka.basicconsumer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BasicConsumerApplication

fun main(args: Array<String>) {
    runApplication<BasicConsumerApplication>(*args)
}
