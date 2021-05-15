package com.github.andreformento.mockapi

import org.springframework.http.HttpMethod

data class MockQueryParameter(
    val key: String,
    val values: List<Any>
)


//sealed class MockData()
//data class ObjectData(val number: Double) : MockData()
//data class ListData(val e1: MockData, val e2: MockData) : MockData()
//object EmptyData : MockData


/*
data class MockRequest(
    val httpMethodName: String,
    val path: String,
    val queryParameters: List<MockQueryParameter>,
    val data: MockData
) {
    constructor(httpMethod: HttpMethod, path: String, requestBodyRaw: Any?) : this(name) {
        parent.children.add(this)
    }
}
*/

class MockRequest(
    val httpMethodName: String,
    val path: String,
    val queryParameters: List<MockQueryParameter>,
    val data: MockData
) {
    companion object Factory {
        fun create(httpMethod: HttpMethod, path: String, requestBodyRaw: String?): MockRequest =
            MockRequest(httpMethod.name.toLowerCase(), path, arrayListOf(), MockData(requestBodyRaw?:""))

    }
}

data class MockResponse(
    val httpStatusCode: Int,
    val data: MockData
)
