package cn.bingoogolapple.gradle.note.extension

class InnerExtension {
    String name
    final Map<String, String> complexParam

    InnerExtension() {
        complexParam = new HashMap()
    }

    void complex(Map<String, String> arg) {
        complexParam.putAll(arg)
    }

    @Override
    String toString() {
        return "${this.class.simpleName} | name is $name | complexParam is $complexParam"
    }
}
