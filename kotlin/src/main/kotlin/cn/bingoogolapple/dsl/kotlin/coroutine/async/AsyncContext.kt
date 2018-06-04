package cn.bingoogolapple.dsl.kotlin.coroutine.async

import kotlin.coroutines.experimental.AbstractCoroutineContextElement
import kotlin.coroutines.experimental.Continuation
import kotlin.coroutines.experimental.ContinuationInterceptor

/**
 * CoroutineContext 接口
 * 运行上下文，资源持有，运行调度
 *
 * ContinuationInterceptor 接口
 * 协程控制拦截器
 * 可用来处理协程调度
 */
class AsyncContext : AbstractCoroutineContextElement(ContinuationInterceptor), ContinuationInterceptor {

    override fun <T> interceptContinuation(continuation: Continuation<T>): Continuation<T> {
        return UiCotinuationWrapper(continuation.context.fold(continuation) { continuation, element ->
            if (element != this && element is ContinuationInterceptor) {
                element.interceptContinuation(continuation)
            } else {
                continuation
            }
        })
    }

}