package cn.bingoogolapple.dsl.kotlin

open class Outter {
    val a: Int = 0

    // 默认是静态内部类，加上 inner 后变非静态内部类
    inner class Inner {
        val a: Int = 5

        fun hello() {
            println(a)
            println(this@Outter.a)
        }
    }
}

interface OnClickListener {
    fun onClick()
}

class View {
    var onClickListener: OnClickListener? = null
}

fun main(args: Array<String>) {
    val inner = Outter().Inner()
    inner.hello()

    val view = View()
    // 匿名内部类，可继承父类、实现多个接口。这里继承 Outter 并实现了 OnClickListener 接口
    view.onClickListener = object : Outter(), OnClickListener {
        override fun onClick() {

        }
    }
}