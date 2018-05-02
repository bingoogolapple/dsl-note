package cn.bingoogolapple.dsl.groovy

interface Action {
    // protected void eat() // 接口中不许定义非 public 的方法
    void eat()

    void drink()

    void play()
}

trait DefaultAction {
    abstract void eat()

    void drink() { // 可以有默认的实现
        println "drink"
    }

    void play() {
        println "play"
    }
}

// groovy 中默认都是 public
class Person implements Serializable, DefaultAction {
    String name
    Integer age

    def increaseAge(Integer years) {
        this.age += years
    }

    // 1.类中有对应的方法直接调用类中对应的方法
    // 2.类中没有对应的方法，MetaClass 中有则调用 MetaClass 中的方法
    // 3.类中没有对应的方法，MetaClass 中也没有对应的方法，有重写 methodMissing 方法则调用 methodMissing 方法
    def methodMissing(String name, Object args) {
        return "the method ${name} is missing, the params is ${args}"
    }
    // 4.类中没有对应的方法，MetaClass 中也没有对应的方法，没有有重写 methodMissing 方法，有重写 invokeMethod 方法则调用 invokeMethod 方法
    def invokeMethod(String name, Object args) {
        return "the method is ${name}, the params is ${args}"
    }
    // 5.类中没有对应的方法，MetaClass 中也没有对应的方法，没有有重写 methodMissing 和 invokeMethod 方法，抛 MissingMethodException

    @Override
    void eat() {
        println "eat"
    }
}

def person = new Person()
// 没有对应构造方法时，可以通过名称参数来实例化
person = new Person(name: 'BGA')
person = new Person(age: 26)
person = new Person(name: 'BGA', age: 26)
// 无论是直接用点操作符还是调用 get/set 方法，最终都是调用 get/set 方法
println "the name is $person.name, the age is $person.age"
person.increaseAge(2)
println "the name is ${person.getName()}, the age is ${person.getAge()}"

//ExpandoMetaClass.enableGlobally()

// 为类动态添加属性
Person.metaClass.sex = 'male'
println person.sex // 动态增加属性只对修改 metaClass 后创建的对象生效，修改之前已经创建的对象不会生效
person = new Person(name: 'BGA', age: 26, sex: 'qwer')
println person.sex // qwer
person = new Person(name: 'BGA', age: 26)
println person.sex // male
person.sex = 'female'
println person.sex // female

// 为类动态添加方法
Person.metaClass.testSdf = { param1, param2 ->
    println "param1 $param1, param2 $param2"
    return name.toLowerCase()
}
println person.testSdf('aaaa', 'bbbb') // 动态增加方法对修改 metaClass 前后创建的对象都生效
person = new Person(name: 'ABC', age: 26)
println person.testSdf('aaaa', 'bbbb')

// 为类动态的添加静态方法
Person.metaClass.static.createPerson = { name, age -> new Person(name: name, age: age, sex: 'female') }
person = Person.createPerson('AAAABBBB', 26)
println "name $person.name, age $person.age, sex $person.sex"
