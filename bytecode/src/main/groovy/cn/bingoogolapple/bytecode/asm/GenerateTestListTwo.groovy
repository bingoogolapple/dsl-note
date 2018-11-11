package cn.bingoogolapple.bytecode.asm

import org.objectweb.asm.*

import java.lang.reflect.Method

class GenerateTestListTwo implements Opcodes {
    GenerateTestListTwo() {
        // 生成一个类只需要 ClassWriter 组件即可
        ClassWriter cw = new ClassWriter(0)
        /**
         * 通过 visit 方法确定类的头部信息
         * 指定类的版本：V1_8
         * 指定这个类是 public 的：ACC_PUBLIC
         * 类的全限定名称：cn/bingoogolapple/bytecode/generated/asm/TestListTwo
         * 泛型签名：<T:Ljava/lang/Object;>Ljava/lang/Object;
         * 指定类的父类：java/lang/Object
         * 指定这个类实现的接口：["java/io/Serializable"] as String[]
         */
        cw.visit(V1_8, ACC_PUBLIC, "cn/bingoogolapple/bytecode/generated/asm/TestListTwo", "<T:Ljava/lang/Object;>Ljava/lang/Object;Ljava/io/Serializable;", "java/lang/Object", ["java/io/Serializable"] as String[])
        MyClassVisitor cv = new MyClassVisitor(cw)

        // 定义私有静态常量 serialVersionUID，值为 -5766514686676908255L
        cv.visitField(ACC_PRIVATE + ACC_STATIC + ACC_FINAL, "serialVersionUID", "J", null, new Long(-5766514686676908255L)).visitEnd()
        // 定义私有属性 name
        cv.visitField(ACC_PRIVATE, "name", Type.getDescriptor(String.class), null, null).visitEnd()
        // 定义私有属性 value
        cv.visitField(ACC_PRIVATE, "value", "Ljava/lang/Object;", "TT;", null).visitEnd()
        // 定义私有属性 bga
        cv.visitField(ACC_PRIVATE, "bga", "Ljava/lang/String;", null, null).visitEnd()

        cv.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null).visitCode()
        cv.visitMethod(ACC_PUBLIC, "<init>", "(Ljava/lang/String;Ljava/lang/Object;)V", "(Ljava/lang/String;TT;)V", null).visitCode()
        cv.visitMethod(ACC_PUBLIC, "setNameAndValue", "(Ljava/lang/String;Ljava/lang/Object;)V", "(Ljava/lang/String;TT;)V", null).visitCode()
        cv.visitMethod(ACC_PUBLIC, "getName", "()Ljava/lang/String;", null, null).visitCode()
        cv.visitMethod(ACC_PUBLIC, "getValue", "()Ljava/lang/Object;", "()TT;", null).visitCode()

        cv.visitEnd()

        new FileOutputStream(new File(AsmGenerator.PACKAGE_DIR, "TestListTwo.class")).withCloseable {
            // 将 cw 转换成字节数组写到文件里面去
            it.write(cw.toByteArray())
        }

        try {
            MyClassLoader classLoader = new MyClassLoader()
            Class clazz = classLoader.loadClass("cn.bingoogolapple.bytecode.generated.asm.TestListTwo")
//        Class clazz = classLoader.defineClassFromClassFile("cn.bingoogolapple.bytecode.generated.asm.TestListTwo", cw.toByteArray())
            Object obj = clazz.newInstance()
            info obj.toString()

            Method setNameAndValueMethod = clazz.getMethod("setNameAndValue", String.class, Object.class)
            setNameAndValueMethod.invoke(obj, '王浩', "bingoogolapple")
            Method getNameMethod = clazz.getMethod("getName")
            Method getValueMethod = clazz.getMethod("getValue")
            info getNameMethod.invoke(obj)
            info getValueMethod.invoke(obj)
        } catch (Exception e) {
            e.printStackTrace()
        }
    }

    private void info(String msg) {
        println "ByteCode ASM ====> ${msg}"
    }

    private static class MyClassVisitor extends ClassVisitor {

        MyClassVisitor(ClassVisitor classVisitor) {
            super(Opcodes.ASM7, classVisitor)
        }

        @Override
        MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
            MethodVisitor methodVisitor = cv.visitMethod(access, name, descriptor, signature, exceptions)
            if (name == "<init>") {
                if (signature == null) {
                    return new NoParameterMethodVisitor(methodVisitor)
                } else {
                    return new ParameterMethodVisitor(methodVisitor)
                }
            } else if (name == "setNameAndValue") {
                return new SetNameAndValueMethodVisitor(methodVisitor)
            } else if (name == "getName") {
                return new GetNameMethodVisitor(methodVisitor)
            } else if (name == "getValue") {
                return new GetValueMethodVisitor(methodVisitor)
            } else {
                return super.visitMethod(access, name, descriptor, signature, exceptions)
            }
        }
    }

    private static class NoParameterMethodVisitor extends MethodVisitor {

        NoParameterMethodVisitor(MethodVisitor methodVisitor) {
            super(Opcodes.ASM7, methodVisitor)
        }

        @Override
        void visitCode() {
            mv.visitCode()

            Label l0 = new Label()
            mv.visitLabel(l0)
            mv.visitVarInsn(ALOAD, 0)
            mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false)

            Label l1 = new Label()
            mv.visitLabel(l1)
            mv.visitVarInsn(ALOAD, 0)
            mv.visitLdcInsn("123")
            mv.visitFieldInsn(PUTFIELD, "cn/bingoogolapple/bytecode/generated/asm/TestListTwo", "bga", "Ljava/lang/String;")

            Label l2 = new Label()
            mv.visitLabel(l2)
            mv.visitInsn(RETURN)

            Label l3 = new Label()
            mv.visitLabel(l3)
            mv.visitLocalVariable("this", "Lcn/bingoogolapple/bytecode/generated/asm/TestListTwo;", "Lcn/bingoogolapple/bytecode/generated/asm/TestListTwo<TT;>;", l0, l3, 0)
            mv.visitMaxs(2, 1)

            mv.visitEnd()
        }
    }

    private static class ParameterMethodVisitor extends MethodVisitor {

        ParameterMethodVisitor(MethodVisitor methodVisitor) {
            super(Opcodes.ASM7, methodVisitor)
        }

        @Override
        void visitCode() {
            mv.visitCode()

            Label l0 = new Label()
            mv.visitLabel(l0)
            mv.visitLineNumber(14, l0)
            mv.visitVarInsn(ALOAD, 0)
            mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false)

            Label l1 = new Label()
            mv.visitLabel(l1)
            mv.visitVarInsn(ALOAD, 0)
            mv.visitLdcInsn("123")
            mv.visitFieldInsn(PUTFIELD, "cn/bingoogolapple/bytecode/generated/asm/TestListTwo", "bga", "Ljava/lang/String;")

            Label l2 = new Label()
            mv.visitLabel(l2)
            mv.visitVarInsn(ALOAD, 0)
            mv.visitVarInsn(ALOAD, 1)
            mv.visitFieldInsn(PUTFIELD, "cn/bingoogolapple/bytecode/generated/asm/TestListTwo", "name", "Ljava/lang/String;")

            Label l3 = new Label()
            mv.visitLabel(l3)
            mv.visitVarInsn(ALOAD, 0)
            mv.visitVarInsn(ALOAD, 2)
            mv.visitFieldInsn(PUTFIELD, "cn/bingoogolapple/bytecode/generated/asm/TestListTwo", "value", "Ljava/lang/Object;")

            Label l4 = new Label()
            mv.visitLabel(l4)
            mv.visitInsn(RETURN)

            Label l5 = new Label()
            mv.visitLabel(l5)
            mv.visitLocalVariable("this", "Lcn/bingoogolapple/bytecode/generated/asm/TestListTwo;", "Lcn/bingoogolapple/bytecode/generated/asm/TestListTwo<TT;>;", l0, l5, 0)
            mv.visitLocalVariable("name", "Ljava/lang/String;", null, l0, l5, 1)
            mv.visitLocalVariable("value", "Ljava/lang/Object;", "TT;", l0, l5, 2)
            mv.visitMaxs(2, 3)

            mv.visitEnd()
        }
    }

    private static class SetNameAndValueMethodVisitor extends MethodVisitor {

        SetNameAndValueMethodVisitor(MethodVisitor methodVisitor) {
            super(Opcodes.ASM7, methodVisitor)
        }

        @Override
        void visitCode() {
            mv.visitCode()

            Label l0 = new Label()
            mv.visitLabel(l0)
            mv.visitVarInsn(ALOAD, 0)
            mv.visitVarInsn(ALOAD, 1)
            mv.visitFieldInsn(PUTFIELD, "cn/bingoogolapple/bytecode/generated/asm/TestListTwo", "name", "Ljava/lang/String;")

            Label l1 = new Label()
            mv.visitLabel(l1)
            mv.visitVarInsn(ALOAD, 0)
            mv.visitVarInsn(ALOAD, 2)
            mv.visitFieldInsn(PUTFIELD, "cn/bingoogolapple/bytecode/generated/asm/TestListTwo", "value", "Ljava/lang/Object;")

            Label l2 = new Label()
            mv.visitLabel(l2)
            mv.visitInsn(RETURN)

            Label l3 = new Label()
            mv.visitLabel(l3)
            mv.visitLocalVariable("this", "Lcn/bingoogolapple/bytecode/generated/asm/TestListTwo;", "Lcn/bingoogolapple/bytecode/generated/asm/TestListTwo<TT;>;", l0, l3, 0)
            mv.visitLocalVariable("name", "Ljava/lang/String;", null, l0, l3, 1)
            mv.visitLocalVariable("value", "Ljava/lang/Object;", "TT;", l0, l3, 2)
            mv.visitMaxs(2, 3)

            mv.visitEnd()
        }
    }

    private static class GetNameMethodVisitor extends MethodVisitor {

        GetNameMethodVisitor(MethodVisitor methodVisitor) {
            super(Opcodes.ASM7, methodVisitor)
        }

        @Override
        void visitCode() {
            mv.visitCode()

            Label l0 = new Label()
            mv.visitLabel(l0)
            mv.visitVarInsn(ALOAD, 0)
            mv.visitFieldInsn(GETFIELD, "cn/bingoogolapple/bytecode/generated/asm/TestListTwo", "name", "Ljava/lang/String;")

            mv.visitInsn(ARETURN)

            Label l1 = new Label()
            mv.visitLabel(l1)
            mv.visitLocalVariable("this", "Lcn/bingoogolapple/bytecode/generated/asm/TestListTwo;", "Lcn/bingoogolapple/bytecode/generated/asm/TestListTwo<TT;>;", l0, l1, 0)
            mv.visitMaxs(1, 1)

            mv.visitEnd()
        }
    }

    private static class GetValueMethodVisitor extends MethodVisitor {

        GetValueMethodVisitor(MethodVisitor methodVisitor) {
            super(Opcodes.ASM7, methodVisitor)
        }

        @Override
        void visitCode() {
            mv.visitCode()

            Label l0 = new Label()
            mv.visitLabel(l0)
            mv.visitVarInsn(ALOAD, 0)
            mv.visitFieldInsn(GETFIELD, "cn/bingoogolapple/bytecode/generated/asm/TestListTwo", "value", "Ljava/lang/Object;")

            mv.visitInsn(ARETURN)

            Label l1 = new Label()
            mv.visitLabel(l1)
            mv.visitLocalVariable("this", "Lcn/bingoogolapple/bytecode/generated/asm/TestListTwo;", "Lcn/bingoogolapple/bytecode/generated/asm/TestListTwo<TT;>;", l0, l1, 0)
            mv.visitMaxs(1, 1)

            mv.visitEnd()
        }
    }
}
