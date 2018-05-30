package cn.bingoogolapple.dsl.kotlin

enum class LogLevel(val id: Int) {
    // 如果后面有自定义方法的话，末尾需要加分号来区分
    VERBOSE(2), DEBUG(4), INFO(6), WARN(8), ERROR(10), ASSERT(12);

    fun getTag(): String {
        return "$id, $name"
    }

    override fun toString(): String {
        return "$name, $ordinal"
    }
}

fun main(args: Array<String>) {
    println(LogLevel.DEBUG.ordinal)

    LogLevel.values().map {
        println(it)
        println(it.getTag())
    }

    println(LogLevel.valueOf("ERROR"))
}