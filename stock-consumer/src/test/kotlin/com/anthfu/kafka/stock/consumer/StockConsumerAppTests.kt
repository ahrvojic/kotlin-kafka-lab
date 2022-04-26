package com.anthfu.kafka.stock.consumer

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@ContextConfiguration(
    classes = [StockConsumerApp::class],
    initializers = [ConfigDataApplicationContextInitializer::class]
)
class StockConsumerAppTests {
  @Test
  fun contextLoads() {
    // Ensure service starts up properly
  }
}
