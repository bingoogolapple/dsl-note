package cn.bingoogolapple.dsl.kotlin.coroutine.sequence

import kotlin.coroutines.experimental.buildSequence

fun testSequence() {
    for (i in fibonacci) {
        println(i)
        if (i > 3) {
            break
        }
    }
}

val fibonacci = buildSequence {
    yield(1)
    var cur = 1
    var next = 1

    while (true) {
        yield(next)
        val tmp = cur + next
        cur = next
        next = tmp
    }
}