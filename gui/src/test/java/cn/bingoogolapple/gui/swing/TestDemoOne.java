package cn.bingoogolapple.gui.swing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class TestDemoOne {
    @Test
    public void test() {
        new DemoOne();
        // 单测方式运行的话需要加上 while true，否则会立即停止
        while (true) {
        }
    }

}
