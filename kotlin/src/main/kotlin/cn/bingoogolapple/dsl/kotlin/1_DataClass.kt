package cn.bingoogolapple.dsl.kotlin

/**
 * 默认情况下：
 * 1.实现了 copy、hashCode、toString、equals、getter、setter 等方法
 * 2.自动生成了 componentN 方法
 * 3.没有无参构造方法，并且是 final 的
 *
 * kotlin-noarg 编译期生成无参构造方法，kotlin 中不能直接使用，需要通过反射来使用，但在 Java 中可以直接使用该无参构造方法
 * kotlin-allopen 编译期去掉 final，可以继承
 */
@AllOpen
@NoArg
data class Country(var id: Int, var name: String)

class ComponentX {
    operator fun component1(): String {
        return "您好，我是"
    }

    operator fun component2(): Int {
        return 1
    }

    operator fun component3(): Int {
        return 1
    }

    operator fun component4(): Int {
        return 0
    }
}

annotation class AllOpen
annotation class NoArg