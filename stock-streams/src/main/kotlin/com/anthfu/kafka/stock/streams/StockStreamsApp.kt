package com.anthfu.kafka.stock.streams

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class StockStreamsApp

fun main(args: Array<String>) {
    runApplication<StockStreamsApp>(*args)
}
