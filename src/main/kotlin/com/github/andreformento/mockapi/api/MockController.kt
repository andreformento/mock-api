package com.github.andreformento.mockapi.api

import com.github.andreformento.mockapi.MockRequest
import com.github.andreformento.mockapi.MockService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
class MockController(val mockService: MockService) {

    private fun convertResponse(
        request: ServerHttpRequest,
        path: String,
        bodyText: String? = null
    ): Mono<ResponseEntity<String>> =
        mockService.getResponse(MockRequest.create(request, path, bodyText))
            .map {
                ResponseEntity
                    .status(it.httpStatusCode ?: HttpStatus.OK.value())
                    .body(it.body.toString())
            }

    @RequestMapping(
        value = ["{*path}"],
        method = [RequestMethod.GET]
    )
    fun apiGet(
        request: ServerHttpRequest,
        @PathVariable path: String
    ): Mono<ResponseEntity<String>> = convertResponse(request, path)

    @RequestMapping(
        value = ["{*path}"],
        method = [RequestMethod.POST, RequestMethod.PUT, RequestMethod.PATCH, RequestMethod.DELETE]
    )
    fun apiBody(
        request: ServerHttpRequest,
        @PathVariable path: String,
        @RequestBody(required = false) bodyText: String?
    ): Mono<ResponseEntity<String>> = convertResponse(request, path, bodyText)

}
