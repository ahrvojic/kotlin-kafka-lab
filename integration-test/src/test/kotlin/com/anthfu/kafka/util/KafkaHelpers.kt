package com.anthfu.kafka.util

import org.testcontainers.containers.GenericContainer
import org.testcontainers.utility.DockerImageName

class ProducerContainer(image: DockerImageName) : GenericContainer<ProducerContainer>(image)
class ConsumerContainer(image: DockerImageName) : GenericContainer<ConsumerContainer>(image)
class StreamsContainer(image: DockerImageName) : GenericContainer<StreamsContainer>(image)

fun createTopicCmd(topic: String): Array<String> = arrayOf(
    "/usr/bin/kafka-topics", "--create",
    "--replication-factor",  "1",
    "--partitions", "1",
    "--topic", topic,
    "--bootstrap-server", "localhost:9092"
)
