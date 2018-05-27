package cn.bingoogolapple.dsl.kotlin

val a: String = "HelloWorld"
val b: String = String(charArrayOf('H', 'e','l','l','o','W','o','r','l','d'))

println(a == b) // true 比较内容，类似 Java 中的 equals
println(a.equals(b)) // true 等价于 == 操作符
println(a === b) // false // 比较对象是否相同

val c: Int = 2
val d: Int = 3
println("$c + $d = ${c + d}")

// 原因输出。没有 Groovy 强大，Groovy 支持三个单引号、三个双引号
val e = """
BGA
    BGA${(11 + 22) / 3}
BGA
"""
println(e) // java.lang.String
println(e.javaClass)