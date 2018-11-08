package cn.bingoogolapple.bytecode

class GroovyMain {
    static final String OUTPUT_PATH = GroovyMain.class.getResource("/").getFile().toString()

    static void main(String[] args) {
        println "输出目录为 ${GroovyMain.OUTPUT_PATH}"
        new JavassistTest()
    }
}