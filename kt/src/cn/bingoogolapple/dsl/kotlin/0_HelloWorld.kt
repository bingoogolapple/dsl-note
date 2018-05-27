package cn.bingoogolapple.dsl.kotlin

val TEST_A = 1 // val 定义运行时常量。可以通过反射来修改
const val TEST_B = 3 // const val 定义编译期常量，不能通过反射来修改

// 函数什么都不返回时，其实是返回的 Unit，可以省略不写
fun main(args: Array<String>): Unit {
    if (args.isNotEmpty()) {
        println("Hi, ${args[0]} welcome to Kotlin!")
    } else {
        println("Hi, BGA welcome to Kotlin!")
    }
    args.forEach(::println)
}

/**
 * kotlinc 0_HelloWorld.kt -include-runtime -d HelloWorld.jar
 * java -jar HelloWorld.jar 参数1
 *
 * kotlinc 0_HelloWorld.kt
 * kotlin cn.bingoogolapple.dsl.kotlin._0_HelloWorldKt 参数1 参数2
 */