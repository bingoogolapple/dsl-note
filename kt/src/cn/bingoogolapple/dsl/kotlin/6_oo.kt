package cn.bingoogolapple.dsl.kotlin

class A {
    var a: String = "默认值"
    // 如果不做特殊处理，大括号，方法体都可以不写
        get() {
            println("获取 a 的值")
            return field
        }
        set(value) {
            println("设置 a 的值")
//            a = value // 不能赋值给 a，否则会导致死循环
            field = value
        }
    var b: String? = null
    lateinit var c: String
    val d: String by lazy {
        println("延迟初始化d")
        "d的默认值"
    }
}

class B(var aField: Int, notAField: Int) {
    var anotherField: Int = 3

    init {
        println("notAField is $notAField，但它不是 B 类的属性")
    }
}

fun main(args: Array<String>) {
    val a = A()
    a.a = "修改后的值"
    println(a.a)
    println(a.b)
    a.c = "c的值初始化之前不能使用"
    println(a.c)
    println(a.d)

    println("=========================================")

    val d = B(1, 2)
    println(d.aField)
    println(d.anotherField)
}