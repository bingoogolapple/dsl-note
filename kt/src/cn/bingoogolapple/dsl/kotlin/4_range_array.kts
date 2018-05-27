package cn.bingoogolapple.dsl.kotlin

val range: IntRange = 0..3 // [0, 3]
val range_exclusive: IntRange = 0 until 3 // [0, 3) = [0, 2]

val emptyRange: IntRange = 0..-1

println(emptyRange.isEmpty())
println(range.contains(2))
println(2 in range)

for (i in range_exclusive) {
    print("$i, ")
}

println("\n====================== array ======================")

val arrayOfInt: IntArray = intArrayOf(1, 3, 5, 7, 9)
val arrayOfChar: CharArray = charArrayOf('H', 'e', 'l', 'l', 'o', 'W', 'o', 'r', 'l', 'd')
val arrayOfString: Array<String> = arrayOf("我", "是", "码农")

println(arrayOfInt.size)
for (int in arrayOfInt) {
    println(int)
}

println(arrayOfString[1])
arrayOfString[1] = "不是"
println(arrayOfString[1])

println(arrayOfChar.joinToString())
println(arrayOfInt.slice(2..4))