package com.github.andreformento.mockapi

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.UriBuilder
import reactor.core.publisher.Mono

@Service
class MockService(val client: WebClient, val mockUrlBuilder: MockUrlBuilder) {
    private val logger = LoggerFactory.getLogger(javaClass)

    fun getResponse(mockRequest: MockRequest): Mono<MockResponse> {
        val bodyResponse = client
            .get()
            .uri { builder: UriBuilder ->
                builder.path(mockUrlBuilder.createMockUrl(mockRequest).url)
//                    .queryParam("id", cityId.cityId)
//                    .queryParam("APPID", config.apiKey)
                    .build()
            }
            .accept(APPLICATION_JSON)
            .retrieve()
            .bodyToMono(Any::class.java)

        return bodyResponse.map{ r ->
            logger.warn("response = $r")
            MockResponse(200, MockData(r.toString()))
        }
    }
}
