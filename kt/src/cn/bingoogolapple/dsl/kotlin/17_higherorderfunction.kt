package cn.bingoogolapple.dsl.kotlin

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

    test1()
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

    // 指定初始值为 StringBuffer 的实例对象
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