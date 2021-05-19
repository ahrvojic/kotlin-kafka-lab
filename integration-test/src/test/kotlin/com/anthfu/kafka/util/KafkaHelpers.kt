package com.anthfu.kafka.util

fun createTopicCmd(topic: String): Array<String> = arrayOf(
    "/usr/bin/kafka-topics", "--create",
    "--replication-factor",  "1",
    "--partitions", "1",
    "--topic", topic,
    "--bootstrap-server", "localhost:9092"
)
