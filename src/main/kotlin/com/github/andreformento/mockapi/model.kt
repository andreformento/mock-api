package com.github.andreformento.mockapi

import com.fasterxml.jackson.databind.JsonNode
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.util.MultiValueMap

class MockRequest(
    val httpMethodName: String,
    val path: String,
    val queryParams: MultiValueMap<String, String>,
    val body: MockBody
) {
    companion object Factory {
        fun create(
            request: ServerHttpRequest, path: String, bodyText: String? = null
        ): MockRequest =
            MockRequest(request.methodValue.toLowerCase(), path, request.queryParams, MockBody(bodyText))

    }
}

data class MockDataResponse(
    val httpStatusCode: Int?,
    val body: JsonNode?
)

data class MockData(val response: MockDataResponse)
