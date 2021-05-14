package com.github.andreformento.mockapi

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class WebClientFactory(val webClientBuilder: WebClient.Builder) {

    @Value("\${mockRepository.host}")
    lateinit var mockRepositoryHost: String

    @Bean
    fun createWebClient(): WebClient {
        return webClientBuilder.baseUrl(mockRepositoryHost).build()
    }

}
