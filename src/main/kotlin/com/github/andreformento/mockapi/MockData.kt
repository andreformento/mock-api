package com.github.andreformento.mockapi

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

private val objectMapper = jacksonObjectMapper()

data class MockData(val value: String) {

    private fun isSubset(subset: JsonNode, set: JsonNode): Boolean {
        if (!subset.nodeType.equals(set.nodeType)) return false

        if (subset.isValueNode) return subset.equals(set)

        val subsetList = subset.toList()
        val setList = set.toList()

        if (subsetList.size > setList.size) return false

        return subsetList.all { subsetItem ->
            setList.any { setItem ->
                isSubset(subsetItem, setItem)
            }
        }
    }

    fun isSubset(set: MockData): Boolean {
        return isSubset(objectMapper.readTree(value), objectMapper.readTree(set.value))
    }
}
