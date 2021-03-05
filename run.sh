#!/usr/bin/env bash

# Create topic on local Kafka cluster
docker run --net=host --rm confluentinc/cp-kafka:latest kafka-topics --create --topic spring-client-stream --replication-factor 1 --partitions 12 --if-not-exists --zookeeper localhost:2181

# Spin up producer and consumers
docker-compose up --scale spring-consumer=3
