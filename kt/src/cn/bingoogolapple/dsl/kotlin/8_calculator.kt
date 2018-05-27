package cn.bingoogolapple.dsl.kotlin

fun main(args: Array<String>) {
    while (true) {
        try {
            println("请输入算式例如： 3 + 4")
            val input = readLine() ?: break
            val splits = input.trim().split(" ")
            if (splits.size < 3) {
                throw IllegalArgumentException("参数个数不对")
            }
            val arg1 = splits[0].toDouble()
            val op = splits[1]
            val arg2 = splits[2].toDouble()
            println("$arg1 $op $arg2 = ${Operator(op)(arg1, arg2)}")
        } catch (e: NumberFormatException) {
            println("您确定输入的是数字吗？")
        } catch (e: IllegalArgumentException) {
            println("您确定输入的是三个参数吗？或者您确定您的输入是用空格分隔的吗？")
        } catch (e: Exception) {
            println("亲爱的用户，您的人品太差了，程序遇到了未知的异常，${e.message}")
        }

        println("再来一发?[Y]")
        val cmd = readLine()
        if (cmd == null || cmd.toLowerCase() != "y") {
            break
        }
    }
    println("感谢您使用我们的计算器。")
}

class Operator(op: String) {
    val opFun: (left: Double, right: Double) -> Double

    init {
        opFun = when (op) {
            "+" -> { l, r -> l + r }
            "-" -> { l, r -> l - r }
            "*" -> { l, r -> l * r }
            "/" -> { l, r -> l / r }
            "%" -> { l, r -> l % r }
            else -> {
                throw UnsupportedOperationException(op)
            }
        }
    }

    operator fun invoke(left: Double, right: Double): Double {
        return opFun(left, right)
    }

}