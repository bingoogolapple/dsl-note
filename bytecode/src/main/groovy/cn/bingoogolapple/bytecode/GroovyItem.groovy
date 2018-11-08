package cn.bingoogolapple.bytecode

class GroovyItem {
    private int mIndex

    GroovyItem(int index) {
        mIndex = index
    }

    @Override
    String toString() {
        return "GroovyItem${mIndex}"
    }
}
