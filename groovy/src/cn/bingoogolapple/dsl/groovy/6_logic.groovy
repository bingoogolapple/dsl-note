package cn.bingoogolapple.dsl.groovy

def a = 30
def result
switch (a) {
    case 'b':
        result = 'bingo'
        break
    case 'g':
        result = 'googol'
        break
    case [5.889, 1, 2, 3, 'sdf']:
        result = 'list'
        break
    case 12..30:
        result = 'range'
        break
    case Integer:
        result = 'integer'
        break
    case BigDecimal:
        result = 'big decimal'
        break
    default:
        result = 'default'
}
println result

def range = 0..9
println range.class // class groovy.lang.IntRange

def sum = 0
for (i in 0..9) {
    sum += i
}
println sum


def list = [1, 2, 3, 4, 5, 6, 7, 8, 9]
println list.class // class java.util.ArrayList

sum = 0
for (i in [1, 2, 3, 4, 5, 6, 7, 8, 9]) {
    sum += i
}
println sum

def map = ['b': 1, 'g': 2, 'a': 3]
println map.getClass() // class java.util.LinkedHashMap

for (i in ['b': 1, 'g': 2, 'a': 3]) {
    sum += i.value
}