package cn.bingoogolapple.dsl.kotlin

val string = "HelloWorld"

fun makeFun(): () -> Unit {
    var count = 0
    return fun() {
        println(++count)
    }
}

fun fibonacci1(): () -> Long {
    var first = 0L
    var second = 1L
    return fun(): Long {
        val result = second
        second += first
        first = second
        return result
    }
}

fun fibonacci2(): Iterable<Long> {
    var first = 0L
    var second = 1L

    return Iterable {
        object : LongIterator() {
            override fun nextLong(): Long {
                val result = second
                second += first
                first = second - first
                return result
            }

            override fun hasNext() = true
        }

    }
}

fun main(args: Array<String>) {
//    val x = makeFun()
//    x()
//    x()
//    x()

//    val x = fibonacci1()
//    println(x())
//    println(x())
//    println(x())
//    println(x())
//    println(x())
//    println(x())

//    for (i in fibonacci2()) {
//        if (i > 100) {
//            break
//        }
//        println(i)
//    }

    val add5 = add1(5)
    println(add5(2))

}

fun test(x: Int): Int {
    return x + 2
}

fun test1(x: Int): Int = x + 2

fun add1(x: Int) = fun(y: Int) = x + y

fun add2(x: Int): (Int) -> Int {
    return fun(y: Int): Int {
        return x + y
    }
}
