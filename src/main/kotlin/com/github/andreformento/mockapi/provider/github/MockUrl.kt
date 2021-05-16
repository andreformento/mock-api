package com.github.andreformento.mockapi.provider.github

import com.github.andreformento.mockapi.MockRequest
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

data class MockUrl(val host: String, val uri: String)

@Component
class MockUrlBuilder {

    @Value("\${mockRepository.github.host}")
    lateinit var mockRepositoryHost: String

    fun createMockUrl(mockRequest: MockRequest): MockUrl {
        if (mockRequest.path.equals("/")) {
            return MockUrl(mockRepositoryHost,"/${mockRequest.httpMethodName}")
        } else {
            return MockUrl(mockRepositoryHost,"/${mockRequest.httpMethodName}${mockRequest.path}")
        }
    }

}
