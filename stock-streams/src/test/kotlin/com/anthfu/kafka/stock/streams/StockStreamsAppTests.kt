package com.anthfu.kafka.stock.streams

import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@ContextConfiguration(
    classes = [StockStreamsApp::class],
    initializers = [ConfigDataApplicationContextInitializer::class]
)
class StockStreamsAppTests
