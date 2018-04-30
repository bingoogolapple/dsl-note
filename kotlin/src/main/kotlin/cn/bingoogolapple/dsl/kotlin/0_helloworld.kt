package cn.bingoogolapple.dsl.kotlin

fun main(args: Array<String>) {
    println("Hi, ${args[0]} welcome to Kotlin!")
    args.forEach(::println)
}