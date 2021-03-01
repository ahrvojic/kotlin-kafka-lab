package com.anthfu.kafka.basicconsumer

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BasicConsumerApp : CommandLineRunner {
    override fun run(vararg args: String) {
        TODO("Not yet implemented")
    }
}

fun main(args: Array<String>) {
    runApplication<BasicConsumerApp>(*args)
}
