package cn.bingoogolapple.dsl.kotlin

import java.io.OutputStream
import java.nio.charset.Charset

/**
 * Currying：多元函数变成一元函数调用链
 * 偏函数：传入部分参数得到新的函数
 */

fun log(tag: String, target: OutputStream, message: Any?) {
    target.write("[$tag] $message\n".toByteArray())
}

//fun log(tag: String)
//    = fun(target: OutputStream)
//    = fun(message: Any?)
//    = target.write("[$tag] $message\n".toByteArray())

fun <P1, P2, P3, R> Function3<P1, P2, P3, R>.curried() = fun(p1: P1) = fun(p2: P2) = fun(p3: P3) = this(p1, p2, p3)


val makeString = fun(byteArray: ByteArray, charset: Charset): String {
    return String(byteArray, charset)
}

fun <P1, P2, R> Function2<P1, P2, R>.partial2(p2: P2) = fun(p1: P1) = this(p1, p2)
fun <P1, P2, R> Function2<P1, P2, R>.partial1(p1: P1) = fun(p2: P2) = this(p1, p2)
val makeStringFromGbkBytes = makeString.partial2(charset("GBK"))

fun main(args: Array<String>) {
    log("BGA", System.out, "HelloWorld")
//    log("BGA")(System.out)("HelloWorld Again.")
    ::log.curried()("BGA")(System.out)("HelloWorld Again.")

    // consoleLogWithTag 为 log 的偏函数
    val consoleLogWithTag = ::log.curried()("BGA")(System.out)
    consoleLogWithTag("HelloAgain Again.")
    consoleLogWithTag("HelloAgain Again.")

    val bytes = "我是中国人".toByteArray(charset("GBK"))
    val stringFromGBK = makeStringFromGbkBytes(bytes)
}