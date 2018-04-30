package cn.bingoogolapple.dsl.groovy

String a = "B\"G\"A"
println a.class // class java.lang.String

def b = "B'G'A"
println b.class // class java.lang.String

def c = 'B"G"A'
println c.class // class java.lang.String

// 原样输出，开始的'''后面和结束的'''前面巧回车时输出会自动换行，不想自动换行的话加上反斜线
def d = '''
BGA
    BGA
BGA\
'''
println d.class // class java.lang.String
println d

def e = "bingoogolapple$c${(2 + 3) * 5}${"hello".capitalize()}" // 支持任意表达式
println e // bingoogolappleBGA25Hello
println e.class // class org.codehaus.groovy.runtime.GStringImpl  Groovy 中特有的 GString

def f = echo1(e)
println f.class // class java.lang.String
def g = echo2(e)
println g.class // class org.codehaus.groovy.runtime.GStringImpl
def h = echo2(b)
println h.class // class java.lang.String

// static String echo1(def message) { // 不管传入的是 String 还是 GString，返回的始终是 String
// static def echo1(String message) { // 不管传入的是 String 还是 GString，返回的始终是 String
static String echo1(String message) { // 不管传入的是 String 还是 GString，返回的始终是 String
    return message
}

static def echo2(def message) { // 传入什么类型返回的就是什么类型
    return message
}

// 三个单引号时不支持表达式拼接
def i = '''
BGA
    BGA${(11 + 22) / 3}
BGA\
'''
println i.class
println i
// 三个双引号时支持任意表达式拼接
def j = """
BGA
    BGA${(11 + 22) / 3}
BGA\
"""
println j.class // class org.codehaus.groovy.runtime.GStringImpl
println j