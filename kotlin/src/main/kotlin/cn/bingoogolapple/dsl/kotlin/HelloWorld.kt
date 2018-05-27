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
 *
 * 导出可执行程序：
 * 1.添加插件「apply plugin: 'application'」
 * 2.配置主类「mainClassName = 'cn.bingoogolapple.dsl.kotlin.HelloWorldKt'」
 * 3.执行 distribution 分组下的 installDist 任务
 * 4.build/install/kotlin/bin/kotlin 就是可执行文件
 */