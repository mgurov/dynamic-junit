package com.example.demo

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory
import java.net.URI

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