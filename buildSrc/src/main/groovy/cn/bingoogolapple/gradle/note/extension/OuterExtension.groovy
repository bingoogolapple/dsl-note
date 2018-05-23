package cn.bingoogolapple.gradle.note.extension

import org.gradle.api.Action

// 外层扩展
class OuterExtension {
    // 简单类型扩展
    String name
    // 嵌套扩展
    final InnerExtension oneInnerExtension
    final InnerExtension twoInnerExtension
    // 复杂类型扩展
    final List<String> complexParam

    OuterExtension() {
        oneInnerExtension = new InnerExtension()
        twoInnerExtension = new InnerExtension()
        complexParam = new ArrayList<>()
    }

    // 通过 Action 嵌套扩展
    void innerOne(Action<InnerExtension> action) {
        action.execute(oneInnerExtension)
    }
    
    // 通过闭包嵌套扩展
    void innerTwo(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = InnerExtension) Closure closure) {
        closure.delegate = twoInnerExtension
        closure.resolveStrategy = Closure.DELEGATE_FIRST
        closure.call()
    }

    // 复杂类型扩展
    void complex(String... args) {
        complexParam.addAll(args)
    }

    @Override
    String toString() {
        return "${this.class.simpleName} | name is $name | complexParam is $complexParam"
    }
}
