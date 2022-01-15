package com.example.demo

import org.junit.jupiter.api.*

class DynamicTestExample {

    @BeforeEach
    fun doBeforeEachTest() {
        println("blah")
    }

    fun isPalindrome(s: String?): Boolean {
        return if (s == null) false else s.reversed() == s
    }


    @TestFactory
    fun makeTests(): List<DynamicTest> {

        return listOf("12321", "poop").map { word ->
            DynamicTest.dynamicTest("$word should be a palindrome") {
                Assertions.assertTrue(isPalindrome(word))
            }
        }
    }
}