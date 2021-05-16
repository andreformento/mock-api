package com.github.andreformento.mockapi.provider

import com.github.andreformento.mockapi.MockData
import com.github.andreformento.mockapi.MockRequest
import reactor.core.publisher.Flux

interface DataProvider {

    fun getMockDataList(mockRequest: MockRequest): Flux<MockData>

}
