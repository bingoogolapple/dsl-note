package cn.bingoogolapple.dsl.kotlin

fun main(args: Array<String>) {
    val latitude = Latitude.ofDouble(3.0)
    val latitude2 = Latitude.ofLatitude(latitude)

    println(Latitude.TAG)
}

/**
 * private constructor 私有构造方法
 * companion object 伴生对象，里面的方法和属性都是静态的
 * 不加 JvmStatic 时，其实就是生成了一个 Java 中的静态内部类，其实例全局只有一份，Java 中需要通过生成的静态内部类 Latitude.Companion.ofLatitude 来访问
 * 加了 JvmStatic 后，可以直接通过 Latitude.ofDouble 来访问
 */
class Latitude private constructor(val value: Double) {
    companion object {
        @JvmStatic
        fun ofDouble(double: Double): Latitude {
            return Latitude(double)
        }

        fun ofLatitude(latitude: Latitude): Latitude {
            return Latitude(latitude.value)
        }

        @JvmField
        val TAG: String = "Latitude"
    }
}