package com.github.andreformento.mockapi

import org.springframework.boot.Banner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MockApiApplication

fun main(args: Array<String>) {
    runApplication<MockApiApplication>(*args) {
        setBannerMode(Banner.Mode.OFF)
    }
}
