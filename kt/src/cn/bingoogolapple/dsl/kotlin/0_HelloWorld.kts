package cn.bingoogolapple.dsl.kotlin

if (args.isNotEmpty()) {
    println("Hi, ${args[0]} welcome to Kotlin Script!")
} else {
    println("Hi, BGA welcome to Kotlin Script!")
}
args.forEach(::println)

// kotlinc -script 0_HelloWorld.kts BGA bingoogolapple
