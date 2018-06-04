package cn.bingoogolapple.dsl.kotlin.coroutine.async

import javax.swing.SwingUtilities
import kotlin.coroutines.experimental.Continuation

/**
 * Continuation 接口
 * 运行控制类，负责结果和异常的返回
 */
class UiCotinuationWrapper<T>(val continuation: Continuation<T>): Continuation<T>{
    override val context = continuation.context

    override fun resume(value: T) {
        SwingUtilities.invokeLater { continuation.resume(value) }
    }

    override fun resumeWithException(exception: Throwable) {
        SwingUtilities.invokeLater { continuation.resumeWithException(exception) }
    }

}