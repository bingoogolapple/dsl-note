package cn.bingoogolapple.bytecode.asm

import cn.bingoogolapple.bytecode.GroovyMain
import cn.bingoogolapple.bytecode.ToModify
import org.objectweb.asm.tree.ClassNode
import org.objectweb.asm.*
import org.objectweb.asm.tree.MethodNode

/**
 * 核心 API：可以对比 XML 中解析的 SAX，不需要把这个类的整个结构读取进来，节约内存，但是编程难度较大。
 *          在采用基于事件的模型时，类是用一系列事件来表示的，每个事件表示类的一个元素，比如它的一个字段、一个方法声明、一条指令，等等。
 *          基于事件的 API 定义了一组可能事件，以及这些事件必须遵循的发生顺序，还提供了一个类分析器，为每个被分析元素生成一个事件，还提供一个类写入器，由这些事件的序列生成经过编译的类。
 * 树 API：对比 XML 解析中的 DOM，需要把整个类的结构读取到内存中，消耗内存多，但是编程较为简单
 *
 * ClassVisitor：主要提供了和类结构同名的一些方法，这些方法可以对相应的类结构进行操作
 *              定义的方法调用是有顺序的，在 ClassVisitor 中定义了调用的顺序和每个方法在可以出现的次数，如下：
 *              visit [ visitSource ] [ visitOuterClass ] ( visitAnnotation | visitAttribute )* (visitInnerClass | visitField | visitMethod )* visitEnd
 *
 *              visitInnerClass、visitField、visitMethod 和 visitEnd 方法允许我们进行添加一个类属性操作
 *              visitInnerClass、visitField、visitMethod：这些方法有可能会被多次调用，因此在这些方法中创建属性时要注意会重复创建
 *              visitEnd：这个方法只有在最后才会被调用且只调用一次，所以在这个方法中添加属性是唯一的，因此一般添加属性选择在这个方法里编码
 *
 * ClassReader：可以读取编译好的二进制 Class 文件。提供你要转变的类的字节数组，它的 accept 方法，接受一个具体的 ClassVisitor，并调用实现中具体的 visit[Xxxxx] 方法
 *
 * ClassWriter：这个类是 ClassVisitor 的一个实现类，这个类中的 toByteArray 方法会将最终修改的字节码以 byte 数组形式返回。
 *              在这个类的构造时可以指定让系统自动为我们计算栈和本地变量表的大小(COMPUTE_MAXS)，也可以指定系统自动为我们计算栈帧的大小(COMPUTE_FRAMES)
 *
 * AnnotationVisitor：这个接口中定义了和 Annotation 结构相对应的方法，这些方法可以操作 Annotation 中的定义
 *                    调用顺序 (visit | visitEnum | visitAnnotation | visitArray)* visitEnd
 *
 * FieldVisitor：定义了和属性结构相对应的方法，这些方法可以操作属性
 *               调用顺序 (visitAnnotation | visitAttribute)* visitEnd
 *
 * MethodVisitor：定义了和方法结构相对应的方法，这些方法可以去操作源方法
 *
 */
class AsmGenerator implements Opcodes {
    public static final File PACKAGE_DIR = new File("${GroovyMain.OUTPUT_PATH}cn/bingoogolapple/bytecode/generated/asm")

    AsmGenerator() {
        if (PACKAGE_DIR.exists()) {
            PACKAGE_DIR.delete()
        }
        PACKAGE_DIR.mkdirs()

//        testClassPrinter()

//        testModifyMethod()

//        new GenerateTestListOne()
//        new GenerateTestListTwo()
        new GenerateItemTable()
    }

    private void testClassPrinter() {
        // 内部名字为：java/lang/Integer
        info "内部名字为：${Type.getInternalName(Integer.class)}"
        // 内部名字为：int
        info "内部名字为：${Type.getInternalName(int.class)}"
        // 类型描述为：Ljava/lang/Integer;
        info "类型描述为：${Type.getDescriptor(Integer.class)}"
        // 类型描述为：I
        info "类型描述为：${Type.getDescriptor(int.class)}"
        // 方法描述为：(I)Ljava/lang/String;
        info "方法描述为：${Type.getMethodDescriptor(String.class.getMethod("substring", int.class))}"


        String className = "cn.bingoogolapple.bytecode.SyntaxSuperInterface"
        className = "cn.bingoogolapple.bytecode.SyntaxChildInterface"
        className = "cn.bingoogolapple.bytecode.SyntaxSuperClass"
        className = "cn.bingoogolapple.bytecode.SyntaxChildClass"
        ClassReader classReader = new ClassReader(className)
        // 核心 API
        // 第二个参数忽略调试信息
        classReader.accept(new ClassPrinter(), ClassReader.SKIP_DEBUG)

        // 树 API
        ClassNode classNode = new ClassNode(ASM7)
        classReader.accept(classNode, ClassReader.SKIP_DEBUG)
        for (MethodNode methodNode : classNode.methods) {
            info "「access=${methodNode.access}」「name=${methodNode.name}」「desc=${methodNode.desc}」「signature=${methodNode.signature}」「exceptions=${methodNode.exceptions}」"
        }
    }

    private void testModifyMethod() {
        ClassReader cr = new ClassReader(ToModify.class.getName())
        ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS)
        ClassVisitor cv = new TestClassVisitor(cw)
        cr.accept(cv, ASM7)

        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, "newMethod", "(Ljava/lang/Integer;)V", null, null)
        mv.visitInsn(RETURN)
        mv.visitEnd()

        new FileOutputStream(new File(PACKAGE_DIR, "Modified.class")).withCloseable {
            // 将 cw 转换成字节数组写到文件里面去
            it.write(cw.toByteArray())
        }
    }


    private void info(String msg) {
        println "ByteCode ====> ${msg}"
    }
}