package cn.bingoogolapple.dsl.kotlin

fun main(args: Array<String>) {
    println(sum1(2, 3))
    println(sum2(2, 3))
    println(sum3(2, 3))
    println(sum3.invoke(2, 3))
    println(sum4(2, 3))
    println(sum4.invoke(2, 3))
    method5(intArrayOf(2, 3, 5))
    println(sum3)
    println(sum4)
}

fun sum1(arg1: Int, arg2: Int): Int {
    return arg1 + arg2
}

fun sum2(arg1: Int, arg2: Int) = arg1 + arg2

// 匿名函数
val sum3 = fun(arg1: Int, arg2: Int): Int {
    return arg1 + arg2
}

// Lambda 表达式
val sum4 = { arg1: Int, arg2: Int ->
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