package cn.bingoogolapple.dsl.kotlin.coroutine.demo

import kotlinx.coroutines.experimental.*
import kotlin.system.measureTimeMillis

/**
 * https://github.com/Kotlin/kotlinx.coroutines/blob/master/docs/composing-suspending-functions.md
 * https://github.com/Kotlin/kotlinx.coroutines/blob/master/core/kotlinx-coroutines-core/test/guide/example-compose-01.kt
 */
fun main(args: Array<String>) {
//    testOne()
//    testTwo()
//    testThree()
    testFour()
}

fun testOne() = runBlocking {
    val timeOne = measureTimeMillis {
        val one: Int = doSomethingUsefulOne("sequential")
        val two = doSomethingUsefulTwo("sequential")
        println("The answer is ${one + two}")
    }
    println("One：Completed in $timeOne ms")

    /**
     * async 和 launch 差不多，它开启一个独立的协程（轻量级的线程），和其它协程并行工作。
     * 它们的不同点在于，launch 返回一个 Job 对象，并且不会有任何返回值，
     * 而 async 返回一个 Deferred 对象——一个轻量级的非阻塞式的 future 对象，它代表稍后会返回一个结果的承诺（promise）。
     * 你可以在一个 deferred 对象上使用 .await() 方法以获取最终值。
     * 当然，Deferred 继承了 Job ，所以如果有需要，你也可以取消它
     */
    val timeTwo = measureTimeMillis {
        val one: Deferred<Int> = async { doSomethingUsefulOne("async") }
        val two = async { doSomethingUsefulTwo("async") }
        // 不调用 await 也会执行，且不会阻塞在这里。调用了 await 后会等待执行完成，两个是异步执行的
        println("The answer is ${one.await() + two.await()}")
    }
    println("Two：Completed in $timeTwo ms")

    val timeThree = measureTimeMillis {
        val one: Deferred<Int> = async(start = CoroutineStart.LAZY) { doSomethingUsefulOne("async start") }
        val two = async(start = CoroutineStart.LAZY) { doSomethingUsefulTwo("async start") }
        // async 函数有一个可选的参数 start，用于配置是否开启惰性求值。如果把它设置为 CoroutineStart.LAZY，那么只有在调用 await 或者 start 方法时才会开启协程

        // 调用 start 后两个是异步执行的，只调用 start 不会阻塞
        one.start()
        two.start()
        // 调用了 await 后会等待执行完成，不调用 start 时两个是顺序执行的，调用了 start 时两个是异步执行的
        println("The answer is ${one.await() + two.await()}")
    }
    println("timeThree：Completed in $timeThree ms")
}

suspend fun doSomethingUsefulOne(tag: String): Int {
    println("$tag doSomethingUsefulOne")
    delay(1000L) // pretend we are doing something useful here
    return 13
}

suspend fun doSomethingUsefulTwo(tag: String): Int {
    println("$tag doSomethingUsefulTwo")
    delay(1000L) // pretend we are doing something useful here, too
    return 29
}

fun testTwo() {
    // 函数没有用 runBlocking 包裹了
    val time = measureTimeMillis {
        // 我们在协程外部初始化 async 操作。这些 xxxAsync 函数并不是 suspending functions，任何地方都可以调用它们
        val one = somethingUsefulOneAsync("async")
        val two = somethingUsefulTwoAsync("async")
        // 但是获取结果必须在 suspend funtion 中，或者在 blocking 中
        // 我们这里使用 runBlocking 阻塞主线程，等待运行结果
        runBlocking {
            println("The answer is ${one.await() + two.await()}")
        }
    }
    println("Completed in $time ms")
}

// The result type of somethingUsefulOneAsync is Deferred<Int>
fun somethingUsefulOneAsync(tag: String) = GlobalScope.async {
    doSomethingUsefulOne(tag)
}

// The result type of somethingUsefulTwoAsync is Deferred<Int>
fun somethingUsefulTwoAsync(tag: String) = GlobalScope.async {
    doSomethingUsefulTwo(tag)
}

fun testThree() = runBlocking {
    val time = measureTimeMillis {
        println("The answer is ${concurrentSum()}")
    }
    println("Completed in $time ms")
}

suspend fun concurrentSum(): Int = coroutineScope {
    val one = async { doSomethingUsefulOne("") }
    val two = async { doSomethingUsefulTwo("") }
    one.await() + two.await()
}

fun testFour() = runBlocking {
    try {
        failedConcurrentSum()
    } catch (e: ArithmeticException) {
        println("Computation failed with ArithmeticException")
    }
}

// Cancellation is always propagated through coroutines hierarchy
suspend fun failedConcurrentSum(): Int = coroutineScope {
    val one = async<Int> {
        try {
            delay(Long.MAX_VALUE) // Emulates very long computation
            42
        } finally {
            println("First child was cancelled")
        }
    }
    val two = async<Int> {
        println("Second child throws an exception")
        throw ArithmeticException()
    }
    one.await() + two.await()
}