package com.anthfu.kafka.spring.streams

import org.apache.kafka.streams.kstream.KStream
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import java.util.function.Function

@SpringBootApplication
class SpringStreamsApp {
    val logger: Logger = LoggerFactory.getLogger(javaClass)

    @Bean
    fun increment(): Function<KStream<String, String>, KStream<String, String>> = Function {
        input -> input.mapValues { v ->
            val message = "${v.toInt() + 1}"
            logger.info("Processed: $v -> $message")
            message
        }
    }
}

fun main(args: Array<String>) {
    runApplication<SpringStreamsApp>(*args)
}
