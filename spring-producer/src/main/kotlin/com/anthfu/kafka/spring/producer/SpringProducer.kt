package com.anthfu.kafka.spring.producer

import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class SpringProducer(private val kafkaTemplate: KafkaTemplate<String, String>) {
  private val logger = LoggerFactory.getLogger(javaClass)

  fun send(message: String) {
    kafkaTemplate.sendDefault(message) // TODO: Callback
    logger.info("Sent: $message")
  }
}
