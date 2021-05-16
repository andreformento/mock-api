package com.github.andreformento.mockapi

import com.github.andreformento.mockapi.provider.DataProvider
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class MockService(val dataProvider: DataProvider) {
    private val logger = LoggerFactory.getLogger(javaClass)

    fun getResponse(mockRequest: MockRequest): Mono<MockDataResponse> {
        val mockDataList = dataProvider.getMockDataList(mockRequest)
        return mockDataList.last().map { it.response }
    }
}
