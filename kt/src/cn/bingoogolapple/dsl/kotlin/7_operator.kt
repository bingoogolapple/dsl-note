package cn.bingoogolapple.dsl.kotlin

class Complex(var real: Double, var imaginary: Double) {
    operator fun plus(other: Complex): Complex {
        return Complex(real + other.real, imaginary + other.imaginary)
    }

    operator fun plus(other: Double): Complex {
        return Complex(real + other, imaginary)
    }

    operator fun plus(other: Int): String {
        return "修复返回类型$other"
    }

    override fun toString(): String {
        return "$real + ${imaginary}i"
    }
}

class Book {
    // 中缀表达式：只有一个参数，且用 infix 修饰的函数
    infix fun on(desk: Desk): Boolean {
        return false
    }
}

class Desk

private const val USERNAME = "BGA"
private const val PASSWORD = "123"
private const val DEBUG = 1
private const val USER = 0

fun main(args: Array<String>) {
    val c1 = Complex(3.0, 4.0) // 3 + 4i
    val c2 = Complex(2.0, 7.5) // 2 + 7.5i
    println(c1 + c2)
    println(c1 + 3.0)
    println(c1 + 3)

    if (Book() on Desk()) {
    }

    for ((index, value) in args.withIndex()) {
        println("$index -> $value")
    }

    var a = 5
//    Outer@ while (a > 0) {
    while (a > 0) {
        println(a)
        a--
        // 跳过 continue
        // 跳出 break
//        break@Outer
    }


    println("请输入模式：")
    val MODE = if (readLine() == "1") {
        DEBUG
    } else {
        USER
    }
    println("模式为：$MODE")

    println("请输入用户名：")
    val username = readLine()
    println("请输入密码：")
    val password = readLine()
    if (username == USERNAME && password == PASSWORD) {
        println("登录成功")
    } else {
        println("用户名或密码错误")
    }

    val x = 3
    when (x) {
        is Int -> println("Hello $x")
        in 1..100 -> println("$x is in 1..100")
        !in 1..100 -> println("$x is not in 1..100")
        args[0].toInt() -> println("x == args[0]")
    }

    val mode = when {
        args.isNotEmpty() && args[0] == "1" -> 1
        else -> 0
    }
    println(mode)

    val result = try {
        args[0].toInt() / args[1].toInt()
    } catch (e: Exception) {
        0
    }
    println(result)
}
