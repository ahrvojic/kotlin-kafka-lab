package com.anthfu.kafka.stock.streams

import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.kstream.Consumed
import org.apache.kafka.streams.kstream.KStream
import org.apache.kafka.streams.kstream.Produced
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.kafka.annotation.EnableKafkaStreams
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration
import org.springframework.kafka.config.KafkaStreamsConfiguration

@Configuration
@EnableKafkaStreams
class StockStreams(env: Environment, private val conf: KafkaProperties) {
    private val logger = LoggerFactory.getLogger(javaClass)

    private val topicIn = env.getProperty("app.topic-in")
    private val topicOut = env.getProperty("app.topic-out")

    private val stringSerde = Serdes.String()

    @Bean(name = [KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME])
    fun kStreamsConfig(): KafkaStreamsConfiguration {
        return KafkaStreamsConfiguration(conf.buildStreamsProperties())
    }

    @Bean
    fun kStream(builder: StreamsBuilder): KStream<String, String> {
        val stream = builder.stream(topicIn, Consumed.with(stringSerde, stringSerde))

        stream.mapValues { v ->
            val message = "${v.toInt() + 1}"
            logger.info("Processed: $v -> $message")
            message
        }
        .to(topicOut, Produced.with(stringSerde, stringSerde))

        return stream
    }
}
