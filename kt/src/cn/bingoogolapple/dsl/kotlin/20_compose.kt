package cn.bingoogolapple.dsl.kotlin

// f(g(x))   m(x) = f(g(x))
val add5 = { i: Int -> i + 5 }
val multiplyBy2 = { i: Int -> i * 2 }

infix fun <P1, R1AndP2, R2> Function1<P1, R1AndP2>.andThen(function: Function1<R1AndP2, R2>): Function1<P1, R2> {
    return fun(p1: P1): R2 {
        return function.invoke(this.invoke(p1))
    }
}

infix fun <P2, R2AndP1, R1> Function1<R2AndP1, R1>.compose(function: Function1<P2, R2AndP1>): Function1<P2, R1> {
    return fun(p2: P2): R1 {
        return this.invoke(function.invoke(p2))
    }
}

fun main(args: Array<String>) {
    println(add5(2))
    println(multiplyBy2(2))
    println(multiplyBy2(add5(2)))

    val add5AndMultiplyBy2 = add5 andThen multiplyBy2
    val add5ComposeMultiplyBy2 = add5 compose multiplyBy2
    println(add5AndMultiplyBy2(2)) // m(x) = f(g(x))
    println(add5ComposeMultiplyBy2(2)) // m(x) = g(f(x))
}