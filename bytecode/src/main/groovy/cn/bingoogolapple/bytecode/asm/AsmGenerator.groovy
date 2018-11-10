package cn.bingoogolapple.bytecode.asm

import cn.bingoogolapple.bytecode.GroovyMain
import cn.bingoogolapple.bytecode.SyntaxChildClass
import javassist.*
import javassist.bytecode.ClassFile
import javassist.bytecode.SignatureAttribute
import org.objectweb.asm.*

class AsmGenerator {
    private ClassPool mClassPool
    private File mPackageDir

    AsmGenerator() {
        mClassPool = ClassPool.getDefault()
        testClassPrinter()

        mkPackageDir()

        testAddMethod()

//        test1()

//        testList()
//        testListOne()
//        testListTwo()
//        testListThree()
//
//        testMapOne()
//        testMapTwo()
//
//        testTable()
    }

    private void testClassPrinter() {
        ClassPrinter printer = new ClassPrinter()
//        new ClassReader("cn.bingoogolapple.bytecode.SyntaxSuperInterface").accept(printer, 0)
//        new ClassReader("cn.bingoogolapple.bytecode.SyntaxChildInterface").accept(printer, 0)
//        new ClassReader("cn.bingoogolapple.bytecode.SyntaxSuperClass").accept(printer, 0)
        new ClassReader("cn.bingoogolapple.bytecode.SyntaxChildClass").accept(printer, 0)
    }

    private void mkPackageDir() {
        mPackageDir = new File("${GroovyMain.OUTPUT_PATH}cn/bingoogolapple/bytecode/generated/asm")
        if (mPackageDir.exists()) {
            mPackageDir.delete()
        }
        mPackageDir.mkdirs()
    }

    private void testAddMethod() {
        ClassReader cr = new ClassReader(SyntaxChildClass.class.getName())
        ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS)
        cr.accept(cw, Opcodes.ASM5)

        MethodVisitor mv = cw.visitMethod(Opcodes.ACC_PUBLIC, "newMethod", "(Ljava/lang/Integer;)V", null, null)
        mv.visitInsn(Opcodes.RETURN)
        mv.visitEnd()

        new FileOutputStream(new File(mPackageDir, "SyntaxChildClassNew.class")).withCloseable {
            // 将 cw 转换成字节数组写到文件里面去
            it.write(cw.toByteArray())
        }
    }

    private void test() {
        // 生成一个类只需要 ClassWriter 组件即可
        ClassWriter cw = new ClassWriter(0)
        // 通过 visit 方法确定类的头部信息
        cw.visit(Opcodes.V1_7, Opcodes.ACC_PUBLIC, "cn/bingoogolapple/bytecode/generated/asm/TestList", "<T:Ljava/lang/Object;>Ljava/lang/Object;", null, null)
        // 自定义静态属性
        cw.visitField(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC + Opcodes.ACC_FINAL, "LESS", 'I', null, new Integer(-1)).visitEnd()
        // 定义属性
        cw.visitField(Opcodes.ACC_PRIVATE, 'value', 'T', 'TT;', null).visitEnd()
        // 定义方法
        MethodVisitor mv
        mv = cw.visitMethod(Opcodes.ACC_PUBLIC, '<init>', '(LT;)V', null, null)
        mv.visitEnd()
        cw.visitMethod(Opcodes.ACC_PUBLIC, 'setValue', '(LT;Ljava/lang/Object;)V', null, null).visitEnd()

        mv = cw.visitMethod(Opcodes.ACC_PUBLIC, 'getValue', '()LT;', null, null)
        mv.visitCode()
        mv.visitLdcInsn("value")
        mv.visitInsn(Opcodes.ARETURN)
        mv.visitMaxs(1, 1)
        mv.visitEnd()

        cw.visitEnd() // 使 cw 类已经完成

        new FileOutputStream(new File(mPackageDir, "TestList.class")).withCloseable {
            // 将cw转换成字节数组写到文件里面去
            it.write(cw.toByteArray())
        }
    }

    private void testList() {
        CtClass customListCtClass = mClassPool.makeClass("cn.bingoogolapple.bytecode.generated.javassist.TestList")

        def typeParameterArr = [new SignatureAttribute.TypeParameter("T")] as SignatureAttribute.TypeParameter[]
        SignatureAttribute.ClassSignature cs = new SignatureAttribute.ClassSignature(typeParameterArr)
        info "类签名为 ${cs.encode()}" // <T:Ljava/lang/Object;>Ljava/lang/Object;
        customListCtClass.setGenericSignature(cs.encode())

        CtClass objectClass = mClassPool.get(CtClass.javaLangObject)
        CtField f = new CtField(objectClass, "value", customListCtClass)
        SignatureAttribute.TypeVariable tvar = new SignatureAttribute.TypeVariable("T")
        info "属性签名为 ${tvar.encode()}" // TT;
        f.setGenericSignature(tvar.encode())
        customListCtClass.addField(f)

        CtConstructor constructor = CtNewConstructor.make("public TestList(Object v){value = v;}", customListCtClass)
        SignatureAttribute.MethodSignature constructorMs = new SignatureAttribute.MethodSignature(null, [tvar] as SignatureAttribute.Type[],
                null, null)
        info "constructor 方法签名为 ${constructorMs.encode()}"
        constructor.setGenericSignature(constructorMs.encode())   // (TT;)V;
        customListCtClass.addConstructor(constructor)

        CtMethod m = CtNewMethod.make("public Object get(){return value;}", customListCtClass)
        SignatureAttribute.MethodSignature getMs = new SignatureAttribute.MethodSignature(null, null, tvar, null)
        info "get 方法签名为 ${getMs.encode()}"
        m.setGenericSignature(getMs.encode())     // ()TT;
        customListCtClass.addMethod(m)

        CtMethod m2 = CtNewMethod.make("public void set(Object v){value = v;}", customListCtClass)
        SignatureAttribute.MethodSignature setMs = new SignatureAttribute.MethodSignature(null, [tvar] as SignatureAttribute.Type[],
                new SignatureAttribute.BaseType("void"), null)
        info "set 方法签名为 ${setMs.encode()}"
        m2.setGenericSignature(setMs.encode())   // (TT;)V;
        customListCtClass.addMethod(m2)

        customListCtClass.writeFile(GroovyMain.OUTPUT_PATH)
        // 后面还要用，不能 detach
//        customListCtClass.detach()
        info "生成 TestList"
    }

    private void testListOne() {
        CtClass childCtClass = mClassPool.makeClass("cn.bingoogolapple.bytecode.generated.javassist.TestListOne")

        childCtClass.setSuperclass(mClassPool.getCtClass("cn.bingoogolapple.bytecode.generated.javassist.TestList"))

        childCtClass.writeFile(GroovyMain.OUTPUT_PATH)
        childCtClass.detach()
        info "生成 TestListOne"
    }

    private void testListTwo() {
        CtClass childCtClass = mClassPool.makeClass("cn.bingoogolapple.bytecode.generated.javassist.TestListTwo")

        childCtClass.setSuperclass(mClassPool.getCtClass("cn.bingoogolapple.bytecode.generated.javassist.TestList"))
        childCtClass.setGenericSignature(getListSignature())

        childCtClass.writeFile(GroovyMain.OUTPUT_PATH)
        childCtClass.detach()
        info "生成 TestListTwo"
    }

    private void testListThree() {
        CtClass childCtClass = mClassPool.makeClass("cn.bingoogolapple.bytecode.generated.javassist.TestListThree")

        childCtClass.setSuperclass(mClassPool.getCtClass("cn.bingoogolapple.bytecode.generated.javassist.TestList"))

        ClassFile childClassFile = childCtClass.classFile
        SignatureAttribute signatureAttribute = new SignatureAttribute(childClassFile.getConstPool(), getListSignature())
        childClassFile.addAttribute(signatureAttribute)

        childCtClass.writeFile(GroovyMain.OUTPUT_PATH)
        childCtClass.detach()
        info "生成 TestListThree"
    }

    private String getListSignature() {
        return "Lcn/bingoogolapple/bytecode/generated/javassist/TestList<Ljava/awt/Point;>;"
    }

    private void testMapOne() {
        CtClass childCtClass = mClassPool.makeClass("cn.bingoogolapple.bytecode.generated.javassist.TestMapOne")

        childCtClass.setSuperclass(mClassPool.getCtClass("java.util.HashMap"))
        childCtClass.setGenericSignature(getMapSignature())

        childCtClass.writeFile(GroovyMain.OUTPUT_PATH)
        childCtClass.detach()
        info "生成 TestMapOne"
    }

    private void testMapTwo() {
        CtClass childCtClass = mClassPool.makeClass("cn.bingoogolapple.bytecode.generated.javassist.TestMapTwo")

        childCtClass.setSuperclass(mClassPool.getCtClass("java.util.HashMap"))

        ClassFile childClassFile = childCtClass.classFile
        SignatureAttribute signatureAttribute = new SignatureAttribute(childClassFile.getConstPool(), getMapSignature())
        childClassFile.addAttribute(signatureAttribute)

        childCtClass.writeFile(GroovyMain.OUTPUT_PATH)
        childCtClass.detach()
        info "生成 TestMapTwo"
    }

    private String getMapSignature() {
        return "Ljava/util/HashMap<Ljava/lang/String;Ljava/awt/Point;>;"
    }

    private void testTable() {
        // 类
        CtClass itemTableCtClass = mClassPool.makeClass("cn.bingoogolapple.bytecode.generated.javassist.ItemTable")
        itemTableCtClass.setSuperclass(mClassPool.getCtClass("java.util.ArrayList"))
        itemTableCtClass.setGenericSignature("Ljava/util/ArrayList<Lcn/bingoogolapple/bytecode/KotlinItem;>;")
        // 静态属性
        CtField contentCtField = new CtField(mClassPool.get("java.util.ArrayList"), "sContent", itemTableCtClass)
        contentCtField.setGenericSignature("Ljava/util/ArrayList<Lcn/bingoogolapple/bytecode/KotlinItem;>;")
        contentCtField.setModifiers(Modifier.PRIVATE | Modifier.STATIC)
        itemTableCtClass.addField(contentCtField, CtField.Initializer.byNew(mClassPool.get("java.util.ArrayList")))
        // 静态代码块
        itemTableCtClass.makeClassInitializer().insertAfter("sContent.add(new cn.bingoogolapple.bytecode.KotlinItem(1));")
        itemTableCtClass.makeClassInitializer().insertAfter("sContent.add(new cn.bingoogolapple.bytecode.KotlinItem(2));")
        // 构造方法
        CtConstructor ctConstructor = CtNewConstructor.make("public ItemTable(){}", itemTableCtClass)

        // 没有参数时可以不设置签名
//        SignatureAttribute.MethodSignature constructorMs = new SignatureAttribute.MethodSignature(null, null, null, null)
//        info "constructor 方法签名为 ${constructorMs.encode()}"
//        ctConstructor.setGenericSignature(constructorMs.encode())   // ()V;

        // 通过 insertParameter 或 addParameter 添加参数时可以不设置签名
//        ctConstructor.insertParameter(mClassPool.get("java.lang.String"))
//        ctConstructor.addParameter(mClassPool.get("java.lang.Long"))

        itemTableCtClass.addConstructor(ctConstructor)
        ctConstructor.insertAfter("add(new cn.bingoogolapple.bytecode.KotlinItem(1));")
        ctConstructor.insertAfter("add(new cn.bingoogolapple.bytecode.KotlinItem(2));")
        ctConstructor.insertAfter("sContent.add(new cn.bingoogolapple.bytecode.KotlinItem(3));")


        itemTableCtClass.writeFile(GroovyMain.OUTPUT_PATH)
        info "生成 ItemTable"

        ArrayList list = itemTableCtClass.toClass().newInstance()
        info list.toString()
    }

    private void info(String msg) {
        println "ByteCode ====> ${msg}"
    }
}