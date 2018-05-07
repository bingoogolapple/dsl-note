package cn.bingoogolapple.dsl.groovy

def helloClosure1 = { param ->
    println "Hello Closure $param"
}
println helloClosure1.class // class cn.bingoogolapple.dsl.groovy.7_closure$_run_closure1
helloClosure1.call('bingo')
helloClosure1('googol')
helloClosure1 'apple'

def helloClosure2 = {
    println "Hello Closure $it" // 默认参数名叫 it
}
helloClosure2 'bingo'

def helloClosure3 = { name, age ->
    println "Hello Closure $name $age"
}
def helloClosure4 = { String name, int age ->
    println "Hello Closure $name $age"
}

helloClosure3('BGA', 26)
helloClosure4('BGA', 26)
helloClosure4.call(["wanghao", 26]) // 也可以通过 List 来返回多个参数
helloClosure4(["wanghao", 26])

// 闭包一定是有返回值的
def helloClosure5 = {
    println "Hello Closure5"
}
def helloClosure6 = {
    return "Hello Closure6"
}
def helloClosure7 = {
    "Hello Closure7" // 如果没有写 return，则用最后一行的结果作为闭包的返回值
}
println helloClosure5() // null
println helloClosure6() // Hello Closure6
println helloClosure7() // Hello Closure7

static int factorial1(int number) {
    int result = 1
    1.upto(number, { num -> result *= num })
    return result
}

println '------------------------------------'

println factorial1(3)

static int factorial2(int number) {
    int result = 1
    number.downto(1) { num -> result *= num } // 可以将最后一个闭包参数放到小括号外面
    return result
}

println factorial2(3)

static int sum(int number) {
    int result = 0
    // 0 到 number - 1
    number.times { num ->
        result += num

//        if (num == 1) {
//            setDirective(Closure.DONE)
//        }
    }
    return result
}

println sum(3)

println '------------------------------------'

def test = 'String Closure'
test.each {
    print it.multiply(2)
}
test = '''\
line1
line2
line3\
'''
test.eachLine { line, counter -> // closure.call(new Object[]{line, counter}) 内部是通过对象数组返回参数的
    println "$line | $counter"
}
println test.find { it.isNumber() } // 1
println test.findAll { it.isNumber() }.toListString() // [1, 2, 3]
println test.any { it.isNumber() } // true 字符串中只要有一个数字就是 true
println test.every { it.isNumber() } // false 字符串中所有的都是数字才为 true
println test.collect { it.toUpperCase() }

static def testClosureParams(Closure closure) {
    println "parameterTypes ${closure.parameterTypes}"
    println "maximumNumberOfParameters ${closure.maximumNumberOfParameters}"

    if (closure.getMaximumNumberOfParameters() == 3) {
        closure('bingoogolapple', 26, 'xxxxxx')
    } else if (closure.getMaximumNumberOfParameters() == 2) {
        closure.call('bingoogolapple', 26)
        closure.call(['bingoogolapple', 26])
        closure('bingoogolapple', 26)
        closure(['bingoogolapple', 26])
    } else {
        closure.call('bingoogolapple')
    }
}

testClosureParams { String name, int age, xxx ->
    println "name $name, age $age, xxx $xxx"
}

testClosureParams { name, Integer age ->
    println "name $name, age $age"
}

testClosureParams({ name ->
    println "name $name"
})

def originClosure = { param1, param2, param3 ->
    println "param1 is $param1, param2 is $param2, param3 is $param3"
}
// curry 方法可以重载原闭包，并将一些参数赋值
def ultimateClosure = originClosure.curry(1, 2)
ultimateClosure.call(3)
originClosure.call(3, 2, 1)