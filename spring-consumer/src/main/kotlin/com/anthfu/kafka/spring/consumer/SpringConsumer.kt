package com.anthfu.kafka.spring.consumer

import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class SpringConsumer {
  private val logger = LoggerFactory.getLogger(javaClass)

  @KafkaListener(topics = ["#{'\${spring.kafka.template.default-topic}'}"])
  fun receive(message: String) {
    logger.info("Received: $message")
  }
}
