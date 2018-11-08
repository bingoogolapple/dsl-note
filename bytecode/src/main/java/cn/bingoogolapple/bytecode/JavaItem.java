package cn.bingoogolapple.bytecode;

public class JavaItem {
    private int mIndex;

    public JavaItem(int index) {
        mIndex = index;
    }

    @Override
    public String toString() {
        return "JavaItem" + mIndex;
    }
}
