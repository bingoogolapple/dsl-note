package cn.bingoogolapple.dsl.kotlin

import java.io.File

fun main(args: Array<String>) {
    statistics2()
}

fun statistics1() {
    val map = HashMap<Char, Int>()
    File("build.gradle")
            .readText()
            .toCharArray()
            .filterNot(Char::isWhitespace)
            .forEach {
                val count = map[it]
                if (count == null) {
                    map[it] = 1
                } else {
                    map[it] = count + 1
                }
            }
    map.forEach(::println)
}

fun statistics2() {
    File("build.gradle")
            .readText()
            .toCharArray()
            .filterNot(Char::isWhitespace)
            .groupBy { it }
            .map { it.key to it.value.size }
            .forEach(::println)
}