package com.example.demo

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*

fun isPalindrome(s: String?): Boolean {
    return true
}

class DynamicTestExample {

    @TestFactory
    fun `palindrome or not`(): List<DynamicTest> {
        val testCases = listOf(
            "poop" to true,
            "poo" to false,
            "" to true,
            " a" to false,
            null to true,
        )

        return testCases.map { (given, expected) ->
            DynamicTest.dynamicTest("$given is " + (if (!expected) "not " else "") + "a palindrome") {
                assertThat(isPalindrome(given)).isEqualTo(expected)
            }
        }
    }
}

class VanillaTest {

    @Test
    fun `poop is a palindrome`() {
        assertThat(isPalindrome("poop")).isTrue()
    }

    @Test
    fun `poo isn't a palindrome`() {
        assertThat(isPalindrome("poo")).isFalse()
    }
}