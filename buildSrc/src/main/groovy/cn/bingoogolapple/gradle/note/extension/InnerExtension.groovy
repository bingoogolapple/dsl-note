package cn.bingoogolapple.gradle.note.extension

// 嵌套内层扩展
class InnerExtension {
    // 简单类型扩展
    String name
    // 复杂类型扩展
    final Map<String, String> complexParam

    InnerExtension() {
        complexParam = new HashMap()
    }

    // 复杂类型扩展
    void complex(Map<String, String> arg) {
        complexParam.putAll(arg)
    }

    @Override
    String toString() {
        return "${this.class.simpleName} | name is $name | complexParam is $complexParam"
    }
}
