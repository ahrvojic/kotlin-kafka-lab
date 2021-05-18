package com.anthfu.kafka.spring

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.slf4j.LoggerFactory
import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.KafkaContainer
import org.testcontainers.containers.Network
import org.testcontainers.containers.output.Slf4jLogConsumer
import org.testcontainers.containers.startupcheck.IndefiniteWaitOneShotStartupCheckStrategy
import org.testcontainers.containers.wait.strategy.Wait
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName

@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SpringKafkaStreamsIT {
    private val kafkaImage = DockerImageName.parse("confluentinc/cp-kafka:6.1.1")
    private val producerImage = DockerImageName.parse("spring-producer:1.0-SNAPSHOT")
    private val consumerImage = DockerImageName.parse("spring-consumer:1.0-SNAPSHOT")
    private val streamsImage = DockerImageName.parse("spring-streams:1.0-SNAPSHOT")

    private val logger = LoggerFactory.getLogger(javaClass)
    private val kafkaNetwork = Network.newNetwork()

    @Container
    private val kafka = KafkaContainer(kafkaImage)
        .withNetwork(kafkaNetwork)
        .withNetworkAliases("kafka")

    @Container
    private val consumer = GenericContainer<Nothing>(consumerImage).apply {
        withNetwork(kafkaNetwork)
        withEnv("SPRING_KAFKA_BOOTSTRAPSERVERS", "kafka:9092")
        withEnv("SPRING_KAFKA_CONSUMER_AUTOOFFSETRESET", "earliest")
        withEnv("SPRING_KAFKA_CONSUMER_GROUPID", "spring-consumers")
        withEnv("SPRING_KAFKA_TEMPLATE_DEFAULTTOPIC", "test-topic-out")
        withLogConsumer(Slf4jLogConsumer(logger).withPrefix("spring-consumer"))
        waitingFor(Wait.forLogMessage(".*partitions assigned.*\\n", 1))
        dependsOn(kafka)
    }

    @Container
    private val producer = GenericContainer<Nothing>(producerImage).apply {
        withNetwork(kafkaNetwork)
        withEnv("SPRING_KAFKA_BOOTSTRAPSERVERS", "kafka:9092")
        withEnv("SPRING_KAFKA_TEMPLATE_DEFAULTTOPIC", "test-topic-in")
        withLogConsumer(Slf4jLogConsumer(logger).withPrefix("spring-producer"))
        withStartupCheckStrategy(IndefiniteWaitOneShotStartupCheckStrategy())
        dependsOn(consumer)
    }

    @Container
    private val streams = GenericContainer<Nothing>(streamsImage).apply {
        withNetwork(kafkaNetwork)
        withEnv("SPRING_KAFKA_BOOTSTRAPSERVERS", "kafka:9092")
        withLogConsumer(Slf4jLogConsumer(logger).withPrefix("spring-streams"))
        dependsOn(consumer)
    }

    @Test
    fun `Verify message production and consumption`() {
        assert(producer.logs.contains("Sent: 999"))
        assert(streams.logs.contains("Processed: 999 -> 1000"))
        assert(consumer.logs.contains("Received: 1000"))
    }
}