package cn.bingoogolapple.bytecode.asm

import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

class TestClassVisitor extends ClassVisitor {

    TestClassVisitor(ClassVisitor cv) {
        super(Opcodes.ASM6, cv)
    }

    @Override
    MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor result = super.visitMethod(access, name, desc, signature, exceptions)
        if ('concat' == name) {
            return new TestMethodVisitor(result)
        }
        return result
    }
}

