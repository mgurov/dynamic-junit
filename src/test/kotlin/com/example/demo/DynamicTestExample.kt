package com.example.demo

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory
import org.junit.platform.engine.discovery.DiscoverySelectors
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
            DynamicTest.dynamicTest("$word should be a palindrome", URI.create("file:/Users/mgurov/src/mine/parameterized-junit/src/test/kotlin/com/example/demo/DynamicTestExample.kt?line=20,column=2")) {
                Assertions.assertTrue(isPalindrome(word))
            }
        }
    }

}