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

    testDataClass()
}

/**
 * kotlinc 0_HelloWorld.kt -include-runtime -d HelloWorld.jar
 * java -jar HelloWorld.jar 参数1 参数2
 *
 * kotlinc 0_HelloWorld.kt
 * kotlin cn.bingoogolapple.dsl.kotlin._0_HelloWorldKt 参数1 参数2
 *
 * 导出可执行程序：
 * 1.添加插件「apply plugin: 'application'」
 * 2.配置主类「mainClassName = 'cn.bingoogolapple.dsl.kotlin._0_HelloWorldKt'」
 * 3.执行 distribution 分组下的 installDist 任务
 * 4.build/install/kotlin/bin/kotlin 就是可执行文件
 */

fun testDataClass() {
    val china = Country(0, "中国")
    println(china)
    println(china.component1())
    println(china.component2())
    val (id, name) = china
    println(id)
    println(name)


    val componentX = ComponentX()
    val (a, b, c, d) = componentX
    println("$a $b$c$d")

    val args: Array<String> = arrayOf("sdf", "sss")
    // 这里的 withIndex 返回的就是 IndexedValue，转换成 Java 代码时也会有对应的 component1 和 component2 方法
    for((index, value) in args.withIndex()) {
        println("$index, $value")
    }
}