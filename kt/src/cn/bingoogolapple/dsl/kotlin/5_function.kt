package cn.bingoogolapple.dsl.kotlin

fun main(args: Array<String>) {
    // 句名参数
    println(method1(arg2 = 2, arg1 = 3))
    println(method1(2))
    println(method2(2, 3))
    println(method2(arg2 = 3))
    println(method3(2, 3))
    println(method3.invoke(2, 3))
    println(method4(2, 3))
    println(method4.invoke(2, 3))
    method5(intArrayOf(2, 3, 5))
    println(method3)
    println(method4)
    method6(2, 4, 6, 8)
}

fun method1(arg1: Int, arg2: Int = 3): Int {
    return arg1 + arg2
}

fun method2(arg1: Int = 3, arg2: Int) = arg1 + arg2

// 匿名函数
val method3 = fun(arg1: Int, arg2: Int): Int {
    return arg1 + arg2
}

// Lambda 表达式
val method4 = { arg1: Int, arg2: Int ->
    arg1 + arg2
}

fun method5(args: IntArray) {
    args.forEach({
        println(it)
    })
    // 最后一个参数是 Lambda 表达式，可以移到小括号后面
    args.forEach {
        println(it)
    }
    args.forEach(::println)

    args.forEach BGA@{ element ->
        if (element == 3) {
            // 跳到指定标签。2、3、5 输出 2、5
            return@BGA
            // 跳出 Lambda 外面的方法
//            return
        }
        println(element)
    }
    println("结束")
}

fun method6(vararg args: Int) {
    println("============== 变长参数 ==============")
    args.forEach(::println)
}