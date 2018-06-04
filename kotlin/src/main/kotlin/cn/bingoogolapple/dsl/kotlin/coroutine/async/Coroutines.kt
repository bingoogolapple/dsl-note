package cn.bingoogolapple.dsl.kotlin.coroutine.async

import cn.bingoogolapple.dsl.kotlin.coroutine.common.HttpError
import cn.bingoogolapple.dsl.kotlin.coroutine.common.HttpException
import cn.bingoogolapple.dsl.kotlin.coroutine.common.HttpService
import cn.bingoogolapple.dsl.kotlin.coroutine.common.log
import kotlin.coroutines.experimental.CoroutineContext
import kotlin.coroutines.experimental.EmptyCoroutineContext
import kotlin.coroutines.experimental.startCoroutine
import kotlin.coroutines.experimental.suspendCoroutine

/**
 * createCoroutine 创建协程
 * startCoroutine 启动协程
 * suspendCoroutine 挂起协程
 */


fun 我要开始协程啦(context: CoroutineContext = EmptyCoroutineContext, block: suspend () -> Unit) {
    block.startCoroutine(ContextContinuation(context + AsyncContext()))
}

suspend fun <T> 我要开始耗时操作了(block: CoroutineContext.() -> T) = suspendCoroutine<T> { continuation ->
    log("异步任务开始前")
    AsyncTask {
        try {
            continuation.resume(block(continuation.context))
        } catch (e: Exception) {
            continuation.resumeWithException(e)
        }
    }.execute()
}

fun 我要开始加载图片啦(url: String): ByteArray {
    log("异步任务开始前")
    log("耗时操作，下载图片")
    val responseBody = HttpService.service.getLogo(url).execute()
    if (responseBody.isSuccessful) {
        responseBody.body()?.byteStream()?.readBytes()?.let {
            return it
        }
        throw HttpException(HttpError.HTTP_ERROR_NO_DATA)
    } else {
        throw HttpException(responseBody.code())
    }
}