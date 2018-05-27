package cn.bingoogolapple.dsl.kotlin

/**
 * Double 64
 * Float 32
 *
 * Long 64
 * Int 32
 * Short 16
 *
 * Byte 8
 *
 * Char 16
 */

import java.math.BigDecimal

println("HelloWorld")

val TEST_ONE = 1 // val 定义常量。可以通过反射来修改
val TEST_TWO: Int = 2 // val 定义常量
//TEST_ONE = 2 // 常量不能修改

var a = 1 // var 定义变量
println(a.javaClass) // int
var b: Int = 1 // Int 而不是 int
println(b.javaClass) // int

var c = 3L
println(c.javaClass) // long
var d: Long = 3L
println(d.javaClass) // long

var e = 3.14
println(e.javaClass) // double
var f: Double = 3.14
println(f.javaClass) // double

var g = 3.14f
println(g.javaClass) // float
var h: Float = 3.14f
println(h.javaClass.simpleName) // float

var i: Short = 3
println(i.javaClass) // short

var j: Byte = 3
println(j.javaClass) // byte

var k: BigDecimal = BigDecimal(3.143)
k = BigDecimal.valueOf(3.143).multiply(BigDecimal.valueOf(3.14))
println(3.143 * 3.14) // 9.869019999999999
println(k.javaClass) // class java.math.BigDecimal
println(k.toInt()) // 9
println(k.toFloat()) // 9.86902
println(k.toDouble()) // 9.86902

var l = "BGA"
println(l.javaClass) // class java.lang.String
var m: String = "BGA"
println(m.javaClass) // class java.lang.String

var n = true
println(n.javaClass) // boolean
var o: Boolean = false
println(o.javaClass) // boolean

var r = 'B'
println(r.javaClass) // char
var s: Char = 'B'
println(s.javaClass) // char

var u: Int = 0xFF
println(u) // 255
println(15 * 16 + 15) // 255
var v: Int = 0b00000011
println(v) // 3
println(1 * 2 + 1) // 3
println(Int.MAX_VALUE) // 2147483647
println(Math.pow(2.0, 31.0) - 1) // 2.147483647E9
println(Int.MIN_VALUE) // -2147483648
println(-Math.pow(2.0, 31.0)) // -2.147483648E9


var w: Int = 5
var x: Long = w.toLong() // 必须调用 toLong 方法来转换
var y: Int = x.toInt() // 必须调用 toInt 方法来转换