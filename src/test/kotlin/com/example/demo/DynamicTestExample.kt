package com.example.demo

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.SoftAssertions
import org.junit.jupiter.api.DynamicNode
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.params.provider.ValueSource

fun isPalindrome(s: String?): Boolean {
    return s?.reversed() == s
}

class TestExample {

    companion object {
        @JvmStatic
        val testCases = listOf(
            "poop" to true,
            "poo" to false,
            "" to true,
            " a" to false,
            null to true,
        )
    }

    @ParameterizedTest
    @MethodSource("getTestCases")
    fun `is a palindrome or not - parameterized style`(testCase: Pair<String, Boolean>) {
        assertThat(isPalindrome(testCase.first)).isEqualTo(testCase.second)
    }

    @TestFactory
    fun `is a palindrome or not - dynamic style`(): List<DynamicNode> {
        return testCases.map { (given, expected) ->
            dynamicTest("$given -> $expected") {
                assertThat(isPalindrome(given)).isEqualTo(expected)
            }
        }
    }

    @Test
    fun `is a palindrome or not`() {
        SoftAssertions.assertSoftly {
            it.assertThat(isPalindrome("poop")).isTrue()
            it.assertThat(isPalindrome("poo")).isFalse()
            it.assertThat(isPalindrome(null)).isFalse()
        }
    }
}