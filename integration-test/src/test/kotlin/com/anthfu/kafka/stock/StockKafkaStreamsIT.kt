package com.anthfu.kafka.stock

import com.anthfu.kafka.util.createTopicCmd
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
import java.util.concurrent.TimeUnit
import kotlin.test.assertEquals

@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class StockKafkaStreamsIT {
    private val kafkaImage = DockerImageName.parse("confluentinc/cp-kafka:6.1.1")
    private val producerImage = DockerImageName.parse("stock-producer:1.0.0-SNAPSHOT")
    private val consumerImage = DockerImageName.parse("stock-consumer:1.0.0-SNAPSHOT")
    private val streamsImage = DockerImageName.parse("stock-streams:1.0.0-SNAPSHOT")

    private val logger = LoggerFactory.getLogger(javaClass)
    private val kafkaNetwork = Network.newNetwork()

    @Container
    private val kafka = KafkaContainer(kafkaImage)
        .withNetwork(kafkaNetwork)
        .withNetworkAliases("kafka")

    private val consumer = GenericContainer<Nothing>(consumerImage).apply {
        withNetwork(kafkaNetwork)
        withEnv("SPRING_KAFKA_BOOTSTRAPSERVERS", "kafka:9092")
        withEnv("SPRING_KAFKA_CONSUMER_AUTOOFFSETRESET", "earliest")
        withEnv("SPRING_KAFKA_CONSUMER_GROUPID", "stock-consumers")
        withEnv("SPRING_KAFKA_TEMPLATE_DEFAULTTOPIC", "stock-stream-out")
        withLogConsumer(Slf4jLogConsumer(logger).withPrefix("stock-consumer"))
        waitingFor(Wait.forLogMessage(".*Resetting offset for partition.*\\n", 1))
        dependsOn(kafka)
    }

    private val producer = GenericContainer<Nothing>(producerImage).apply {
        withNetwork(kafkaNetwork)
        withEnv("SPRING_KAFKA_BOOTSTRAPSERVERS", "kafka:9092")
        withEnv("SPRING_KAFKA_TEMPLATE_DEFAULTTOPIC", "stock-stream-in")
        withLogConsumer(Slf4jLogConsumer(logger).withPrefix("stock-producer"))
        withStartupCheckStrategy(IndefiniteWaitOneShotStartupCheckStrategy())
        dependsOn(kafka)
    }

    private val streams = GenericContainer<Nothing>(streamsImage).apply {
        withNetwork(kafkaNetwork)
        withEnv("SPRING_KAFKA_STREAMS_BOOTSTRAPSERVERS", "kafka:9092")
        withLogConsumer(Slf4jLogConsumer(logger).withPrefix("stock-streams"))
        dependsOn(consumer)
    }

    @Test
    fun `Verify message production and consumption`() {
        val t1 = kafka.execInContainer(*createTopicCmd("stock-stream-in"))
        assertEquals(0, t1.exitCode)

        val t2 = kafka.execInContainer(*createTopicCmd("stock-stream-out"))
        assertEquals(0, t2.exitCode)

        producer.start()
        consumer.start()
        streams.start()

        TimeUnit.SECONDS.sleep(10)

        assert(producer.logs.contains("Sent: 999"))
        assert(streams.logs.contains("Processed: 999 -> 1000"))
        assert(consumer.logs.contains("Received: 1000"))
    }
}
