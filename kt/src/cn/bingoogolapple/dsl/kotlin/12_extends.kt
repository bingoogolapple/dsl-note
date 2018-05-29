package cn.bingoogolapple.dsl.kotlin

fun main(args: Array<String>) {
    println("abc" * 16)
    "abc".b = 10
    println("abc".b)
}

// 为现有类添加方法 fun X.y(): Z { ... }
operator fun String.times(int: Int): String{
    val stringBuilder = StringBuilder()
    for(i in 0 until int){
        stringBuilder.append(this)
    }
    return stringBuilder.toString()
}

// 为现有类添加属性 var X.m: T  扩展属性不能初始化，类似接口属性
val String.a: String
    get() = "abc"

var String.b: Int
    set(value) {
    }
    get() = 5
