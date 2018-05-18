package cn.bingoogolapple.gradle.note.extension

class InnerExtension {
    String name

    @Override
    String toString() {
        return "${this.class.simpleName} | name = $name"
    }
}
