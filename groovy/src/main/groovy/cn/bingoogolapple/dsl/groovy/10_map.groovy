package cn.bingoogolapple.dsl.groovy

def colors = [red: 'ff0000', green: '00ff00', blue: '0000ff']
println colors.getClass() // class java.util.LinkedHashMap
// println colors.class 不能用点操作符获取类型，map 的点操作符是获取对应 key 的值
println colors['red']
println colors.red

colors.yellow = 'ffff00'
println colors
colors.complex = [bingo: 1, googol: 2]
println colors

def students = [
        1: [number: '0001', name: 'Bob',
            score : 55, sex: 'male'],
        2: [number: '0002', name: 'Johnny',
            score : 62, sex: 'female'],
        3: [number: '0003', name: 'Claire',
            score : 73, sex: 'female'],
        4: [number: '0004', name: 'Amy',
            score : 66, sex: 'male']
]
println '\n----------------------------------------------------------'
students.each { student -> println "each entry | key $student.key, value $student.value" }
println '----------------------------------------------------------'
students.each { key, value -> println "each key value | key $key, value $value" }
println '----------------------------------------------------------'
students.eachWithIndex { student, index -> println "eachWithIndex entry | key $student.key, value $student.value, index $index" }
println '----------------------------------------------------------'
students.eachWithIndex { key, value, index -> println "eachWithIndex key value | key $key, value $value, index $index" }
println '----------------------------------------------------------\n'

def entry = students.find { studentEntry -> studentEntry.value.score >= 60 }
println entry.class
println entry
def resultMap = students.findAll { studentEntry -> studentEntry.value.score >= 60 }
println resultMap.getClass()
println resultMap

println students.count { studentEntry -> studentEntry.value.score >= 60 && studentEntry.value.sex == 'male' }

println students.findAll { studentEntry -> studentEntry.value.score >= 60 }.collect { it.value.name }.toListString()

println students.groupBy { studentEntry -> studentEntry.value.score >= 60 ? '及格' : '不及格' }

def sortedMap = students.sort { studentEntry1, studentEntry2 ->
    def score1 = studentEntry1.value.score
    def score2 = studentEntry2.value.score
    return score1 == score2 ? 0 : score1 < score2 ? -1 : 1
} // 返回一个新的 map，老的 map 没变
println sortedMap