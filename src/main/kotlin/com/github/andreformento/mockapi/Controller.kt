package com.github.andreformento.mockapi

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class Controller {

    @RequestMapping(value = ["{*mockPath}"], method = [RequestMethod.GET])
    fun apiGet(@PathVariable mockPath: String): Any {
        return mapOf("result" to mockPath)
    }

    @RequestMapping(value = ["{*mockPath}"], method = [RequestMethod.POST, RequestMethod.PUT, RequestMethod.PATCH])
    fun apiBody(@PathVariable mockPath: String, @RequestBody(required = false) requestBodyRaw: Any?): Any {
        return requestBodyRaw ?: mapOf("result" to mockPath)
    }

}
