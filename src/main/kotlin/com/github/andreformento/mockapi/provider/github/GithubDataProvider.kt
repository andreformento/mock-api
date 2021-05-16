package com.github.andreformento.mockapi.provider.github

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.github.andreformento.mockapi.MockData
import com.github.andreformento.mockapi.MockRequest
import com.github.andreformento.mockapi.provider.DataProvider
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux

private data class GithubResponse(val download_url: String?, val type: String)

@Component
@ConditionalOnProperty(prefix = "mockRepository", name = ["providerStrategy"], havingValue = "github")
class GithubDataProvider(
    val webClientBuilder: WebClient.Builder,
    val mockUrlBuilder: MockUrlBuilder
) : DataProvider {

    private val logger = LoggerFactory.getLogger(javaClass)
    private val objectMapper: ObjectMapper = jacksonObjectMapper()
        .setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE)
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

    override fun getMockDataList(mockRequest: MockRequest): Flux<MockData> {
        val mockUrl = mockUrlBuilder.createMockUrl(mockRequest)

        val githubFiles: Flux<GithubResponse> = webClientBuilder
            .baseUrl(mockUrl.host)
            .build()
            .get()
            .uri { it
                    .path(mockUrl.uri)
                    .queryParams(mockRequest.queryParams)
                    .build()
            }
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToFlux(GithubResponse::class.java)

        return githubFiles
            .filter { it.type.equals("file", ignoreCase = true) && !it.download_url.isNullOrEmpty() }
            .map { it.download_url!! }
            .filter { it.endsWith(".json", ignoreCase = true) }
            .flatMap {
                WebClient.create(it)
                    .get()
                    .accept(MediaType.TEXT_PLAIN)
                    .retrieve()
                    .bodyToMono(String::class.java)
            }
            .map { objectMapper.readValue(it, MockData::class.java) }
    }

}
