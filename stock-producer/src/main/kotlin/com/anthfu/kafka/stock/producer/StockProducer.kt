package com.anthfu.kafka.stock.producer

import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.clients.producer.RecordMetadata
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.stereotype.Component

@Component
class StockProducer(conf: KafkaProperties) : AutoCloseable {
    private val logger = LoggerFactory.getLogger(javaClass)

    private val topic = conf.template.defaultTopic
    private val producer = KafkaProducer<String, String>(conf.buildProducerProperties())

    fun send(message: String) {
        producer.send(ProducerRecord(topic, message)) { m: RecordMetadata, e: Exception? ->
            when (e) {
                null -> logger.info("Sent: $message")
                else -> logger.error("Error producing record to topic ${m.topic()}", e)
            }
        }
    }

    override fun close() {
        producer.flush()
        producer.close()
    }
}
