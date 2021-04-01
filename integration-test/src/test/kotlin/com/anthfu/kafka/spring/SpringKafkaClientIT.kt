package com.anthfu.kafka.spring

import org.junit.jupiter.api.Test
import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.KafkaContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName

@Testcontainers
class SpringKafkaClientIT {
    @Container
    val kafka = KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest"))

    @Container
    val producer = GenericContainer<Nothing>(DockerImageName.parse("spring-producer:1.0-SNAPSHOT"))

    @Container
    val consumer = GenericContainer<Nothing>(DockerImageName.parse("spring-consumer:1.0-SNAPSHOT"))

    @Test
    fun integrationTest() {
        // TODO
    }
}
