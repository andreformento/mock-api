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
class MockService(val client: WebClient) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @Value("\${mockRepository.basePath}")
    lateinit var mockRepositoryBasePath: String

    fun getResponse(httpMethod: HttpMethod, path: String, requestBodyRaw: Any?): Mono<MockResponse> {
        val uri = "/${mockRepositoryBasePath}/${httpMethod.name.toLowerCase()}$path"
        logger.debug("uri $uri")
        val bodyResponse = client
            .get()
            .uri { builder: UriBuilder ->
                builder.path(uri)
//                    .queryParam("id", cityId.cityId)
//                    .queryParam("APPID", config.apiKey)
                    .build()
            }
            .accept(APPLICATION_JSON)
            .retrieve()
            .bodyToMono(Any::class.java)

        return bodyResponse.map{ r ->
            logger.warn("response = $r")
            MockResponse(200, r)
        }
    }
}
