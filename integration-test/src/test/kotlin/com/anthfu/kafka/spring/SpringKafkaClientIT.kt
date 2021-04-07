package com.anthfu.kafka.spring

import org.apache.kafka.clients.admin.AdminClient
import org.apache.kafka.clients.admin.AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG
import org.junit.jupiter.api.Test
import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.KafkaContainer
import org.testcontainers.containers.Network
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName

@Testcontainers
class SpringKafkaClientIT {
    private val kafkaImage = DockerImageName.parse("confluentinc/cp-kafka:latest")
    private val producerImage = DockerImageName.parse("spring-producer:1.0-SNAPSHOT")
    private val consumerImage = DockerImageName.parse("spring-consumer:1.0-SNAPSHOT")

    private val network = Network.newNetwork()

    @Container
    val kafka: KafkaContainer = KafkaContainer(kafkaImage)
        .withNetwork(network)
        .withNetworkAliases("kafka")

    @Test
    fun integrationTest() {
        val producer = GenericContainer<Nothing>(producerImage).apply {
            withNetwork(network)
            withEnv("APP_TOPIC", "spring-kafka-test")
            withEnv("SPRING_KAFKA_PRODUCER_BOOSTRAP-SERVERS", "kafka:9092")
        }

        val consumer = GenericContainer<Nothing>(consumerImage).apply {
            withNetwork(network)
            withEnv("APP_TOPIC", "spring-kafka-test")
            withEnv("SPRING_KAFKA_CONSUMER_BOOSTRAP-SERVERS", "kafka:9092")
            withEnv("SPRING_KAFKA_CONSUMER_GROUP-ID", "spring-consumers")
        }

        producer.start()
        consumer.start()

        assert(kafka.isRunning)
        assert(producer.isRunning)
        assert(consumer.isRunning)

        val admin = AdminClient.create(mapOf(BOOTSTRAP_SERVERS_CONFIG to kafka.bootstrapServers))
        admin.listTopics()
    }
}
