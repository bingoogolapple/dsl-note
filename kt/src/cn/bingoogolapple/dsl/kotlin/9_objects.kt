package cn.bingoogolapple.dsl.kotlin

class Driver

interface OnExternalDriverMountListener {
    fun onMount(driver: Driver)

    fun onUnmount(driver: Driver)
}

abstract class Player

/**
 * object 和 class 很像，只是 object 只有一个实例，可以通过将 kotlin 字节码反编译成 Java 来看
 * 只有一个实例的类，不能自定义构造方法，可以实现接口、继承父类，其实就是一个饿汉式单例
 */
object MusicPlayer : Player(), OnExternalDriverMountListener {
    val state: Int = 0

    fun play(url: String) {
    }

    fun stop() {
    }

    override fun onMount(driver: Driver) {
    }

    override fun onUnmount(driver: Driver) {
    }
}

fun main(args: Array<String>) {
    MusicPlayer.stop()
}