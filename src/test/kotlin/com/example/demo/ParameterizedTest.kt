package com.example.demo

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class ParameterizedTestExample {

    @BeforeEach
    fun doBeforeEachTest() {
        println("blah")
    }

    fun isPalindrome(s: String?): Boolean {
        return if (s == null) false else s.reversed() == s
    }

    @ParameterizedTest(name = "{index} - {0} is a palindrome")
    @ValueSource(strings = ["12321", "pop"])
    fun testPalindrome(word: String?) {
        assertTrue(isPalindrome(word))
    }
}