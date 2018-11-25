package cn.bingoogolapple.dslapp

import android.util.Log

class KotlinAspectDemo {
    fun kotlinDemo() {
        Log.e("KotlinAspectDemo", "kotlin demo")
        demo1()
        Log.e("KotlinAspectDemo", demo2())
    }

    private fun demo1() {
        Log.e("KotlinAspectDemo", "kotlin demo1")
    }

    private fun demo2(): String {
        Log.e("KotlinAspectDemo", "kotlin demo2")
        return "kotlin demo2 的结果"
    }
}
