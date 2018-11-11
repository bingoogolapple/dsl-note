package cn.bingoogolapple.bytecode.asm

import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

class TestMethodVisitor extends MethodVisitor {

    TestMethodVisitor(MethodVisitor mv) {
        super(Opcodes.ASM6, mv)
    }

    // 在方法体开始时调用
    @Override
    void visitCode() {
        /*
        GETSTATIC java/lang/System.out : Ljava/io/PrintStream;
        LDC "========start========="
        INVOKEVIRTUAL java/io/PrintStream.println (Ljava/lang/String;)V
         */
        mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;")
        // Ldc 表示将 int, float 或 String 型常量值从常量池中推送至栈顶
        mv.visitLdcInsn("========start=========")
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false)
        super.visitCode()
    }

    // 在每个指令被执行时都会调用
    @Override
    void visitInsn(int opcode) {
        if (opcode == Opcodes.RETURN) {
            mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;")
            mv.visitLdcInsn("========end=========")
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false)
        }
        super.visitInsn(opcode)
    }
}