package com.anthfu.kafka.basicconsumer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.boot.runApplication

@ConstructorBinding
@ConfigurationProperties("basic-consumer")
data class BasicConsumerProps(
    val topic: String
)

@SpringBootApplication
@ConfigurationPropertiesScan
class BasicConsumerApp

fun main(args: Array<String>) {
    runApplication<BasicConsumerApp>(*args)
}
