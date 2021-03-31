package com.anthfu.kafka.spring.consumer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class SpringConsumerApp

fun main(args: Array<String>) {
    runApplication<SpringConsumerApp>(*args)
}
