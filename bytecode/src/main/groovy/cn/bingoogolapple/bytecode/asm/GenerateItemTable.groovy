package cn.bingoogolapple.bytecode.asm


import org.objectweb.asm.*

class GenerateItemTable implements Opcodes {
    GenerateItemTable() {
        // 生成一个类只需要 ClassWriter 组件即可
        ClassWriter cw = new ClassWriter(0)

        cw.visit(V1_8, ACC_PUBLIC, "cn/bingoogolapple/bytecode/generated/asm/ItemTable", "Ljava/util/ArrayList<Lcn/bingoogolapple/bytecode/JavaItem;>;", "java/util/ArrayList", null)

        cw.visitField(ACC_PRIVATE + ACC_STATIC + ACC_FINAL, "sContent", "Ljava/util/ArrayList;", "Ljava/util/ArrayList<Lcn/bingoogolapple/bytecode/JavaItem;>;", null).visitEnd()

        // 无参数构造方法
        addClinitMethod(cw)
        addInitMethod(cw)

        cw.visitEnd()

        new FileOutputStream(new File(AsmGenerator.PACKAGE_DIR, "ItemTable.class")).withCloseable {
            // 将 cw 转换成字节数组写到文件里面去
            it.write(cw.toByteArray())
        }

        try {
            MyClassLoader classLoader = new MyClassLoader()
            Class clazz = classLoader.loadClass("cn.bingoogolapple.bytecode.generated.asm.ItemTable")
//            Class clazz = classLoader.defineClassFromClassFile("cn.bingoogolapple.bytecode.generated.asm.ItemTable", cw.toByteArray())
            ArrayList obj = clazz.newInstance()
            info obj.toString()
        } catch (Exception e) {
            e.printStackTrace()
        }
    }

    private void addClinitMethod(ClassWriter cw) {
        /**
         * 1.clinit 方法与类的实例构造方法init不同，clinit方法不需要显式调用父类构造器，虚拟机会保证子类的 clinit 方法执行之前，父类的 clinit 方法已经执行完毕。故第一个被执行的 clinit 方法的类肯定是 java.lang.Object;
         * 2.clinit 方法不是必需的，对于没有静态块和类变量赋值操作，编译器不会生成 clinit 方法。
         * 3.父类静态语句和静态变量赋值优先于子类.
         * 4.interface 中不能有静态语句块，但仍可以有变量初始化的赋值操作，也可以生成 clinit 方法。但接口和类的不同是，执行接口的 clinit 方法不需要先执行父接口的 clinit 方法。只有当父接口中定义的变量使用时，父接口才会初始化。
         * 5.虚拟机保证类构造方法可以多线程正确执行，会加锁、同步的操作。 一个线程执行类构造方法，其他线程阻塞等待，当类构造方法有耗时操作，会造成多进程的阻塞,往往比较隐蔽。
         */
        MethodVisitor mv = cw.visitMethod(ACC_STATIC, "<clinit>", "()V", null, null)
        mv.visitCode()

        Label l0 = new Label()
        mv.visitLabel(l0)
        // NEW 指令在 Java 堆上为 ArrayList 对象「分配内存空间」，并将对象地址压入操作数栈顶
        mv.visitTypeInsn(NEW, "java/util/ArrayList")
        // DUP 指令复制操作数栈顶值，并将其压入栈顶，也就是说此时操作数栈上有连续相同的两个对象地址
        mv.visitInsn(DUP)
        // INVOKESPECIAL 指令调用实例初始化方法 <init> ()V，注意这个方法是一个实例方法，所以需要从操作数栈顶弹出一个 this 引用（也就是说这一步会弹出一个之前入栈的对象地址）
        mv.visitMethodInsn(INVOKESPECIAL, "java/util/ArrayList", "<init>", "()V", false)
        // PUTSTATIC 指令从操作数栈顶取出一个引用类型的值，并赋值给 sContent
        mv.visitFieldInsn(PUTSTATIC, "cn/bingoogolapple/bytecode/generated/asm/ItemTable", "sContent", "Ljava/util/ArrayList;")

        Label l1 = new Label()
        mv.visitLabel(l1)
        mv.visitFieldInsn(GETSTATIC, "cn/bingoogolapple/bytecode/generated/asm/ItemTable", "sContent", "Ljava/util/ArrayList;")
        // NEW 指令在 Java 堆上为 JavaItem 对象「分配内存空间」，并将对象地址压入操作数栈顶
        mv.visitTypeInsn(NEW, "cn/bingoogolapple/bytecode/JavaItem")
        // DUP 指令复制操作数栈顶值，并将其压入栈顶，也就是说此时操作数栈上有连续相同的两个对象地址
        mv.visitInsn(DUP)
        // 加载常量 ICONST_1 到栈顶（此时操作数栈上有连续相同的两个对象地址 + ICONST_1）
        mv.visitInsn(ICONST_1)
        // INVOKESPECIAL 指令调用实例初始化方法 <init> ()V，注意这个方法是一个实例方法，且接收一个整型参数，所以需要从操作数栈顶弹出整型常量和一个 this 引用（之前入栈的对象地址）
        mv.visitMethodInsn(INVOKESPECIAL, "cn/bingoogolapple/bytecode/JavaItem", "<init>", "(I)V", false)
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/util/ArrayList", "add", "(Ljava/lang/Object;)Z", false)
        // 栈顶数值出栈
        mv.visitInsn(POP)

        Label l2 = new Label()
        mv.visitLabel(l2)
        mv.visitFieldInsn(GETSTATIC, "cn/bingoogolapple/bytecode/generated/asm/ItemTable", "sContent", "Ljava/util/ArrayList;")
        mv.visitTypeInsn(NEW, "cn/bingoogolapple/bytecode/JavaItem")
        mv.visitInsn(DUP)
        mv.visitInsn(ICONST_2)
        mv.visitMethodInsn(INVOKESPECIAL, "cn/bingoogolapple/bytecode/JavaItem", "<init>", "(I)V", false)
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/util/ArrayList", "add", "(Ljava/lang/Object;)Z", false)
        mv.visitInsn(POP)

        Label l3 = new Label()
        mv.visitLabel(l3)
        mv.visitInsn(RETURN)
        mv.visitMaxs(4, 0)
        mv.visitEnd()
    }

    private void addInitMethod(ClassWriter cw) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null)
        mv.visitCode()

        Label l0 = new Label()
        mv.visitLabel(l0)
        mv.visitVarInsn(ALOAD, 0)
        mv.visitMethodInsn(INVOKESPECIAL, "java/util/ArrayList", "<init>", "()V", false)

        Label l1 = new Label()
        mv.visitLabel(l1)
        mv.visitVarInsn(ALOAD, 0)
        mv.visitTypeInsn(NEW, "cn/bingoogolapple/bytecode/JavaItem")
        mv.visitInsn(DUP)
        mv.visitInsn(ICONST_1)
        mv.visitMethodInsn(INVOKESPECIAL, "cn/bingoogolapple/bytecode/JavaItem", "<init>", "(I)V", false)
        mv.visitMethodInsn(INVOKEVIRTUAL, "cn/bingoogolapple/bytecode/generated/asm/ItemTable", "add", "(Ljava/lang/Object;)Z", false)
        mv.visitInsn(POP)

        Label l2 = new Label()
        mv.visitLabel(l2)
        mv.visitVarInsn(ALOAD, 0)
        mv.visitTypeInsn(NEW, "cn/bingoogolapple/bytecode/JavaItem")
        mv.visitInsn(DUP)
        mv.visitInsn(ICONST_2)
        mv.visitMethodInsn(INVOKESPECIAL, "cn/bingoogolapple/bytecode/JavaItem", "<init>", "(I)V", false)
        mv.visitMethodInsn(INVOKEVIRTUAL, "cn/bingoogolapple/bytecode/generated/asm/ItemTable", "add", "(Ljava/lang/Object;)Z", false)
        mv.visitInsn(POP)

        Label l3 = new Label()
        mv.visitLabel(l3)
        mv.visitFieldInsn(GETSTATIC, "cn/bingoogolapple/bytecode/generated/asm/ItemTable", "sContent", "Ljava/util/ArrayList;")
        mv.visitTypeInsn(NEW, "cn/bingoogolapple/bytecode/JavaItem")
        mv.visitInsn(DUP)
        mv.visitInsn(ICONST_3)
        mv.visitMethodInsn(INVOKESPECIAL, "cn/bingoogolapple/bytecode/JavaItem", "<init>", "(I)V", false)
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/util/ArrayList", "add", "(Ljava/lang/Object;)Z", false)
        mv.visitInsn(POP)

        Label l4 = new Label()
        mv.visitLabel(l4)
        mv.visitInsn(RETURN)

        Label l5 = new Label()
        mv.visitLabel(l5)
        mv.visitLocalVariable("this", "Lcn/bingoogolapple/bytecode/generated/asm/ItemTable;", null, l0, l5, 0)
        mv.visitMaxs(4, 1)
        mv.visitEnd()
    }

    private void info(String msg) {
        println "ByteCode ASM ====> ${msg}"
    }
}
