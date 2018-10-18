package cn.bingoogolapple.dsl.kotlin.coroutine.demo

import kotlinx.coroutines.experimental.*
import kotlin.concurrent.thread

/**
 * https://saplf.gitbooks.io/kotlinx-coroutines/content/chapter1/xie-cheng-ji-chu/di-yi-ge-xie-cheng.html
 * https://github.com/Kotlin/kotlinx.coroutines/blob/master/docs/basics.md#coroutine-basics
 * https://github.com/Kotlin/kotlinx.coroutines/blob/master/core/kotlinx-coroutines-core/test/guide/example-basic-01.kt
 *
 */

fun main(args: Array<String>) {
//    testLaunch()
//    testThread()
//    testRunBlockingOne()
//    println(testRunBlockingTwo())
//    println(testJobOne())
//    println(testJobTwo())
//    testScope()
//    testSuspend()
//    lightWeight()
    likeDaemonThread()
}

fun testLaunch() {
    // 在后台启动一个协程
    GlobalScope.launch {
        delay(1000L) // 非阻塞式延迟 1 秒（默认的时间单位是毫秒）
        println("2") // 延迟后输出
    }
    println("1") // 协程虽然延迟了，主线程还在继续
    Thread.sleep(2000L) // 阻塞主线程 2 秒，保活
}

fun testThread() {
    thread {
        // delay 搭配 thread 一起使用时编译器报错：Suspend function 'delay' should be called only from a coroutine or another suspend function
        // 因为 delay 是一个特殊的 suspend function ，它不会锁死线程，仅仅是挂起协程，它也只能在协程内部使用
        // delay(1000L)
        Thread.sleep(1000L)
        println("2") // 延迟后输出
    }
    print("1")
    Thread.sleep(2000L) // 阻塞主线程 2 秒，保活
}

fun testRunBlockingOne() {
    GlobalScope.launch {
        println("2")
        delay(1000L)
        println("4")
    }
    println("1")
    // but this expression blocks the main thread
    runBlocking {
        println("3")
        delay(2000L)
        println("5")
    }
    println("6")
}

fun testRunBlockingTwo(): String = runBlocking<String> {
    GlobalScope.launch {
        println("2")
        delay(1000L)
        println("3")
    }
    println("1")
    // delaying for 2 seconds to keep JVM alive
    delay(2000L)
    println("4")
    return@runBlocking "finish"
}

fun testJobOne(): String = runBlocking<String> {
    // launch new coroutine and keep a reference to its Job
    val job = GlobalScope.launch {
        println("2")
        delay(1000L)
        println("3")
    }
    println("1")
    // wait until child coroutine completes
    job.join()
    println("4")
    return@runBlocking "finish"
}

fun testJobTwo(): String = runBlocking<String> {
    // launch new coroutine in the scope of runBlocking
    val job = launch {
        println("2")
        delay(1000L)
        println("3")
    }
    println("1")
    // wait until child coroutine completes
    job.join()
    println("4")
    return@runBlocking "finish"
}

fun testScope() = runBlocking {
    launch {
        println("4")
        delay(2000L)
        println("6")
    }
    println("1")

    coroutineScope {
        println("2")
        launch {
            println("5")
            delay(3000L)
            println("7")
        }
        println("3")
        delay(1000L)
    }
    println("finish")
}

fun testSuspend() = runBlocking {
    launch {
        println("2")
        customSuspendFunction()
        println("5")
    }
    println("1")
}

// 在协程内部的使用和普通函数一样，特别之处在于内部还可以调用其它的 suspending functions，就像这个例子里的 delay，挂起一个协程的执行
suspend fun customSuspendFunction() {
    println("3")
    delay(1000L)
    println("4")
    launchDoWorld()
    println("Hello,")
}

// this is your first suspending function
suspend fun launchDoWorld() = currentScope {
    launch {
        println("World!")
    }
}

fun lightWeight() = runBlocking {
    repeat(100_000) {
        // launch a lot of coroutines
        launch {
            delay(1000L)
            println(it)
        }
    }
    delay(1200L)
    println("finish")
}

fun likeDaemonThread() = runBlocking {
    GlobalScope.launch {
        repeat(1000) { i ->
            println("I'm sleeping $i ...")
            delay(500L)
        }
    }
    delay(1300L) // just quit after delay
}