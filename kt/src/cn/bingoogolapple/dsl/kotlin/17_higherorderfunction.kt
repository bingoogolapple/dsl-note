package cn.bingoogolapple.dsl.kotlin

import java.io.BufferedReader
import java.io.FileReader

fun main(args: Array<String>) {
    // 包级函数
    args.forEach(::println)
    // 类名引用函数，有一个隐含的参数是调用者本身
    args.filter(String::isNotEmpty)

    val customPrintln = PdfPrinter::println
    customPrintln(PdfPrinter(), "test")

    val pdfPrinter = PdfPrinter()
    // 调用者引用函数
    args.forEach(pdfPrinter::println)

//    test1()
    test2()
}

class PdfPrinter {
    fun println(any: Any) {
        kotlin.io.println(any)
    }
}

fun test1() {
    val list = listOf(2..6, 3..5, 12..15)
    val newList = list.flatMap { intRange ->
        intRange.map { intElement ->
            "No. $intElement"
        }
    }
    newList.forEach(::println)

    val sum = list.flatMap { it }.reduce { acc, intElement -> acc + intElement }
    println(sum)

    // fold 有初始值的 reduce。指定初始值为 StringBuffer 的实例对象
    println((0..6).map(::factorial).fold(StringBuffer()) { acc, i ->
        acc.append(i).append(",")
    })
}

fun factorial(n: Int): Int {
    return when (n) {
        0 -> 1
        else -> (1..n).reduce { acc, i -> acc * i }
    }
}

fun test2() {
    val person = findPerson()
    person?.let {
        it.work()
        // 这里不用再加 ? 了
        person.work()
    }
    person?.let { (name, age) ->
        println(name)
        println(age)
    }
    person?.apply {
        work()
        println(name)
        println(age)
    }


    val br = BufferedReader(FileReader("settings.gradle"))
    println(br.readText())

    with(br) {
        var line: String?
        while (true) {
            line = readLine() ?: break
            println(line)
        }
        close()
    }

    br.use {
        var line: String?
        while (true) {
            line = it.readLine() ?: break
            println(line)
        }
        // 不用再调 close 方法，use 内部已经调了
    }
}

data class Person(val name: String, val age: Int) {
    fun work() {
        println("$name is working!!!")
    }
}

fun findPerson(): Person? {
    return null
}