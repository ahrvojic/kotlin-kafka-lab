package com.anthfu.kafka.spring

import org.apache.kafka.clients.admin.AdminClient
import org.apache.kafka.clients.admin.AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG
import org.apache.kafka.clients.admin.NewTopic
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.KafkaContainer
import org.testcontainers.containers.Network
import org.testcontainers.containers.output.Slf4jLogConsumer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName
import java.time.Duration

@Testcontainers
class SpringKafkaClientIT {
    private val kafkaImage = DockerImageName.parse("confluentinc/cp-kafka:latest")
    private val producerImage = DockerImageName.parse("spring-producer:1.0-SNAPSHOT")
    private val consumerImage = DockerImageName.parse("spring-consumer:1.0-SNAPSHOT")

    private val logger = LoggerFactory.getLogger(javaClass)
    private val network = Network.newNetwork()

    @Container
    private val kafka: KafkaContainer = KafkaContainer(kafkaImage)
        .withNetwork(network)
        .withNetworkAliases("kafka")

    @Test
    fun integration() {
        val admin = AdminClient.create(mapOf(BOOTSTRAP_SERVERS_CONFIG to kafka.bootstrapServers))
        admin.createTopics(listOf(NewTopic("spring-kafka-test", 1, 1)))

        val producer = GenericContainer<Nothing>(producerImage).apply {
            withNetwork(network)
            withEnv("APP_TOPIC", "spring-kafka-test")
            withEnv("SPRING_KAFKA_PRODUCER_BOOSTRAP-SERVERS", "kafka:9092")
            withLogConsumer(Slf4jLogConsumer(logger))
            withMinimumRunningDuration(Duration.ofMinutes(1))
        }

        val consumer = GenericContainer<Nothing>(consumerImage).apply {
            withNetwork(network)
            withEnv("APP_TOPIC", "spring-kafka-test")
            withEnv("SPRING_KAFKA_CONSUMER_BOOSTRAP-SERVERS", "kafka:9092")
            withEnv("SPRING_KAFKA_CONSUMER_GROUP-ID", "spring-consumers")
            withLogConsumer(Slf4jLogConsumer(logger))
            withMinimumRunningDuration(Duration.ofMinutes(1))
        }

        consumer.start()
        producer.start()

        assert(kafka.isRunning)
        assert(producer.isRunning)
        assert(consumer.isRunning)

        val topics = admin.listTopics().names().get()

        assert("spring-kafka-test" in topics)

        admin.close()

        producer.stop()
        consumer.stop()
    }
}
