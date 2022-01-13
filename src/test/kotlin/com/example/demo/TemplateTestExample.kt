package com.example.demo

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.TestTemplate
import org.junit.jupiter.api.extension.*
import java.util.stream.Stream


val fruits: List<String> = listOf("apple", "banana", "lemon")


class TemplateTestExample {

    @TestTemplate
    @ExtendWith(MyTestTemplateInvocationContextProvider::class)
    fun testTemplate(fruit: String) {
        assertTrue(fruits.contains(fruit + "a"))
    }

}

class MyTestTemplateInvocationContextProvider : TestTemplateInvocationContextProvider {
    override fun supportsTestTemplate(context: ExtensionContext): Boolean {
        return true
    }

    override fun provideTestTemplateInvocationContexts(
        context: ExtensionContext,
    ): Stream<TestTemplateInvocationContext> {
        return Stream.of(invocationContext("apple"), invocationContext("banana"))
    }

    private fun invocationContext(parameter: String): TestTemplateInvocationContext {
        return object : TestTemplateInvocationContext {
            override fun getDisplayName(invocationIndex: Int): String {
                return parameter
            }

            override fun getAdditionalExtensions(): List<Extension> {
                return listOf(object : ParameterResolver {
                    override fun supportsParameter(
                        parameterContext: ParameterContext,
                        extensionContext: ExtensionContext,
                    ): Boolean {
                        return parameterContext.parameter.type == String::class.java
                    }

                    override fun resolveParameter(
                        parameterContext: ParameterContext,
                        extensionContext: ExtensionContext,
                    ): Any {
                        return parameter
                    }
                })
            }
        }
    }
}