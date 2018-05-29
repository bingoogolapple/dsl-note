package cn.bingoogolapple.dsl.kotlin

class Overloads {
    // 要在 Java 中也使用默认参数，需要加上 JvmOverloads 注解，转换成 Java 语法后其实是生成了两个方法
    @JvmOverloads
    fun a(int: Int = 0): Int {
        return int
    }
}

fun main(args: Array<String>) {
    val overloads = Overloads()
    overloads.a(3)

    val integerList = ArrayList<Int>()
    integerList.add(13)
    integerList.add(2)
    integerList.add(3)
    integerList.add(23)
    integerList.add(5)
    integerList.add(15)
    integerList.add(50)
    integerList.add(500)
    println(integerList)

    integerList.removeAt(1)
    integerList.remove(5)
    println(integerList)
}