package cn.bingoogolapple.dsl.kotlin.coroutine.demo

import kotlinx.coroutines.experimental.*

/**
 * https://github.com/Kotlin/kotlinx.coroutines/blob/master/docs/cancellation-and-timeouts.md
 * https://github.com/Kotlin/kotlinx.coroutines/blob/master/core/kotlinx-coroutines-core/test/guide/example-cancel-01.kt
 */
fun main(args: Array<String>) {
//    cancellingCoroutineExecution()
//    cancellationIsCooperative()
//    makingComputationCodeCancellable()
//    closingResourcesWithFinally()
//    timeoutOne()
    timeoutTwo()
}

// 取消协程的执行
fun cancellingCoroutineExecution() = runBlocking {
    val job = launch {
        repeat(1000) { i ->
            println("I'm sleeping $i ...")
            delay(500L)
        }
    }
    delay(1300L) // delay a bit
    println("main: I'm tired of waiting!")
    job.cancel() // cancels the job
    job.join() // waits for job's completion
//    job.cancelAndJoin()
    println("main: Now I can quit.")
}

// 取消需要配合。如果一个协程一直在跑计算，没有去检查取消标记，那它就无法取消。即使取消了协程，还是打印完了 5 次 "I'm sleeping"
fun cancellationIsCooperative() = runBlocking {
    val startTime = System.currentTimeMillis()
    val job = launch(Dispatchers.Default) {
        var nextPrintTime = startTime
        var i = 0
        while (i < 5) { // computation loop, just wastes CPU
            // print a message twice a second
            if (System.currentTimeMillis() >= nextPrintTime) {
                println("I'm sleeping ${i++} ...")
                nextPrintTime += 500L
            }
        }
    }
    delay(1300L) // delay a bit
    println("main: I'm tired of waiting!")
    job.cancelAndJoin() // cancels the job and waits for its completion
    println("main: Now I can quit.")
}

fun makingComputationCodeCancellable() = runBlocking {
    val startTime = System.currentTimeMillis()
    val job = launch(Dispatchers.Default) {
        var nextPrintTime = startTime
        var i = 0
        // isActive 是协程内部的一个属性，它继承自 CoroutineScope 类型
        while (isActive) { // cancellable computation loop
            // print a message twice a second
            if (System.currentTimeMillis() >= nextPrintTime) {
                println("I'm sleeping ${i++} ...")
                nextPrintTime += 500L
            }
        }
    }
    delay(1300L) // delay a bit
    println("main: I'm tired of waiting!")
    job.cancelAndJoin() // cancels the job and waits for its completion
    println("main: Now I can quit.")
}

fun closingResourcesWithFinally() = runBlocking {
    val job = launch {
        try {
            repeat(1000) { i ->
                println("I'm sleeping $i ...")
                delay(500L)
            }
        } catch (e: CancellationException) {
            // 不加 try catch 也不会导致程序出错，加了 try catch 才会走到这里，因为在协程内部 CancellationException 被看作是协程结束的正常途径
            e.printStackTrace()
        } finally {
            // join 和 cancelAndJoin 都会等待 finally 块中的代码执行完成
            println("I'm running finally")

            try {
                // 当前协程已经取消了，此处使用 suspending function 会产生 CancellationException
                delay(1000L)
            } catch (e: CancellationException) {
                println("报错")
            }

            // 运行不可取消的代码块
            withContext(NonCancellable) {
                println("I'm running finally NonCancellable")
                try {
                    delay(1000L)
                } catch (e: CancellationException) {
                    println("不报错")
                }
                println("And I've just delayed for 1 sec because I'm non-cancellable")
            }
        }
    }
    delay(1300L) // delay a bit
    println("main: I'm tired of waiting!")
    job.cancelAndJoin() // cancels the job and waits for its completion
    println("main: Now I can quit.")
}

fun timeoutOne() = runBlocking {
    /**
     * TimeoutCancellationException 异常是由 withTimeout 抛出，它是 CancellationException 子类。
     * 我们之前还没有看见类似的异常栈，这是因为在协程内部，CancellationException 被看作是协程结束的正常途径。
     * 然而，在这个例子中，我们用的是 withTimeout
     */
//    withTimeout(1300L) {
//        repeat(1000) { i ->
//            println("One：I'm sleeping $i ...")
//            delay(500L)
//        }
//    }

    launch {
        // 包到协程内部就不会导致程序崩溃。因为在协程内部，CancellationException 被看作是协程结束的正常途径
        withTimeout(1300L) {
            repeat(1000) { i ->
                println("Two：I'm sleeping $i ...")
                delay(500L)
            }
        }
    }
}

fun timeoutTwo() = runBlocking {
    val resultOne = withTimeoutOrNull(1300L) {
        repeat(2) { i ->
            println("One: I'm sleeping $i ...")
            delay(500L)
        }
        "Done" // will get cancelled before it produces this result
    }
    // 未超时返回 Done
    println("ResultOne is $resultOne")

    val resultTwo = withTimeoutOrNull(1300L) {
        repeat(1000) { i ->
            println("Two: I'm sleeping $i ...")
            delay(500L)
        }
        "Done" // will get cancelled before it produces this result
    }
    // 超时返回 null
    println("ResultTwo is $resultTwo")
}