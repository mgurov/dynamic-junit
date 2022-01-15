package com.example.demo

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*

fun isPalindrome(s: String?): Boolean {
    return true
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