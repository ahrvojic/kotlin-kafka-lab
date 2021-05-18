package com.anthfu.kafka.stock.consumer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class StockConsumerApp

fun main(args: Array<String>) {
    runApplication<StockConsumerApp>(*args)
}
