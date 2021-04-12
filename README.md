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

* A full local installation of [Docker Desktop](https://www.docker.com/products/docker-desktop). It includes Docker Compose if you'd like to run services standalone.
* JDK 11+

## Build

All modules and Docker images can be built in one shot with the following:

```shell
$ cd kotlin-kafka-lab
$ ./mvnw clean install
```

## Standalone Run

Although running the services within your IDE is typically easiest for local experimentation, it's definitely instructive to run them against a full Kafka deployment with a web interface to observe metrics in real time. 

Confluent provides [all-in-one distributions](https://github.com/confluentinc/cp-all-in-one) of its platform, which are very convenient for getting up and running quickly:

```shell
$ cd cp-all-in-one/cp-all-in-one
$ docker-compose up -d
```

Once all the Confluent Platform services are running, we can start up our own services:

```shell
$ cd kotlin-kafka-lab
$ docker-compose up -d --scale spring-consumer=3
```

This will bring up a `spring-producer` and three `spring-consumer` instances in the same consumer group. The topic will be created automatically by default, and the producer will immediately begin sending messages. You can then visit `localhost:9021` to see the messages and data flow graphs in Confluent Control Center.
