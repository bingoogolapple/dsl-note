package cn.bingoogolapple.gradle.note.extension

import org.gradle.api.Action

class OuterExtension {
    String name
    final InnerExtension oneInnerExtension
    final InnerExtension twoInnerExtension
    final List<String> complexParam

    OuterExtension() {
        oneInnerExtension = new InnerExtension()
        twoInnerExtension = new InnerExtension()
        complexParam = new ArrayList<>()
    }

    void innerOne(Action<InnerExtension> action) {
        action.execute(oneInnerExtension)
    }

    void innerTwo(Closure closure) {
        closure.delegate = twoInnerExtension
        closure.resolveStrategy = Closure.DELEGATE_FIRST
        closure.call()
    }

    void complex(String... args) {
        complexParam.addAll(args)
    }

    @Override
    String toString() {
        return "${this.class.simpleName} | name is $name | complexParam is $complexParam"
    }
}
