package cn.bingoogolapple.dsl.groovy

/**
 * this:代表闭包定义处的类或对象。静态闭包时为定义处的类，非静态时为定义处的类的实例对象
 * owner:代表闭包定义处的类或对象。闭包嵌套闭包时，内层闭包的 owner 和 this 就会不一样，为外层闭包类的实例对象
 * delegate:代表任意对象，默认与 owner 一致。人为修改 delegate 时，delegate 和 owner 时就会不一样
 *
 * owner 和 thisObject 都是构成方法传入的，不能修改
 */

class Tester {
    static def classClosure = {
        println "classClosure this:" + this // cn.bingoogolapple.dsl.groovy.Tester
        println "classClosure owner:" + owner // cn.bingoogolapple.dsl.groovy.Tester
        println "classClosure delegate:" + delegate // cn.bingoogolapple.dsl.groovy.Tester
        println '----------------------------'
        def innerClassClosure = {
            println "innerClassClosure this:" + this // cn.bingoogolapple.dsl.groovy.Tester
            println "innerClassClosure outerClosureClass: " + classClosure.class // cn.bingoogolapple.dsl.groovy.Tester$__clinit__closure4
            println "innerClassClosure owner:" + owner // cn.bingoogolapple.dsl.groovy.Tester$__clinit__closure4@1dde4cb2
            println "innerClassClosure delegate:" + delegate // cn.bingoogolapple.dsl.groovy.Tester$__clinit__closure4@1dde4cb2
            println '--------------------------------------------------------'
        }
        innerClassClosure.call()
    }

    static def classMethod() {
        def classMethodClosure = {
            println "classMethodClosure this:" + this // cn.bingoogolapple.dsl.groovy.Tester
            println "classMethodClosure owner:" + owner // cn.bingoogolapple.dsl.groovy.Tester
            println "classMethodClosure delegate:" + delegate // cn.bingoogolapple.dsl.groovy.Tester
            println '----------------------------'

            def innerClassMethodClosure = {
                println "innerClassMethodClosure this:" + this // cn.bingoogolapple.dsl.groovy.Tester
                println "innerClassMethodClosure owner:" + owner // cn.bingoogolapple.dsl.groovy.Tester$_classMethod_closure2@5119fb47
                println "innerClassMethodClosure delegate:" + delegate // cn.bingoogolapple.dsl.groovy.Tester$_classMethod_closure2@5119fb47
                println '--------------------------------------------------------'
            }
            innerClassMethodClosure.call()
        }
        classMethodClosure.call()
    }

    def objectClosure = {
        println "objectClosure this:" + this // cn.bingoogolapple.dsl.groovy.Tester@1afd44cb
        println "objectClosure owner:" + owner // cn.bingoogolapple.dsl.groovy.Tester@1afd44cb
        println "objectClosure delegate:" + delegate // cn.bingoogolapple.dsl.groovy.Tester@1afd44cb
        println '----------------------------'
        def innerObjectClosure = {
            println "innerObjectClosure this:" + this // cn.bingoogolapple.dsl.groovy.Tester@1afd44cb
            println "innerObjectClosure outerClosureClass: " + objectClosure.class // cn.bingoogolapple.dsl.groovy.Tester$_closure1
            println "innerObjectClosure owner:" + owner // cn.bingoogolapple.dsl.groovy.Tester$_closure1@1ab3a8c8
            println "innerObjectClosure delegate:" + delegate // cn.bingoogolapple.dsl.groovy.Tester$_closure1@1ab3a8c8
            println '--------------------------------------------------------'
        }
        innerObjectClosure.call()
    }

    def objectMethod() {
        def objectMethodClosure = {
            println "objectMethodClosure this:" + this // cn.bingoogolapple.dsl.groovy.Tester@1afd44cb
            println "objectMethodClosure owner:" + owner // cn.bingoogolapple.dsl.groovy.Tester@1afd44cb
            println "objectMethodClosure delegate:" + delegate // cn.bingoogolapple.dsl.groovy.Tester@1afd44cb
            println '----------------------------'
            def innerObjectMethodClosure = {
                println "innerObjectMethodClosure this:" + this // cn.bingoogolapple.dsl.groovy.Tester@1afd44cb
                println "innerObjectMethodClosure owner:" + owner // cn.bingoogolapple.dsl.groovy.Tester$_objectMethod_closure3@479d31f3
                println "innerObjectMethodClosure delegate:" + delegate // cn.bingoogolapple.dsl.groovy.Tester$_objectMethod_closure3@479d31f3
                println '--------------------------------------------------------'
            }
            innerObjectMethodClosure.call()
        }
        objectMethodClosure.call()
    }
}

Tester.classClosure.call()
Tester.classMethod()
def tester = new Tester()
tester.objectClosure.call()
tester.objectMethod()

def scriptOuterClosure = {
    println "scriptOuterClosure this:" + this // cn.bingoogolapple.dsl.groovy.8_closureadvance@64cd705f
    println "scriptOuterClosure owner:" + owner // cn.bingoogolapple.dsl.groovy.8_closureadvance@64cd705f
    println "scriptOuterClosure delegate:" + delegate // cn.bingoogolapple.dsl.groovy.8_closureadvance@64cd705f
    println '----------------------------'
    def scriptInnerClosure = {
        println "scriptInnerClosure this:" + this // cn.bingoogolapple.dsl.groovy.8_closureadvance@64cd705f
        // println "scriptInnerClosure owner:$owner" // 在嵌套的内层 Closure 中，不能通过在字符串中直接使用 $owner 和 $delegate，否则会内存溢出
        println "scriptInnerClosure owner:" + owner // cn.bingoogolapple.dsl.groovy.8_closureadvance$_run_closure2@c267ef4
        println "scriptInnerClosure delegate:" + delegate // cn.bingoogolapple.dsl.groovy.8_closureadvance$_run_closure2@c267ef4
        println '----------------------------'
    }
    def scriptInnerTwoClosure = {
        println "scriptInnerTwoClosure this:" + this // cn.bingoogolapple.dsl.groovy.8_closureadvance@64cd705f
        println "scriptInnerTwoClosure owner:" + owner // cn.bingoogolapple.dsl.groovy.8_closureadvance$_run_closure2@c267ef4
        println "scriptInnerTwoClosure delegate:" + delegate // cn.bingoogolapple.dsl.groovy.Tester@1afd44cb
        println "scriptInnerTwoClosure thisObject:" + thisObject // cn.bingoogolapple.dsl.groovy.Tester@1afd44cb
        println '--------------------------------------------------------'
    }
    scriptInnerClosure.call()
    scriptInnerTwoClosure.delegate = tester // 修改默认的 delegate
    scriptInnerTwoClosure.call()
}
scriptOuterClosure.call()

class Student {
    String name
    def pretty = { "My name is ${name}" }

    String toString() {
        pretty.call()
    }
}

class Teacher {
    String name
}

def student = new Student(name: 'BGA')
def teacher = new Teacher(name: 'bingoogolapple')
println student.toString() // My name is BGA
student.pretty.delegate = teacher
println student.toString() // My name is BGA
// 修改解析策略，默认值为 Closure.OWNER_FIRST。学习委托策略时看源码中这几个常量对应注释中的 demo 更容易理解
student.pretty.resolveStrategy = Closure.DELEGATE_FIRST
println student.toString() // My name is bingoogolapple