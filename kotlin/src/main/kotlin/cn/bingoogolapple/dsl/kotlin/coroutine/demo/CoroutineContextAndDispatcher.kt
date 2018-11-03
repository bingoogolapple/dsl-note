package cn.bingoogolapple.dsl.kotlin.coroutine.demo

import kotlinx.coroutines.experimental.*


/**
 * https://github.com/Kotlin/kotlinx.coroutines/blob/master/docs/coroutine-context-and-dispatchers.md
 * https://github.com/Kotlin/kotlinx.coroutines/blob/master/core/kotlinx-coroutines-core/test/guide/example-context-01.kt
 */
fun main(args: Array<String>) {
    CoroutineContextAndDispatcher().testOne()
//    testTwo()
//    testThree()
}

class CoroutineContextAndDispatcher {
    fun testOne() = runBlocking {
        /**
         * GlobalScope.launch 开启的协程在子线程。不受调用的 suspending function 影响
         */
        GlobalScope.launch {
            // DefaultDispatcher-worker-1
            println("Outer GlobalScope.launch1: ${Thread.currentThread().name}")
            delay(1000)
            // DefaultDispatcher-worker-2
            println("Outer GlobalScope.launch2: ${Thread.currentThread().name}")
        }
        /**
         * launch 开启协程时，不指定 CoroutineContext 的话默认在父协程所在线程。不受调用的 suspending function 影响
         */
        launch {
            // main
            println("Outer launch1: ${Thread.currentThread().name}")
            delay(1000)
            // main
            println("Outer launch2: ${Thread.currentThread().name}")
        }
        /**
         * 协程调度器 Dispatchers.Unconfined 会在调用它的线程里开启，但这只限于第一个暂停点之前。
         * 在它暂停之后，恢复的线程就由调用的 suspending function 来决定了。
         * Unconfined 调度器适合一些既不太消耗 CPU 时间，又不需要更改绑定在特定线程的共享数据的协程去使用
         */
        launch(Dispatchers.Unconfined) {
            // main
            println("Outer Dispatchers.Unconfined1: ${Thread.currentThread().name}")
            delay(1000)
            // kotlinx.coroutines.DefaultExecutor
            println("Outer Dispatchers.Unconfined2: ${Thread.currentThread().name}")
        }
        /**
         * 子线程，不受调用的 suspending function 影响
         */
        launch(Dispatchers.Default) {
            // DefaultDispatcher-worker-1
            println("Outer Dispatchers.Default1: ${Thread.currentThread().name}")
            delay(1000)
            // DefaultDispatcher-worker-3
            println("Outer Dispatchers.Default2: ${Thread.currentThread().name}")
        }
        /**
         * 子线程，不受调用的 suspending function 影响
         */
        launch(Dispatchers.IO) {
            // DefaultDispatcher-worker-2
            println("Outer Dispatchers.IO1: ${Thread.currentThread().name}")
            delay(1000)
            // DefaultDispatcher-worker-3
            println("Outer Dispatchers.IO2: ${Thread.currentThread().name}")
        }
        // newSingleThreadContext 会创建一个新的线程，不受调用的 suspending function 影响。这很耗费资源。在实际项目中，如果不再使用它，需要通过 close 方法释放，或者存储成一个顶级变量，然后再程序里复用它
        launch(newSingleThreadContext("BGA")) {
            // BGA
            println("newSingleThreadContext1: ${Thread.currentThread().name}")
            delay(1000)
            println("newSingleThreadContext2: ${Thread.currentThread().name}")

            /**
             * GlobalScope.launch 开启的协程在子线程。不受调用的 suspending function 影响
             */
            GlobalScope.launch {
                // DefaultDispatcher-worker-7
                println("Inner GlobalScope.launch1: ${Thread.currentThread().name}")
                delay(1000)
                // DefaultDispatcher-worker-7
                println("Inner GlobalScope.launch2: ${Thread.currentThread().name}")
            }
            /**
             * launch 开启协程时，不指定 CoroutineContext 的话默认在父协程所在线程。不受调用的 suspending function 影响
             */
            launch {
                // BGA
                println("Inner launch1: ${Thread.currentThread().name}")
                delay(1000)
                // BGA
                println("Inner launch2: ${Thread.currentThread().name}")
            }
            /**
             * 协程调度器 Dispatchers.Unconfined 会在调用它的线程里开启，但这只限于第一个暂停点之前。
             * 在它暂停之后，恢复的线程就由调用的 suspending function 来决定了。
             * Unconfined 调度器适合一些既不太消耗 CPU 时间，又不需要更改绑定在特定线程的共享数据的协程去使用
             */
            launch(Dispatchers.Unconfined) {
                // BGA
                println("Inner Dispatchers.Unconfined1: ${Thread.currentThread().name}")
                delay(1000)
                // kotlinx.coroutines.DefaultExecutor
                println("Inner Dispatchers.Unconfined2: ${Thread.currentThread().name}")
            }
            /**
             * 子线程，不受调用的 suspending function 影响
             */
            launch(Dispatchers.Default) {
                // DefaultDispatcher-worker-7
                println("Inner Dispatchers.Default1: ${Thread.currentThread().name}")
                delay(1000)
                // DefaultDispatcher-worker-7
                println("Inner Dispatchers.Default2: ${Thread.currentThread().name}")
            }
            /**
             * 子线程，不受调用的 suspending function 影响
             */
            launch(Dispatchers.IO) {
                // DefaultDispatcher-worker-7
                println("Inner Dispatchers.IO1: ${Thread.currentThread().name}")
                delay(1000)
                // DefaultDispatcher-worker-4
                println("Inner Dispatchers.IO2: ${Thread.currentThread().name}")
            }
        }
    }
}