import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DynamicContainer.dynamicContainer
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.function.Executable
import org.junit.jupiter.api.function.ThrowingConsumer
import java.util.*
import java.util.function.Function
import java.util.function.IntFunction
import java.util.function.IntUnaryOperator
import java.util.stream.IntStream
import java.util.stream.Stream

class Calculator {
    fun multiply(x: Int, y: Int) = x * y

}

fun isPalindrome(s: String?) = if (s == null) false else s.reversed() == s

internal class DynamicTestsDemo {
    private val calculator: Calculator = Calculator()

    // This will result in a JUnitException!
    @TestFactory
    fun dynamicTestsWithInvalidReturnType(): List<String> {
        return Arrays.asList("Hello")
    }

    @TestFactory
    fun dynamicTestsFromCollection(): Collection<DynamicTest> {
        return Arrays.asList<DynamicTest>(
            DynamicTest.dynamicTest("1st dynamic test", Executable { assertTrue(isPalindrome("madam")) }),
            DynamicTest.dynamicTest("2nd dynamic test", Executable { assertEquals(4, calculator.multiply(2, 2)) })
        )
    }

    @TestFactory
    fun dynamicTestsFromIterable(): Iterable<DynamicTest> {
        return Arrays.asList<DynamicTest>(
            DynamicTest.dynamicTest("3rd dynamic test", Executable { assertTrue(isPalindrome("madam")) }),
            DynamicTest.dynamicTest("4th dynamic test", Executable { assertEquals(4, calculator.multiply(2, 2)) })
        )
    }

    @TestFactory
    fun dynamicTestsFromIterator(): Iterator<DynamicTest> {
        return Arrays.asList<DynamicTest>(
            DynamicTest.dynamicTest("5th dynamic test", Executable { assertTrue(isPalindrome("madam")) }),
            DynamicTest.dynamicTest("6th dynamic test", Executable { assertEquals(4, calculator.multiply(2, 2)) })
        ).iterator()
    }

    @TestFactory
    fun dynamicTestsFromArray(): Array<DynamicTest> {
        return arrayOf<DynamicTest>(
            DynamicTest.dynamicTest("7th dynamic test", Executable { assertTrue(isPalindrome("madam")) }),
            DynamicTest.dynamicTest("8th dynamic test", Executable { assertEquals(4, calculator.multiply(2, 2)) })
        )
    }

    @TestFactory
    fun dynamicTestsFromStream(): Stream<DynamicTest> {
        return Stream.of("racecar", "radar", "mom", "dad")
            .map<DynamicTest>(Function<String, DynamicTest> { text: String? ->
                DynamicTest.dynamicTest(text,
                    Executable { assertTrue(isPalindrome(text)) })
            })
    }

    @TestFactory
    fun dynamicTestsFromIntStream(): Stream<DynamicTest> {
        // Generates tests for the first 10 even integers.
        return IntStream.iterate(0, IntUnaryOperator { n: Int -> n + 2 }).limit(10)
            .mapToObj<DynamicTest>(IntFunction<DynamicTest> { n: Int ->
                DynamicTest.dynamicTest("test$n",
                    Executable { assertTrue(n % 2 == 0) })
            })
    }

    @TestFactory
    fun generateRandomNumberOfTestsFromIterator(): Stream<DynamicTest> {

        // Generates random positive integers between 0 and 100 until
        // a number evenly divisible by 7 is encountered.
        val inputGenerator: Iterator<Int> = object : MutableIterator<Int> {
            var random = Random()
            var current = 0
            override fun hasNext(): Boolean {
                current = random.nextInt(100)
                return current % 7 != 0
            }

            override fun next(): Int {
                return current
            }

            override fun remove() {
                TODO("Not yet implemented")
            }
        }

        // Generates display names like: input:5, input:37, input:85, etc.
        val displayNameGenerator = Function { input: Int -> "input:$input" }

        // Executes tests based on the current input value.
        val testExecutor = ThrowingConsumer { input: Int -> Assertions.assertTrue(input % 7 != 0) }

        // Returns a stream of dynamic tests.
        return DynamicTest.stream<Int>(inputGenerator, displayNameGenerator, testExecutor)
    }

    @TestFactory
    fun dynamicTestsFromStreamFactoryMethod(): Stream<DynamicTest> {
        // Stream of palindromes to check
        val inputStream = Stream.of("racecar", "radar", "mom", "dad")

        // Generates display names like: racecar is a palindrome
        val displayNameGenerator = Function { text: String -> "$text is a palindrome" }

        // Executes tests based on the current input value.
        val testExecutor = ThrowingConsumer { text: String? -> assertTrue(isPalindrome(text)) }

        // Returns a stream of dynamic tests.
        return DynamicTest.stream<String>(inputStream, displayNameGenerator, testExecutor)
    }

    @TestFactory
    fun dynamicTestsFromStreamFactoryMethodWithNames(): Stream<DynamicTest> {
        // Stream of palindromes to check
        val inputStream = Stream.of(
            Named.named("racecar is a palindrome", "racecar"),
            Named.named("radar is also a palindrome", "radar"),
            Named.named("mom also seems to be a palindrome", "mom"),
            Named.named("dad is yet another palindrome", "dad")
        )

        // Returns a stream of dynamic tests.
        return DynamicTest.stream<String>(inputStream,
            ThrowingConsumer { text: String? -> assertTrue(isPalindrome(text)) })
    }

    @TestFactory
    fun dynamicTestsWithContainers(): List<DynamicNode> {
        return listOf("A", "B", "C")
            .map { input: String ->
                dynamicContainer(
                    "Container $input", listOf(
                        dynamicTest("not null") { assertNotNull(input) },
                        dynamicContainer("properties", listOf(
                            dynamicTest("length > 0") { assertTrue(input.length > 0) },
                            dynamicTest("not empty", { assertFalse(input.isEmpty()) })
                        ))
                    ))
            }
    }

    @TestFactory
    fun dynamicNodeSingleTest(): DynamicNode {
        return DynamicTest.dynamicTest("'pop' is a palindrome", Executable { assertTrue(isPalindrome("pop")) })
    }

    @TestFactory
    fun dynamicNodeSingleContainer(): DynamicNode {
        return DynamicContainer.dynamicContainer("palindromes",
            Stream.of("racecar", "radar", "mom", "dad")
                .map<DynamicTest>(Function<String, DynamicTest> { text: String? ->
                    DynamicTest.dynamicTest(text,
                        Executable { assertTrue(isPalindrome(text)) })
                }
                ))
    }
}