package com.anthfu.kafka.stock.producer

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@ContextConfiguration(
    classes = [StockProducerApp::class],
    initializers = [ConfigDataApplicationContextInitializer::class]
)
class StockProducerAppTests {
    @Test
    fun contextLoads() {
        // Ensure service starts up properly
    }
}
