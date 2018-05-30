package cn.bingoogolapple.dsl.kotlin

/**
 * 密封类的子类自能定义到密封类的内部或同一个文件中
 *
 * 指令的方式用密封类
 * 状态的方式用枚举
 */
sealed class PlayerCmd {
    class Play(val url: String, val position: Long = 0): PlayerCmd()

    object Pause: PlayerCmd()

    object Resume: PlayerCmd()
}

class Seek(val position: Long): PlayerCmd()

object Stop: PlayerCmd()

enum class PlayerState{
    IDLE, PAUSE, PLAYING
}

fun main(args: Array<String>) {
    val pause = PlayerCmd.Pause
    val stop = Stop
    val seek = Seek(1)
    val play = PlayerCmd.Play("sdf", 1)
    println(PlayerCmd.Pause)
    println(pause)
    println(stop)
    println(Stop)
    println(seek)
    println(play)
}