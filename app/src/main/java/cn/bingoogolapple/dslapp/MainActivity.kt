package cn.bingoogolapple.dslapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import cn.bingoogolapple.dsl.moduleone.ModuleOne
import cn.bingoogolapple.dslapp.JavaAspectDemo.normalClick
import cn.bingoogolapple.dslapp.JavaAspectDemo.singleClick
import cn.bingoogolapple.dslapp.R.id.contentTv
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        contentTv.text = ModuleOne.getString()

        javaAspectDemo.setOnClickListener {
            JavaAspectDemo.javaDemo()
        }
        kotlinAspectDemo.setOnClickListener {
            KotlinAspectDemo().kotlinDemo()
        }
        singleClickDemo.setOnClickListener {
            for (i in 1..5) {
                JavaAspectDemo.normalClick()
                JavaAspectDemo.singleClick()
            }
            normalTv.text = "点击次数:${JavaAspectDemo.normalCount}次"
            singleTv.text = "防止多次点击:${JavaAspectDemo.singleCount}次"
        }
    }
}
