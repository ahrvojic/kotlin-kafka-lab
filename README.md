# Kotlin Kafka Lab

## Goal

When integrating multiple complex technologies, especially in a secondary language, it's often very helpful to see full implementations and even their runtime behavior. To that end, this is a collection of self-contained Kafka client examples in Kotlin intended for local experimentation.

## Examples by Technology Stack

### Spring Kafka + Spring Boot
* **spring-producer**: a basic Kafka string message producer
* **spring-consumer**: a basic Kafka string message consumer

## Integration Testing
The **integration-tests** module is organized by technology stack, with tests implemented using [Testcontainers](https://testcontainers.org) and [JUnit](https://junit.org).

## Requirements

* A full local installation of [Docker](https://www.docker.com/products/docker-desktop) that includes Docker Compose if you'd like to run services standalone.
