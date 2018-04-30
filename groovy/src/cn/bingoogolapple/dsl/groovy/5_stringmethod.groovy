package cn.bingoogolapple.dsl.groovy

/*
三个来源:
java.lang.String
DefaultGroovyMethods
StringGroovyMethods
 */

// 填充到指定长度，第二个参数为要填充的值，默认填充空格
println '1111'.center(8, '2') // 22111122
println '11111'.center(8, '2') // 21111122
println '1111'.padLeft(8, '2') // 22221111
println '1111'.padRight(8, '2') // 11112222

println '1111'.leftShift('2') // 11112 leftShift 可以用 << 操作符替换
println '1111' << '2' // 11112 // 内部实现 new StringBuffer('1111').append('2')

// 从左边第一个字符开始比较，如果前面的字符都相等则比较长度
println '111'.compareTo('22') // -1 等价于操作符 <=>
println '111' <=> '22' // -1
println '111' > '22' // false
println '3' > '22' // true
println '22' <=> '22' // true

println 'BGA'.charAt(0) // B 等价 [0] 来获取
println 'BGA'[0] // B
println 'BGA'[0, 1] // BG 逗号分隔，获取指定1个或多个位置的值
println 'BGA'[0, 2] // BA 逗号分隔，获取指定1个或多个位置的值
println 'BGA'[1..2] // BGA .. 分隔，获取指定返回的值

println 'bingoogolapple' - 'apple' - 'bingo' // ogol

println 'hello world'.capitalize() // Hello world 句子首字母大写
println 'hello world'.split(' ').collect { it.capitalize() }.join(' ') // Hello World 每个单词首字母大写
println 'HELLO WORLD'.uncapitalize() // hELLO WORLD 句子首字母小写
println 'HELLO WORLD'.split(' ').collect { it.uncapitalize() }.join(' ') // hELLO wORLD 每个单词首字母小写

println 'BGA'.reverse() // AGB

println 'BGA'.asBoolean() // true 对比长度是否大于 0
println 'BGA'.toBoolean() // false 对比是否为字符串 true、y、0
