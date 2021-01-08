/*
 * Copyright 2019 ArcTouch LLC.
 *
 * All rights reserved.
 *
 * This file, its contents, concepts, methods, behavior, and operation
 * (collectively the "Software") are protected by trade secret, patent,
 * and copyright laws. The use of the Software is governed by a license
 * agreement. Disclosure of the Software to third parties, in any form,
 * in whole or in part, is expressly prohibited except as authorized by
 * the license agreement.
 */

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
