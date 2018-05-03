package cn.bingoogolapple.dsl.app

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import cn.bingoogolapple.dsl.moduleone.ModuleOne
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        contentTv.text = ModuleOne.getString()
    }
}
