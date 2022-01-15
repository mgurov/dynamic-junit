package com.example.demo

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.SoftAssertions
import org.junit.jupiter.api.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

fun isPalindrome(s: String?): Boolean {
    println(s)
    return s?.reversed() == s
}

class DynamicTestExample {


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

    @BeforeEach
    fun beforeEach() {
        println("before each")
    }

    @ParameterizedTest()
    @MethodSource("getTestCases")
    fun `palindrome or not asserted parametrizedly`(case: Pair<String, Boolean>) {
        assertThat(isPalindrome(case.first)).isEqualTo(case.second)
    }

    @Test
    fun `palindrome or not asserted softly`() {

        SoftAssertions.assertSoftly { softly ->
            testCases.forEach { (given, expected) ->
                softly.assertThat(isPalindrome(given))
                    .describedAs("$given is " + (if (!expected) "not " else "") + "a palindrome")
                    .isEqualTo(expected)
            }
        }

    }

    @TestFactory
    fun `palindrome or not`(): List<DynamicNode> {
        println("producing test methods")

        val palindromes = DynamicContainer.dynamicContainer(
            "these are palindromes", listOf("poop", "", null).map {
                DynamicTest.dynamicTest("-> " + it) {
                    assertThat(isPalindrome(it)).isTrue()
                }
            }
        )

        val nonPalindromes = DynamicContainer.dynamicContainer(
            "these aren't palindromes", listOf("poo", " a").map {
                DynamicTest.dynamicTest(it) {
                    assertThat(isPalindrome(it)).isFalse()
                }
            }
        )

        return listOf(palindromes, nonPalindromes)
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