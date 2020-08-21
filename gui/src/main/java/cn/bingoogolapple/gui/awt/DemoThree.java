package cn.bingoogolapple.gui.awt;

import cn.bingoogolapple.gui.utils.ScreenUtils;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * 演示 Frame + ScrollPane 使用方式
 * 1、ScrollPane 是一个带滚动条的可以容纳其他组件的容器，但不能独立存在，它必须内嵌在其他容器中使用，，默认使用 BorderLayout 管理其内部组件布局
 */
public class DemoThree extends Frame {
    public DemoThree() {
        setTitle("窗体标题"); // 设置窗体标题
        setResizable(false); // 设置窗体大小是否可以改变
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

//        setLocation(200, 100); // 设置窗体位置，单位像素
        setSize(300, 200); // 设置窗体大小，单位为像素
        setBounds(200, 100, 300, 200); // 同时设置窗体位置和大小
        ScreenUtils.showOnScreen(1, this);

        // scrollPane 以及其他容器都不能独立存在，必须依附于 window，而 Frame 就是 Window 的子类
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setBackground(Color.PINK);
        Label label = new Label("我是文本组件");
        scrollPane.add(label); // 添加组件
//        scrollPane.remove(label); // 移除组件
//        scrollPane.validate(); // 验证容器中的组件
        Button button = new Button("我是按钮");
        scrollPane.add(button);
        TextField textField = new TextField("我是输入框");
        scrollPane.add(textField);

        add(scrollPane);

        System.out.println("x=" + getX() + ", y=" + getY() + ", width=" + getWidth() + ", height=" + getHeight());

        setVisible(true); // 设置窗体可见
    }

    public static void main(String[] args) {
        new DemoThree();
        // 正常启动的话不需要加上 while true
    }
}
