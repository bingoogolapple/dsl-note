package cn.bingoogolapple.dsl.groovy

def range = 1..10
println range.class // class groovy.lang.IntRange
println range
println range[0]
println range.from
println range.to
println range.contains(10) // true
range = 1..<10
println range.contains(10) // false
range.each { println it }
for (value in range) {
    println value
}

static def getGrade(def number) {
    def result
    switch (number) {
        case 0..<60:
            result = '不及格'
            break
        case 60..<70:
            result = '及格'
            break
        case 70..<80:
            result = '良好'
            break
        case 80..100:
            result = '优秀'
            break
    }

//    return result
    result // 最后一行可以省略 return 关键字
}

println getGrade(59)
println getGrade(60)
println getGrade(90)