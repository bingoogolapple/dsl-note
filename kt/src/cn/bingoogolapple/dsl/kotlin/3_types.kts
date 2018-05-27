package cn.bingoogolapple.dsl.kotlin

//class 妹子 constructor(var 性格: String, var 长相: String, var 声音: String) {
//}

// 只有一个构造方法的话可以省略 constructor 关键字
//class 妹子(var 性格: String, var 长相: String, var 声音: String) {
//}

// 没有方法时可以省略括号
//class 妹子(var 性格: String, var 长相: String, var 声音: String)

//class 妹子(var 性格: String, var 长相: String, var 声音: String) {
//    // 构造方法的方法体
//    init {
//        println("new 了一个${this.javaClass.simpleName}, 性格:$性格, 长相:$长相, 声音:$声音")
//    }
//}

// 默认是 final 的不能被继承，需要加上 open 关键字
open class 人(var 性格: String, var 长相: String, var 声音: String) {
    init {
        println("new 了一个${this.javaClass.simpleName}, 性格:$性格, 长相:$长相, 声音:$声音")
    }
}
// 冒号表示继承
class 妹子(性格: String, 长相: String, 声音: String) : 人(性格, 长相, 声音)

class 帅哥(性格: String, 长相: String, 声音: String) : 人(性格, 长相, 声音)


var 我喜欢的妹子: 妹子 = 妹子("温柔", "甜美", 声音 = "动人")
val 我膜拜的帅哥: 帅哥 = 帅哥("彪悍", "帅气", "浑厚")
// 所有的类都继承自 Any
println(我喜欢的妹子 is 人)
println(我膜拜的帅哥 is Any)

open class Parent

class Child : Parent() {
    fun getName(): String {
        return "孩子"
    }
}

var parent1: Parent = Parent()
// as 转换失败会崩溃，as? 转换失败返回 null
val child: Child? = parent1 as? Child
println(child)

val parent2: Parent = Child()
if (parent2 is Child) {
    // 加了 if 判断后不用再强制类型转换
    println(parent2.getName())
}
val string: String? = "Hello"
println(string?.length)
println(string!!.length)
if (string != null) {
    println(string.length)
}

