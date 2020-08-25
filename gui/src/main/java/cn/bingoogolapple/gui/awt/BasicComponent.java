package cn.bingoogolapple.gui.awt;

import cn.bingoogolapple.gui.utils.ScreenUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * 演示常用基础组件
 */
public class BasicComponent extends Frame {
    public BasicComponent() {
        setTitle("演示常用基础组件");
        setResizable(false);
        ScreenUtils.showOnScreen(1, this);

        testListener();

        initMenu();
        initTopBox();
        initBottomBox();

        pack(); // 设置 Frame 最佳大小
        setVisible(true);
    }

    private void initMenu() {
        Menu fileMenu = new Menu("文件");

        MenuItem auto = new MenuItem("自动换行");
        MenuItem copy = new MenuItem("复制");
        MenuItem paste = new MenuItem("粘贴");

        MenuItem comment = new MenuItem("注释", new MenuShortcut(KeyEvent.VK_P, true));
        comment.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("点击了「注释」");
            }
        });
        MenuItem cancelComment = new MenuItem("取消注释");

        Menu formatMenu = new Menu("格式");
        formatMenu.add(comment);
        formatMenu.add(cancelComment);

        Menu editMenu = new Menu("编辑");
        editMenu.add(auto);
        editMenu.add(copy);
        editMenu.add(paste);
        editMenu.addSeparator();
        editMenu.add(formatMenu);

        MenuBar menuBar = new MenuBar();
        menuBar.add(fileMenu);
        menuBar.add(editMenu);

        setMenuBar(menuBar);
    }

    private void initTopBox() {
        Box topLeftBox = Box.createVerticalBox();
        final TextArea textArea = new TextArea("请输入简介", 5, 20);
        textArea.addTextListener(new TextListener() {
            @Override
            public void textValueChanged(TextEvent e) {
                System.out.println("简介：" + textArea.getText());
            }
        });

        MenuItem comment = new MenuItem("注释");
        MenuItem cancelComment = new MenuItem("取消注释");
        MenuItem copy = new MenuItem("复制");
        MenuItem save = new MenuItem("保存");
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String actionCommand = e.getActionCommand();
                System.out.println("点击了菜单：" + actionCommand);
            }
        };
        comment.addActionListener(actionListener);
        cancelComment.addActionListener(actionListener);
        copy.addActionListener(actionListener);
        save.addActionListener(actionListener);

        final PopupMenu popupMenu = new PopupMenu();
        popupMenu.add(comment);
        popupMenu.add(cancelComment);
        popupMenu.addSeparator();
        popupMenu.add(copy);
        popupMenu.add(save);

        textArea.add(popupMenu);
        textArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) { // 右键时才触发
                    popupMenu.show(textArea, e.getX(), e.getY());
                }
            }
        });

        Choice colorChoice = new Choice();
        colorChoice.addItem("红色");
        colorChoice.addItem("绿色");
        colorChoice.addItem("蓝色");
        colorChoice.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                System.out.println("Choice ItemListener 选择了：" + e.getItem());
            }
        });

        CheckboxGroup genderCg = new CheckboxGroup();
        Checkbox male = new Checkbox("男", genderCg, false);
        Checkbox female = new Checkbox("女", genderCg, false);
        ItemListener genderListener = new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                System.out.println("性别：" + e.getItem());
            }
        };
        male.addItemListener(genderListener);
        female.addItemListener(genderListener);

        final Checkbox isMarried = new Checkbox("是否已婚？");
        isMarried.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                System.out.println("是否已婚：" + isMarried.getState());
            }
        });

        Box topBottomBox = Box.createHorizontalBox();
        topBottomBox.add(colorChoice);
        topBottomBox.add(male);
        topBottomBox.add(female);
        topBottomBox.add(isMarried);

        topLeftBox.add(textArea);
        topLeftBox.add(topBottomBox);

        List colorList = new List(6, false);
        colorList.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                System.out.println("List ItemListener 选择了：" + e.getItem());
            }
        });
        colorList.add("红色");
        colorList.add("绿色");
        colorList.add("蓝色");
        colorList.add("黄色");
        colorList.add("灰色");

        Box topBox = Box.createHorizontalBox();
        topBox.add(topLeftBox);
        topBox.add(colorList);

        add(topBox); // Frame 默认为 BorderLayout 布局，不写第二个参数默认为 BorderLayout.CENTER
    }

    private void initBottomBox() {
        // 如何设置 placeholder？
        final TextField nameTf = new TextField("请输入名称");
        nameTf.addTextListener(new TextListener() {
            @Override
            public void textValueChanged(TextEvent e) {
                System.out.println("名称：" + e.getSource().equals(nameTf) + " " + nameTf.getText());
            }
        });
        nameTf.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                System.out.println("focusGained：" + e.toString());
            }

            @Override
            public void focusLost(FocusEvent e) {
                System.out.println("focusLost：" + e.toString());
            }
        });

        Button confirmBtn = new Button("确认");
        confirmBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("点解了确认按钮");
            }
        });

        Box bottomBox = Box.createHorizontalBox();
        bottomBox.add(nameTf);
        bottomBox.add(confirmBtn);

        add(bottomBox, BorderLayout.SOUTH);
    }

    private void testListener() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // 点击窗口关闭按钮时关闭应用程序。dispose() 最外层窗口或者 System.exit(0)
                dispose();
                // System.exit(0);
            }

            @Override
            public void windowIconified(WindowEvent e) {
                System.out.println("最小化 windowIconified：" + e.toString());
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
                System.out.println("最小化变为可见 windowDeiconified：" + e.toString());
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
                System.out.println("windowDeactivated：" + e.toString());
            }

            @Override
            public void windowActivated(WindowEvent e) {
                System.out.println("windowActivated：" + e.toString());
            }
        });
        addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                System.out.println("componentResized：" + e.toString());
            }

            @Override
            public void componentMoved(ComponentEvent e) {
                System.out.println("componentMoved：" + e.toString());
            }

            @Override
            public void componentShown(ComponentEvent e) {
                System.out.println("componentShown：" + e.toString());
            }

            @Override
            public void componentHidden(ComponentEvent e) {
                System.out.println("componentHidden：" + e.toString());
            }
        });
        addContainerListener(new ContainerListener() {
            @Override
            public void componentAdded(ContainerEvent e) {
                System.out.println("componentAdded：" + e.toString());
            }

            @Override
            public void componentRemoved(ContainerEvent e) {
                System.out.println("componentRemoved：" + e.toString());
            }
        });
        addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
                System.out.println("windowGainedFocus：" + e.toString());
            }

            @Override
            public void windowLostFocus(WindowEvent e) {
                System.out.println("windowLostFocus：" + e.toString());
            }
        });
        addWindowStateListener(new WindowStateListener() {
            @Override
            public void windowStateChanged(WindowEvent e) {
                System.out.println("windowStateChanged：" + e.toString());
            }
        });
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("mouseClicked：" + e.toString());
            }

            @Override
            public void mousePressed(MouseEvent e) {
                System.out.println("mousePressed：" + e.toString());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                System.out.println("mouseReleased：" + e.toString());
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                System.out.println("mouseEntered：" + e.toString());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                System.out.println("mouseExited：" + e.toString());
            }
        });
    }

    public static void main(String[] args) {
        new BasicComponent();
        // 正常启动的话不需要加上 while true
    }
}
