package cn.bingoogolapple.dsl.groovy

def list = [1, 2, 3, 4, 5]
println list.class // class java.util.ArrayList
println list.size()
def array = [1, 2, 3, 4, 5] as int[]
println array.class // class [I
println array.length
int[] array2 = [1, 2, 3, 4, 5]
println array2.class // class [I
println array2.length
println '------------------------'
println list.sum {
    it * 3
} // 每一项乘以 3 的和
list.add(6) // 末尾追加
println list
list.leftShift(7) // 末尾追加
println list
list << 8 // 末尾追加
println list
list.push(9) // 末尾追加
println list
println list.minus([1, 3, 5, 7, 9])
println list - [1, 3, 5, 7, 9]
println list
list.removeElement(1)
println list
list.removeAll { it % 2 == 0 }
println list
list.removeAll([7, 9])
println list
println '------------------------'
def sortList = [2, 8, 4, 6, 7, 9]
//sortList.sort() // 默认是升序
//println sortList
sortList.sort { a, b -> a == b ? 0 : a > b ? -1 : 1 }
println sortList

def sortStringList = ['kotlin', 'java', 'swift', 'python', 'shell', 'groovy']
sortStringList.sort { it -> return it.size() }
println sortStringList

def findList = [-1, 1, -2, 2, -3, 3, -4, 4]
println '------------------------'
println findList[2..5] // [-2, 2, -3, 3]
println findList[2, 5, 3] // [-2, 3, 2]
println findList.find { return it % 2 == 0 }
println findList.findAll { return it % 2 != 0 }
println findList.any { return it % 2 != 0 } // true
println findList.every { return it % 2 == 0 } // false
println findList.min()
println findList.min { return Math.abs(it) }
println findList.max()
println findList.max { return Math.abs(it) }
println findList.count { return it % 2 == 0 }

list.each {
    println "each $it"
}
list.forEach {
    println "forEach $it"
}
list.eachWithIndex { element, index -> println "element $element, index $index" }