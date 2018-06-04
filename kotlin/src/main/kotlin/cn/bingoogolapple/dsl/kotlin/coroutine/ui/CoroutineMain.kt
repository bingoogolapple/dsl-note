package cn.bingoogolapple.dsl.kotlin.coroutine.ui

import cn.bingoogolapple.dsl.kotlin.coroutine.async.DownloadContext
import cn.bingoogolapple.dsl.kotlin.coroutine.async.我要开始加载图片啦
import cn.bingoogolapple.dsl.kotlin.coroutine.async.我要开始协程啦
import cn.bingoogolapple.dsl.kotlin.coroutine.async.我要开始耗时操作了
import cn.bingoogolapple.dsl.kotlin.coroutine.common.HttpError
import cn.bingoogolapple.dsl.kotlin.coroutine.common.HttpException
import cn.bingoogolapple.dsl.kotlin.coroutine.common.HttpService
import cn.bingoogolapple.dsl.kotlin.coroutine.common.log
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.swing.JFrame
import javax.swing.SwingUtilities

const val LOGO_URL = "https://avatars0.githubusercontent.com/u/8949716?v=4"

/**
 * 协程被编译成状态机
 * suspend 函数即状态转移
 * 正常通过 resume 返回
 * 异常通过 resumeWithException 抛出
 *        ←  ←
 *        ↓  ↑
 *        ↓  ↑
 * 开始 → 状态机 → 结束
 *          ↓
 *         异常
 *
 */
object CoroutineMain {

    fun main() {
        val frame = MainWindow()
        frame.title = "协程学习笔记"
        frame.setSize(200, 150)
        frame.isResizable = true
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.init()
        frame.isVisible = true
        frame.onButtonClick {
//            callback(frame)
            coroutime(frame)
        }
    }

    fun coroutime(frame: MainWindow) {
        log("协程之前")
//        我要开始协程啦 {
//            val imageData = 我要开始加载图片啦(LOGO_URL)
//            frame.setLogo(imageData)
//            log("拿到图片")
//        }

        我要开始协程啦(DownloadContext(LOGO_URL)) {
            log("协程开始")
            try {
                val imageData = 我要开始耗时操作了 {
                    我要开始加载图片啦(this[DownloadContext]!!.url)
                }
                log("拿到图片")
                frame.setLogo(imageData)
            } catch(e: Exception) {
                e.printStackTrace()
            }
        }
        log("协程之后")
    }

    fun callback(frame: MainWindow) {
        HttpService.service.getLogo(LOGO_URL)
                .enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        if (response.isSuccessful) {
                            val imageData = response.body()?.byteStream()?.readBytes()
                            if (imageData == null) {
                                throw HttpException(HttpError.HTTP_ERROR_NO_DATA)
                            } else {
                                SwingUtilities.invokeLater {
                                    frame.setLogo(imageData)
                                }
                            }
                        } else {
                            throw HttpException(response.code())
                        }
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        throw HttpException(HttpError.HTTP_ERROR_UNKNOWN)
                    }

                })
    }
}