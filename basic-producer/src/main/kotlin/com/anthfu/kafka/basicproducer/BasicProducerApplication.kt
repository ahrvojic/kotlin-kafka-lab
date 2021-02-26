package com.anthfu.kafka.basicproducer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BasicProducerApplication

fun main(args: Array<String>) {
    runApplication<BasicProducerApplication>(*args)
}
