package cn.bingoogolapple.bytecode

import cn.bingoogolapple.bytecode.asm.AsmGenerator
import cn.bingoogolapple.bytecode.javassist.JavassistGenerator

class GroovyMain {
    static final String OUTPUT_PATH = GroovyMain.class.getResource("/").getFile().toString()

    static void main(String[] args) {
        println "输出目录为 ${GroovyMain.OUTPUT_PATH}"
        new JavassistGenerator()
        new AsmGenerator()
    }
}