package com.anthfu.kafka.spring

import org.junit.jupiter.api.Test
import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.KafkaContainer
import org.testcontainers.containers.Network
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName

@Testcontainers
class SpringKafkaClientIT {
    private val network = Network.newNetwork()

    @Container
    val kafka: KafkaContainer = KafkaContainer(
        DockerImageName.parse("confluentinc/cp-kafka:latest")
    ).withNetwork(network)

    @Test
    fun integrationTest() {
        val producer = GenericContainer<Nothing>(
            DockerImageName.parse("spring-producer:1.0-SNAPSHOT")
        ).apply {
            withNetwork(network)
            withEnv("APP_TOPIC", "spring-kafka-test")
            withEnv("SPRING_KAFKA_PRODUCER_BOOSTRAP-SERVERS", kafka.bootstrapServers)
        }

        val consumer = GenericContainer<Nothing>(
            DockerImageName.parse("spring-consumer:1.0-SNAPSHOT")
        ).apply {
            withNetwork(network)
            withEnv("APP_TOPIC", "spring-kafka-test")
            withEnv("SPRING_KAFKA_CONSUMER_BOOSTRAP-SERVERS", kafka.bootstrapServers)
            withEnv("SPRING_KAFKA_CONSUMER_GROUP-ID", "spring-consumers")
        }

        producer.start()
        consumer.start()
    }
}
