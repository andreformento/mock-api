package com.github.andreformento.mockapi

import org.springframework.http.HttpMethod
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class MockController(val mockService: MockService) {

    private fun api(httpMethod: HttpMethod, path: String, requestBodyRaw: Any?): Any {
        return mockService.getResponse(httpMethod, path, requestBodyRaw)
    }

    @RequestMapping(value = ["{*path}"], method = [RequestMethod.GET])
    fun apiGet(request: ServerHttpRequest, @PathVariable path: String): Any {
        return api(request.method!!, path, null)
    }

    @RequestMapping(
        value = ["{*path}"],
        method = [RequestMethod.POST, RequestMethod.PUT, RequestMethod.PATCH, RequestMethod.DELETE]
    )
    fun apiBody(request: ServerHttpRequest, @PathVariable path: String, @RequestBody(required = false) requestBodyRaw: Any?): Any {
        return api(request.method!!, path, requestBodyRaw)
    }

}
