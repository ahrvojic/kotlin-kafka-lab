package com.anthfu.kafka.springconsumer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.boot.runApplication

@ConstructorBinding
@ConfigurationProperties("spring-consumer")
data class SpringConsumerProps(
    val topic: String
)

@SpringBootApplication
@ConfigurationPropertiesScan
class SpringConsumerApp

fun main(args: Array<String>) {
    runApplication<SpringConsumerApp>(*args)
}
