package cn.bingoogolapple.dsl.groovy

// 弱类型定义方式：用 def 定义变量时可以动态类型转换
def a = 1
println a.class // class java.lang.Integer

a = 3.14
println a.class // class java.math.BigDecimal

a = "BGA"
println a.class // class java.lang.String

// 也可以不用 def，直接给变量赋值
b = 3.14
println b.class // class java.math.BigDecimal

b = "BGA"
println b.class // class java.lang.String

// 强类型定义方式：用具体类型定义变量时，同父类型可以转换，和 Java 一样
int c = 1
println c.class // class java.lang.Integer

c = 3.14
println c // 3
println c.class // class java.lang.Integer 不会被转换成 java.math.BigDecimal

c = "BGA"
println c.class
/*
Caught: org.codehaus.groovy.runtime.typehandling.GroovyCastException: Cannot cast object 'BGA' with class 'java.lang.String' to class 'int'
org.codehaus.groovy.runtime.typehandling.GroovyCastException: Cannot cast object 'BGA' with class 'java.lang.String' to class 'int'
        at study.variable.run(2_variable.groovy:18)
 */
