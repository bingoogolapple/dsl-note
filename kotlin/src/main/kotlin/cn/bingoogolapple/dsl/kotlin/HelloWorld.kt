package cn.bingoogolapple.dsl.kotlin

fun main(args: Array<String>) {
    if (args.isNotEmpty()) {
        println("Hi, ${args[0]} welcome to Kotlin!")
    } else {
        println("Hi, BGA welcome to Kotlin!")
    }
    args.forEach(::println)
}

/**
 * kotlinc HelloWorld.kt -include-runtime -d HelloWorld.jar
 * java -jar HelloWorld.jar 参数1
 */