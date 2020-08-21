package cn.bingoogolapple.gui.swing;

import cn.bingoogolapple.gui.utils.ScreenUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 演示 JFrame 和 JDialog 结合使用方式
 */
public class DemoThree extends JFrame {
    public DemoThree() {
        setTitle("父窗体标题");
        setResizable(false);
        setSize(600, 300);
//        setBounds(50, 50, 600, 300);
        ScreenUtils.showOnScreen(1, this);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        Container container = getContentPane();
        container.setBackground(Color.CYAN);
        container.setLayout(new FlowLayout()); // 设置流布局，否则子控件会父容器

        JButton button = new JButton("打开 JDialog");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showJDialog();
            }
        });
        container.add(button);

        JLabel label = new JLabel("初始文本");
        label.setText("修改后的文本");
        label.setFont(new Font("楷体", Font.BOLD, 15));
        label.setForeground(Color.RED); // 设置前景色，也就是字体颜色
        container.add(label);

        JLabel iconLabel = new JLabel("图片和标签文本可以同时展示");
        String resourceMain = ClassLoader.getSystemResource(".").getPath().replace("classes/java/main/", "resources/main/");

        Icon icon = new ImageIcon(resourceMain + "avatar.png");
        iconLabel.setIcon(icon);
        iconLabel.setSize(20, 20); // 即使设置标签大小也不会改变图片大小，调整窗口大小也不会改变图片大小
        container.add(iconLabel);

        setVisible(true);
    }

    private void showJDialog() {
        JDialog dialog = new JDialog();
        /**
         * 参数1：父窗体
         * 参数2：标题
         * 参数3：是否为模态窗口，阻塞操作父窗体，默认为 false
         */
//        JDialog dialog = new JDialog(this, "我是子窗口标题", true);
        dialog.setTitle("我是子窗口");
        dialog.setModal(true); // 设置为模态窗口，阻塞操作父窗体
        dialog.setBounds(100, 100, 200, 200); // 如果设置成了模态窗口，一定要在 setVisible 之前调用 setBounds，否则 setBounds 无效


        Container container = dialog.getContentPane();
        container.setBackground(Color.CYAN);
        /**
         * 将容器设置为绝对布局。默认值为 JRootPane.RootLayout
         * 使用绝对布局的窗口通常都是固定大小的，组件的位置和形状不会随着窗体的改变而发生变化
         */
        container.setLayout(null);

        JButton btn1 = new JButton("按钮1");
        btn1.setBounds(10, 10, 100, 20);
        JButton btn2 = new JButton("按钮2");
        btn2.setBounds(50, 50, 100, 20);
        container.add(btn1);
        container.add(btn2);
        dialog.setVisible(true);
    }

    public static void main(String[] args) {
        new DemoThree();
        // 正常启动的话不需要加上 while true
    }
}
