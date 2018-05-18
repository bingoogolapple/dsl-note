package cn.bingoogolapple.gradle.note.extension

import org.gradle.api.Action

class OuterExtension {
    String name
    InnerExtension oneInnerExtension
    InnerExtension twoInnerExtension

    OuterExtension() {
        oneInnerExtension = new InnerExtension()
        twoInnerExtension = new InnerExtension()
    }

    void innerOne(Action<InnerExtension> action) {
        action.execute(oneInnerExtension)
    }

    void innerTwo(Closure closure) {
        closure.delegate = twoInnerExtension
        closure.resolveStrategy = Closure.DELEGATE_FIRST
        closure.call()
    }

    @Override
    String toString() {
        return "${this.class.simpleName} | name = $name"
    }
}
