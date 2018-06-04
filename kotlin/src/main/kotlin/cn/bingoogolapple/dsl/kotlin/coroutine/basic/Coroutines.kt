package cn.bingoogolapple.dsl.kotlin.coroutine.basic

import cn.bingoogolapple.dsl.kotlin.coroutine.common.HttpException
import cn.bingoogolapple.dsl.kotlin.coroutine.common.HttpService
import cn.bingoogolapple.dsl.kotlin.coroutine.common.log
import kotlin.coroutines.experimental.startCoroutine
import kotlin.coroutines.experimental.suspendCoroutine

fun 我要开始协程啦(block: suspend () -> Unit) {
    block.startCoroutine(BaseContinuation())
}

suspend fun 我要开始加载图片啦(url: String) = suspendCoroutine<ByteArray> { continuation ->
    log("耗时操作，下载图片")
    try {
        val responseBody = HttpService.service.getLogo(url).execute()
        if (responseBody.isSuccessful) {
            responseBody.body()?.byteStream()?.readBytes()?.let(continuation::resume)
        } else {
            continuation.resumeWithException(HttpException(responseBody.code()))
        }
    } catch (e: Exception) {
        continuation.resumeWithException(e)
    }
}