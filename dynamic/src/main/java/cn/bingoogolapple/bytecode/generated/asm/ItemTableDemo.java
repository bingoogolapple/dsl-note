package cn.bingoogolapple.bytecode.generated.asm;

import cn.bingoogolapple.bytecode.JavaItem;

import java.util.ArrayList;

public class ItemTableDemo extends ArrayList<JavaItem> {
    private static final ArrayList<JavaItem> sContent = new ArrayList();

    static {
        sContent.add(new JavaItem(1));
        sContent.add(new JavaItem(2));
    }

    public ItemTableDemo() {
        add(new JavaItem(1));
        add(new JavaItem(2));
        sContent.add(new JavaItem(3));
    }
}