package com.anthfu.kafka.spring.producer

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@ContextConfiguration(
    classes = [SpringProducerApp::class],
    initializers = [ConfigDataApplicationContextInitializer::class]
)
class SpringProducerAppTests {
    @Test
    fun contextLoads() {
        // Ensure service starts up properly
    }
}
