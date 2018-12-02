package cn.bingoogolapple.bytecode.asm

import org.objectweb.asm.AnnotationVisitor
import org.objectweb.asm.Attribute
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.FieldVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.ModuleVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.TypePath

class ClassPrinter extends ClassVisitor {
    ClassPrinter() {
        super(Opcodes.ASM6)
    }

    @Override
    void visitSource(String source, String debug) {
        info "「source=${source}」「debug=${debug}」"
        super.visitSource(source, debug)
    }

    @Override
    ModuleVisitor visitModule(String name, int access, String version) {
        info "「name=${name}」「access=${access}」「version=${version}」"
        return super.visitModule(name, access, version)
    }

    @Override
    void visitAttribute(Attribute attr) {
        info "「attr.type=${attr.type}」"
        super.visitAttribute(attr)
    }

    @Override
    void visitInnerClass(String name, String outerName, String innerName, int access) {
        super.visitInnerClass(name, outerName, innerName, access)
        info "「name=${name}」「outerName=${outerName}」「innerName=${innerName}」「access=${access}」"
    }

    @Override
    void visitOuterClass(String owner, String name, String desc) {
        super.visitOuterClass(owner, name, desc)
        info "「owner=${owner}」「name=${name}」「desc=${desc}」"
    }

    @Override
    AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        info "「desc=${desc}」「visible=${visible}」"
        return super.visitAnnotation(desc, visible)
    }

    @Override
    AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
        info "「typeRef=${typeRef}」「typePath=${typePath}」「desc=${desc}」「visible=${visible}」"
        return super.visitTypeAnnotation(typeRef, typePath, desc, visible)
    }

    @Override
    void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces)
        info "「version=${version}」「access=${access}」「name=${name}」「signature=${signature}」「superName=${superName}」「interfaces=${interfaces}」"
    }

    @Override
    MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        info "「access=${access}」「name=${name}」「desc=${desc}」「signature=${signature}」「exceptions=${exceptions}」"
        return super.visitMethod(access, name, desc, signature, exceptions)
    }

    @Override
    FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
        info "「access=${access}」「name=${name}」「desc=${desc}」「signature=${signature}」「value=${value}」"
        return super.visitField(access, name, desc, signature, value)
    }

    private void info(String msg) {
        println "ByteCode ClassPrinter ====> ${msg}"
    }
}