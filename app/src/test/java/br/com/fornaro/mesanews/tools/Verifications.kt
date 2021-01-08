package br.com.fornaro.mesanews.tools

import org.junit.Assert.fail

suspend fun assertDoesNotThrowBlocking(block: suspend () -> Unit) {
    try {
        block()
    } catch (e: Exception) {
        fail("The test has been failed with exception: $e")
    }
}

fun assertDoesNotThrow(block: () -> Unit) {
    try {
        block()
    } catch (e: Exception) {
        fail("The test has been failed with exception: $e")
    }
}
