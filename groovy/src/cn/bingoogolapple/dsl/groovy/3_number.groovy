package cn.bingoogolapple.dsl.groovy

int a = 10
println a.class // class java.lang.Integer
println 10.class // class java.lang.Integer
float b = 3.14
println b.class // class java.lang.Float
double c = 3.14
println c.class // class java.lang.Double
println 3.14f.class // class java.lang.Float
println 3.14.class // class java.math.BigDecimal
println 3.class // class java.lang.Integer
short d = 1
println d.class // class java.lang.Short
byte e = 1
println e.class // class java.lang.Byte

def f = 11
println f.class // class java.lang.Integer
def g = 3.14f
println g.class // class java.lang.Float
def h = 3.14
println h.class // class java.math.BigDecimal

// 这里的加号是 NumberNumberPlus 的 plus 方法
println 5.889f + 5.889f  // 11.777999877929688 // 最终会转换成 Double 的 add 方法
println 5.889 + 5.889 // 11.778 最终会转换成 BigDecimal 的 add 方法
println 5.889.add(5.889) // 11.778
