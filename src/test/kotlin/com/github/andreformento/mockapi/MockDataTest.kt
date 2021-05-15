package com.github.andreformento.mockapi

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class MockDataTest {

    @Test
    fun shouldBeContainedWhenHaveLessFields() {
        val setData =
            MockData(""" { "name":"André Formento", "band":"Iron Maiden" } """)
        val expected =
            MockData(""" { "name":"André Formento", "band":"Iron Maiden", "extra_value": true } """)

        assertThat(setData.isSubset(expected)).isTrue()
    }

    @Test
    fun shouldNotBeContainedWhenHaveMoreFields() {
        val setData =
            MockData(""" { "name":"André Formento", "band":"Iron Maiden", "extra_value": true } """)
        val expected =
            MockData(""" { "name":"André Formento", "band":"Iron Maiden" } """)

        assertThat(setData.isSubset(expected)).isFalse()
    }

    @Test
    fun shouldNotBeContainedWhenHaveDifferentFields() {
        val setData =
            MockData(""" { "name":"André Formento", "extra_value": true } """)
        val expected =
            MockData(""" { "name":"André Formento", "band":"Iron Maiden" } """)

        assertThat(setData.isSubset(expected)).isFalse()
    }

    @Test
    fun shouldNotBeContainedWhenHaveDifferentValues() {
        val setData =
            MockData(""" { "name":"André Formento", "band":"Iron Maiden" } """)
        val expected =
            MockData(""" { "name":"André Formento", "band":"ANTI-FLAG" } """)

        assertThat(setData.isSubset(expected)).isFalse()
    }

    @Test
    fun shouldBeContainedWhenHaveAList() {
        val setData =
            MockData(""" [ 1, 2 ] """)
        val expected =
            MockData(""" [ 3, 2, 1 ] """)

        assertThat(setData.isSubset(expected)).isTrue()
    }

    @Test
    fun shouldNotBeContainedWhenHaveADifferentList() {
        val setData =
            MockData(""" [ 1, 2 ] """)
        val expected =
            MockData(""" [ 3, 1 ] """)

        assertThat(setData.isSubset(expected)).isFalse()
    }

    @Test
    fun shouldBeContainedWhenHaveAListOfObjects() {
        val setData =
            MockData(""" [ {"value": 1, "extra_value": "blah" }, {"value": 2, "another_value": "two" } ] """)
        val expected =
            MockData(""" [ {"value": 3}, {"value": 2, "another_value": "two"}, {"value": 1, "extra_value": "blah" } ] """)

        assertThat(setData.isSubset(expected)).isTrue()
    }

    @Test
    fun shouldNotBeContainedWhenHaveAListOfDifferentObjects() {
        val setData =
            MockData(""" [ {"value": 1, "extra_value": "blah" }, {"value": 2, "another_value": "two" } ] """)
        val expected =
            MockData(""" [ {"value": 1, "extra_value": "blah" }, {"value": 2, "another_value": "DOOONT" } ] """)

        assertThat(setData.isSubset(expected)).isFalse()
    }

    @Test
    fun shouldBeContainedWhenHaveAnInnerObject() {
        val setData =
            MockData(""" { "name": "blah", "my_object": {"inner_value": "abc"} } """)
        val expected =
            MockData(""" { "name": "blah", "my_object": {"inner_value": "abc"}, "extra_object": {"extra_inner_value": "def"} } """)

        assertThat(setData.isSubset(expected)).isTrue()
    }

}
