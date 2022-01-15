package com.example.demo

import io.kotest.core.spec.style.FunSpec
import org.assertj.core.api.Assertions.assertThat

class DynamicTests : FunSpec({

    listOf(
        "sam",
        "pam",
        "tima",
    ).forEach {
        context("$it should be a three letter name") {

            val givenValue1 = "value1" + it

            context("blah") {
                assertThat(givenValue1).isEqualTo("blah")
                test("nested2") {
                    assertThat(givenValue1).isEqualTo("blah")
                }
            }

            test("nested 1") {
                assertThat(givenValue1).isEqualTo("blah")
            }

            assertThat(it).hasLineCount(1)
        }
    }
})

class AnotherTest : FunSpec({

    val value1 = "value1"
    context("context1") {
        //assertThat(value1).isEqualTo("blah")
        context("the nested one").config(enabled = value1 != "value1") {

            //assertThat(value1).isEqualTo("blah")
            test("the nested another one") {
                assertThat(value1).isEqualTo("blah")
            }
            test("the nested another one") {
                assertThat(value1).isEqualTo("blah")
            }
        }
    }
})