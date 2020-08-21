package cn.bingoogolapple.gui.swing;

import cn.bingoogolapple.gui.utils.ScreenUtils;

import javax.swing.*;
import java.awt.*;

/**
 * 演示 JFrame 使用方式
 */
public class DemoOne extends JFrame {
    public DemoOne() {
        setTitle("窗体标题"); // 设置窗体标题
        setResizable(false); // 设置窗体大小是否可以改变
        /**
         * EXIT_ON_CLOSE：隐藏窗体，并停止程序
         * HIDE_ON_CLOSE：隐藏窗体，但不停止程序【默认值】
         * DO_NOTHING_ON_CLOSE：无任何操作
         * DISPOSE_ON_CLOSE：释放窗体资源，如果没有其他窗体存在则停止程序
         */
        setDefaultCloseOperation(EXIT_ON_CLOSE);

//        setLocation(200, 100); // 设置窗体位置，单位像素
        setSize(300, 200); // 设置窗体大小，单位为像素
//        setBounds(200, 100, 300, 200); // 同时设置窗体位置和大小
        ScreenUtils.showOnScreen(1, this);

        Container container = getContentPane();
        container.setBackground(Color.PINK);

        JLabel label = new JLabel("我是文本组件");
        container.add(label); // 添加组件
//        container.remove(label); // 移除组件
        container.validate(); // 验证容器中的组件
//        setContentPane(container); // 重新载入容器，不推荐
        System.out.println("x=" + getX() + ", y=" + getY() + ", width=" + getWidth() + ", height=" + getHeight());

        setVisible(true); // 设置窗体可见
    }

    public static void main(String[] args) {
        new DemoOne();
        // 正常启动的话不需要加上 while true
    }
}
