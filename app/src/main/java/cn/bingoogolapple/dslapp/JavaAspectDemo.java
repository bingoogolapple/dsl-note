package cn.bingoogolapple.dslapp;

import android.util.Log;

class JavaAspectDemo {
    public static int normalCount;
    public static int singleCount;

    public static void javaDemo() {
        Log.e("JavaAspectDemo", "java demo");
        demo1();
        Log.e("JavaAspectDemo", demo2());
    }

    private static void demo1() {
        Log.e("JavaAspectDemo", "java demo1");
    }

    private static String demo2() {
        Log.e("JavaAspectDemo", "java demo2");
        return "java demo2 的结果";
    }

    public static void normalClick() {
        normalCount++;
    }

    @SingleClick
    public static void singleClick() {
        singleCount++;
    }
}
