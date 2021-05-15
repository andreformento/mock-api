package com.github.andreformento.mockapi

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

data class MockUrl(val url: String)

@Component
class MockUrlBuilder {

    @Value("\${mockRepository.basePath}")
    lateinit var mockRepositoryBasePath: String

    fun createMockUrl(mockRequest: MockRequest): MockUrl {
        return MockUrl("/${mockRepositoryBasePath}/${mockRequest.httpMethodName}${mockRequest.path}")
    }

}
