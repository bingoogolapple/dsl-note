package cn.bingoogolapple.dsl.kotlin

import sun.text.normalizer.UTF16.append
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

interface Node {
    fun render(): String
}

// Tag.() 表示 block 的参数为 Tag 的实例对象
fun html(block: Tag.() -> Unit): Tag {
//    return Tag("html").apply { block(this) }
    return Tag("html", 0).apply(block)
}

fun Tag.head(block: Head.() -> Unit) {
    this + Head().apply(block)
}

fun Tag.body(block: Body.() -> Unit) {
    this + Body().apply(block)
}

class StringNode(private val content: String) : Node {
    override fun render() = content
}

class Head : Tag("head", 1)

class Body : Tag("body", 1) {
    var id by MapDelegate(properties)

    var `class` by MapDelegate(properties)
}

class MapDelegate(private val map: MutableMap<String, String>) : ReadWriteProperty<Body, String> {
    override fun getValue(thisRef: Body, property: KProperty<*>): String {
        println("getValue: $thisRef -> ${property.name}")
        return thisRef.properties[property.name] ?: ""
//        return map[property.name] ?: ""
    }

    override fun setValue(thisRef: Body, property: KProperty<*>, value: String) {
        println("setValue, $thisRef -> ${property.name} = $value")
        thisRef.properties[property.name] = value
//        map[property.name] = value
    }
}

open class Tag(private val name: String, private val level: Int) : Node {
    private val children = ArrayList<Node>()
    val properties = HashMap<String, String>()

    operator fun String.invoke(value: String) {
        properties[this] = value
    }

    operator fun String.invoke(block: Tag.() -> Unit) {
//        children.add(Tag(this, level + 1).apply(block))
        // 也可以通过 this@Tag 访问外部类
        this@Tag.children.add(Tag(this, level + 1).apply(block))
    }

    operator fun String.unaryPlus() {
        children.add(StringNode(this))
    }

    operator fun plus(node: Node) {
        children.add(node)
    }

    override fun render(): String {
        return StringBuilder()
                .apply {
                    for (i in 0 until level) {
                        append("    ")
                    }
                }
                .append("<$name")
                .apply {
                    if (!properties.isEmpty()) {
                        properties.forEach {
                            append(" ${it.key}=\"${it.value}\"")
                        }
                    }
                }
                .append(">")
                .apply {
                    if (children.size == 1 && children.get(0) is StringNode) {
                        append(children[0].render())
                    } else if (children.isNotEmpty()) {
                        append("\n")
                        children.map(Node::render).map(::append)
                        for (i in 0 until level) {
                            append("    ")
                        }
                    }
                }
                .append("</$name>\n")
                .toString()
    }

}

fun main(args: Array<String>) {
    html {
        head {
            "meta" {
                "charset"("utf-8")
            }
            "title" {
                +"BGA"
            }
        }
        body {
            id = "bodyId"
            `class` = "bodyClass"

            "ul" {
                "li" {
                    "a" {
                        "href"("https://github.com/bingoogolapple")
                        +"GitHub"
                    }
                }
                "li" {
                    "a" {
                        "href"("http://www.bingoogolapple.cn")
                        +"个人博客"
                    }
                }
            }
        }
    }.render().let(::println)
}

