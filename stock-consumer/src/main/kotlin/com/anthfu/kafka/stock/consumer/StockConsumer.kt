package com.anthfu.kafka.stock.consumer

import org.apache.kafka.clients.consumer.KafkaConsumer
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.Duration.ofMillis

@Component
class StockConsumer(conf: KafkaProperties) : AutoCloseable {
  private val logger = LoggerFactory.getLogger(javaClass)

  private val topic = conf.template.defaultTopic
  private val consumer = KafkaConsumer<String, String>(
      conf.buildConsumerProperties()
  ).apply {
    subscribe(listOf(topic))
  }

  @Scheduled(fixedDelay = 1L)
  fun run() {
    consumer
        .poll(ofMillis(100L))
        .forEach { rec ->
          val message = rec.value()
          logger.info("Received: $message")
        }
  }

  override fun close() {
    consumer.close()
  }
}
