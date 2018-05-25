package cn.bingoogolapple.dsl.groovy

// specify parameters
def cli = new CliBuilder(usage: "groovy ${this.class.getSimpleName()}.groovy [option]")
cli.a(longOpt: 'name', args: 1, '名称')
cli.b(longOpt: 'age', args: 2, '年龄')
cli.c(longOpt: 'test', args: 3, '测试boolean')
cli.h(longOpt: 'help', '显示帮助信息')

// parse and process parameters
def options = cli.parse(args)

println options.c.class // java.lang.String
println options.c as Boolean // 判断的是长度，只要长度大于 0 就为 true
println options.c as boolean // 判断的是长度，只要长度大于 0 就为 true
println Boolean.valueOf(options.c) // 只有 options.c 的值本来就是字符串 true 时结果才会是 true

if (options.h) {
    cli.usage()
} else {
    println "name is ${options.a}"
    println "age is ${options.b}"
}