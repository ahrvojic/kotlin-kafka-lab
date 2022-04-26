package com.anthfu.kafka.spring.producer

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringProducerApp(
    private val producer: SpringProducer
) : CommandLineRunner {
  override fun run(vararg args: String) {
    repeat(1000) {
      producer.send(it.toString())
    }
  }
}

fun main(args: Array<String>) {
  runApplication<SpringProducerApp>(*args)
}
