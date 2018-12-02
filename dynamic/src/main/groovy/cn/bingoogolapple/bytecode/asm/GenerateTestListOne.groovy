package cn.bingoogolapple.bytecode.asm

import org.objectweb.asm.ClassWriter
import org.objectweb.asm.FieldVisitor
import org.objectweb.asm.Label
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.Type

import java.lang.reflect.Method

class GenerateTestListOne implements Opcodes {
    GenerateTestListOne() {
        // 生成一个类只需要 ClassWriter 组件即可
        ClassWriter cw = new ClassWriter(0)
        FieldVisitor fv
        /**
         * 通过 visit 方法确定类的头部信息
         * 指定类的版本：V1_8
         * 指定这个类是 public 的：ACC_PUBLIC
         * 类的全限定名称：cn/bingoogolapple/bytecode/generated/asm/TestListOne
         * 泛型签名：<T:Ljava/lang/Object;>Ljava/lang/Object;
         * 指定类的父类：java/lang/Object
         * 指定这个类实现的接口：["java/io/Serializable"] as String[]
         */
        cw.visit(V1_8, ACC_PUBLIC, "cn/bingoogolapple/bytecode/generated/asm/TestListOne", "<T:Ljava/lang/Object;>Ljava/lang/Object;Ljava/io/Serializable;", "java/lang/Object", ["java/io/Serializable"] as String[])

        /**
         * 第一个参数指定的是这个属性的操作权限
         * 第二个参数是属性名
         * 第三个参数是类型描述
         * 第四个参数是泛型类型
         * 第五个参数是初始化的值。这个参数只有属性为 static 时才有效，对于非 static 属性，它将被忽略
         */
        // 定义私有静态常量 serialVersionUID，值为 -5766514686676908255L
        fv = cw.visitField(ACC_PRIVATE + ACC_STATIC + ACC_FINAL, "serialVersionUID", "J", null, new Long(-5766514686676908255L))
        fv.visitEnd()
        // 定义私有属性 name
        fv = cw.visitField(ACC_PRIVATE, "name", Type.getDescriptor(String.class), null, null)
        fv.visitEnd()
        // 定义私有属性 value
        fv = cw.visitField(ACC_PRIVATE, "value", "Ljava/lang/Object;", "TT;", null)
        fv.visitEnd()
        // 定义私有属性 bga
        fv = cw.visitField(ACC_PRIVATE, "bga", "Ljava/lang/String;", null, null)
        fv.visitEnd()

        // 无参数构造方法
        noParameterConstructor(cw)
        // 有参构造方法
        parameterConstructor(cw)
        // setNameAndValue 方法
        setNameAndValue(cw)
        // getValue 方法
        getValue(cw)
        // getName 方法
        getName(cw)

        cw.visitEnd()

        new FileOutputStream(new File(AsmGenerator.PACKAGE_DIR, "TestListOne.class")).withCloseable {
            // 将 cw 转换成字节数组写到文件里面去
            it.write(cw.toByteArray())
        }

        try {
            MyClassLoader classLoader = new MyClassLoader()
            Class clazz = classLoader.loadClass("cn.bingoogolapple.bytecode.generated.asm.TestListOne")
//            Class clazz = classLoader.defineClassFromClassFile("cn.bingoogolapple.bytecode.generated.asm.TestListOne", cw.toByteArray())
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

    private void noParameterConstructor(ClassWriter cw) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null)
        mv.visitCode()

        Label l0 = new Label()
        mv.visitLabel(l0)
        // 加载隐含的 this 对象，这是每个 Java 方法都有的
        mv.visitVarInsn(ALOAD, 0)
        // 调用父类的构造方法
        mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false)

        Label l1 = new Label()
        mv.visitLabel(l1)
        mv.visitVarInsn(ALOAD, 0)
        // 从常量池中加载 “123” 字符到栈顶
        mv.visitLdcInsn("123")
        // 将栈顶的 "123" 赋值给 bga 属性
        mv.visitFieldInsn(PUTFIELD, "cn/bingoogolapple/bytecode/generated/asm/TestListOne", "bga", "Ljava/lang/String;")

        Label l2 = new Label()
        mv.visitLabel(l2)
        // 设置返回值
        mv.visitInsn(RETURN)

        Label l3 = new Label()
        mv.visitLabel(l3)
        mv.visitLocalVariable("this", "Lcn/bingoogolapple/bytecode/generated/asm/TestListOne;", "Lcn/bingoogolapple/bytecode/generated/asm/TestListOne<TT;>;", l0, l3, 0)
        // 设置方法的栈和本地变量表的大小
        mv.visitMaxs(2, 1)

        mv.visitEnd()
    }

    private void parameterConstructor(ClassWriter cw) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, "<init>", "(Ljava/lang/String;Ljava/lang/Object;)V", "(Ljava/lang/String;TT;)V", null)
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
        mv.visitFieldInsn(PUTFIELD, "cn/bingoogolapple/bytecode/generated/asm/TestListOne", "bga", "Ljava/lang/String;")

        Label l2 = new Label()
        mv.visitLabel(l2)
        mv.visitVarInsn(ALOAD, 0)
        mv.visitVarInsn(ALOAD, 1)
        mv.visitFieldInsn(PUTFIELD, "cn/bingoogolapple/bytecode/generated/asm/TestListOne", "name", "Ljava/lang/String;")

        Label l3 = new Label()
        mv.visitLabel(l3)
        mv.visitVarInsn(ALOAD, 0)
        mv.visitVarInsn(ALOAD, 2)
        mv.visitFieldInsn(PUTFIELD, "cn/bingoogolapple/bytecode/generated/asm/TestListOne", "value", "Ljava/lang/Object;")

        Label l4 = new Label()
        mv.visitLabel(l4)
        mv.visitInsn(RETURN)

        Label l5 = new Label()
        mv.visitLabel(l5)
        mv.visitLocalVariable("this", "Lcn/bingoogolapple/bytecode/generated/asm/TestListOne;", "Lcn/bingoogolapple/bytecode/generated/asm/TestListOne<TT;>;", l0, l5, 0)
        mv.visitLocalVariable("name", "Ljava/lang/String;", null, l0, l5, 1)
        mv.visitLocalVariable("value", "Ljava/lang/Object;", "TT;", l0, l5, 2)
        mv.visitMaxs(2, 3)

        mv.visitEnd()
    }

    private void setNameAndValue(ClassWriter cw) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, "setNameAndValue", "(Ljava/lang/String;Ljava/lang/Object;)V", "(Ljava/lang/String;TT;)V", null)
        mv.visitCode()

        Label l0 = new Label()
        mv.visitLabel(l0)
        mv.visitVarInsn(ALOAD, 0)
        mv.visitVarInsn(ALOAD, 1)
        mv.visitFieldInsn(PUTFIELD, "cn/bingoogolapple/bytecode/generated/asm/TestListOne", "name", "Ljava/lang/String;")

        Label l1 = new Label()
        mv.visitLabel(l1)
        mv.visitVarInsn(ALOAD, 0)
        mv.visitVarInsn(ALOAD, 2)
        mv.visitFieldInsn(PUTFIELD, "cn/bingoogolapple/bytecode/generated/asm/TestListOne", "value", "Ljava/lang/Object;")

        Label l2 = new Label()
        mv.visitLabel(l2)
        mv.visitInsn(RETURN)

        Label l3 = new Label()
        mv.visitLabel(l3)
        mv.visitLocalVariable("this", "Lcn/bingoogolapple/bytecode/generated/asm/TestListOne;", "Lcn/bingoogolapple/bytecode/generated/asm/TestListOne<TT;>;", l0, l3, 0)
        mv.visitLocalVariable("name", "Ljava/lang/String;", null, l0, l3, 1)
        mv.visitLocalVariable("value", "Ljava/lang/Object;", "TT;", l0, l3, 2)
        mv.visitMaxs(2, 3)

        mv.visitEnd()
    }

    private void getValue(ClassWriter cw) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, "getValue", "()Ljava/lang/Object;", "()TT;", null)
        mv.visitCode()

        Label l0 = new Label()
        mv.visitLabel(l0)
        mv.visitVarInsn(ALOAD, 0)
        mv.visitFieldInsn(GETFIELD, "cn/bingoogolapple/bytecode/generated/asm/TestListOne", "value", "Ljava/lang/Object;")

        mv.visitInsn(ARETURN)

        Label l1 = new Label()
        mv.visitLabel(l1)
        mv.visitLocalVariable("this", "Lcn/bingoogolapple/bytecode/generated/asm/TestListOne;", "Lcn/bingoogolapple/bytecode/generated/asm/TestListOne<TT;>;", l0, l1, 0)
        mv.visitMaxs(1, 1)

        mv.visitEnd()
    }

    private void getName(ClassWriter cw) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, "getName", "()Ljava/lang/String;", null, null)
        mv.visitCode()

        Label l0 = new Label()
        mv.visitLabel(l0)
        mv.visitVarInsn(ALOAD, 0)
        // 获取 value 属性的值
        mv.visitFieldInsn(GETFIELD, "cn/bingoogolapple/bytecode/generated/asm/TestListOne", "name", "Ljava/lang/String;")
        // 返回一个引用，这里是 String 的引用即 name
        mv.visitInsn(ARETURN)

        Label l1 = new Label()
        mv.visitLabel(l1)
        mv.visitLocalVariable("this", "Lcn/bingoogolapple/bytecode/generated/asm/TestListOne;", "Lcn/bingoogolapple/bytecode/generated/asm/TestListOne<TT;>;", l0, l1, 0)
        mv.visitMaxs(1, 1)

        mv.visitEnd()
    }

    private void info(String msg) {
        println "ByteCode ASM ====> ${msg}"
    }
}
