package cn.bingoogolapple.gui.swing;

import cn.bingoogolapple.gui.utils.ResourceUtils;
import cn.bingoogolapple.gui.utils.ScreenUtils;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.*;

/**
 * 演示常用基础组件
 */
public class BasicComponent extends JFrame {
    public BasicComponent() {
        setTitle("演示常用基础组件");
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        ScreenUtils.showOnScreen(1, this);

        testListener();

        initMenu();
        initTopBox();
        initBottomBox();

        pack(); // 设置 Frame 最佳大小
        setVisible(true);
    }

    private void initMenu() {
        JMenu fileMenu = new JMenu("文件");

        JMenuItem auto = new JMenuItem("自动换行");
        JMenuItem copy = new JMenuItem("复制");
        JMenuItem paste = new JMenuItem("粘贴");

        JMenuItem comment = new JMenuItem("注释", ResourceUtils.getImageIcon("avatar.png"));
        comment.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("点击了「注释」");
            }
        });
        JMenuItem cancelComment = new JMenuItem("取消注释");

        JMenu formatMenu = new JMenu("格式");
        formatMenu.add(comment);
        formatMenu.add(cancelComment);

        JMenu editMenu = new JMenu("编辑");
        editMenu.add(auto);
        editMenu.add(copy);
        editMenu.add(paste);
        editMenu.addSeparator();
        editMenu.add(formatMenu);

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(fileMenu);
        menuBar.add(editMenu);

        setJMenuBar(menuBar);
    }

    private void initTopBox() {
        Box topLeftBox = Box.createVerticalBox();
        final JTextArea textArea = new JTextArea("请输入简介", 5, 20);
        // 抽象类 JTextComponent 的 getDocument 方法
        textArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                // 当 JTextField 内容增加时触发
                Document doc = e.getDocument();
                try {
                    String text = doc.getText(0, doc.getLength());
                    System.out.println("简介 insertUpdate：" + textArea.getText() + " | " + text);
                } catch (BadLocationException ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                // 当 JTextField 内容被删减时触发
                Document doc = e.getDocument();
                try {
                    String text = doc.getText(0, doc.getLength());
                    System.out.println("简介 removeUpdate：" + textArea.getText() + " | " + text);
                } catch (BadLocationException ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // 当 attribute 改变时触发
                Document doc = e.getDocument();
                try {
                    String text = doc.getText(0, doc.getLength());
                    System.out.println("简介 changedUpdate：" + textArea.getText() + " | " + text);
                } catch (BadLocationException ex) {
                    ex.printStackTrace();
                }
            }
        });
//        textArea.addTextListener(new TextListener() {
//            @Override
//            public void textValueChanged(TextEvent e) {
//                System.out.println("简介：" + textArea.getText());
//            }
//        });

//        JMenuItem comment = new JMenuItem("注释");
//        JMenuItem cancelComment = new JMenuItem("取消注释");
//        JMenuItem copy = new JMenuItem("复制");
//        JMenuItem save = new JMenuItem("保存");
//        ActionListener actionListener = new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                String actionCommand = e.getActionCommand();
//                System.out.println("点击了菜单：" + actionCommand);
//            }
//        };
//        comment.addActionListener(actionListener);
//        cancelComment.addActionListener(actionListener);
//        copy.addActionListener(actionListener);
//        save.addActionListener(actionListener);
//
        final JPopupMenu popupMenu = new JPopupMenu();
//        popupMenu.add(comment);
//        popupMenu.add(cancelComment);
//        popupMenu.addSeparator();
//        popupMenu.add(copy);
//        popupMenu.add(save);
//        textArea.add(popupMenu);
//        textArea.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseReleased(MouseEvent e) {
//                if (e.isPopupTrigger()) { // 右键时才触发
//                    popupMenu.show(textArea, e.getX(), e.getY());
//                }
//            }
//        });

        ButtonGroup styleBg = new ButtonGroup();
        JRadioButtonMenuItem metalItem = new JRadioButtonMenuItem("Metal 风格");
        JRadioButtonMenuItem nimbusItem = new JRadioButtonMenuItem("Nimbus 风格", true);
        JRadioButtonMenuItem windowsItem = new JRadioButtonMenuItem("Windows 风格");
        JRadioButtonMenuItem windowsClassicItem = new JRadioButtonMenuItem("Windows Classic 风格");
        JRadioButtonMenuItem motifClassicItem = new JRadioButtonMenuItem("Motif 风格");

        popupMenu.add(metalItem);
        popupMenu.add(nimbusItem);
        popupMenu.add(windowsItem);
        popupMenu.add(windowsClassicItem);
        popupMenu.add(motifClassicItem);

        styleBg.add(metalItem);
        styleBg.add(nimbusItem);
        styleBg.add(windowsItem);
        styleBg.add(windowsClassicItem);
        styleBg.add(motifClassicItem);

        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String actionCommand = e.getActionCommand();
                System.out.println("点击了风格：" + actionCommand);
                try {
                    switch (actionCommand) {
                        case "Metal 风格":
                            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
                            break;
                        case "Nimbus 风格":
                            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
                            break;
                        case "Windows 风格":
                            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
                            break;
                        case "Windows Classic 风格":
                            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");
                            break;
                        case "Motif 风格":
                            UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
                            break;
                    }
                    SwingUtilities.updateComponentTreeUI(BasicComponent.this);
                } catch (Exception exp) {
                    exp.printStackTrace();
                }
            }
        };
        metalItem.addActionListener(actionListener);
        nimbusItem.addActionListener(actionListener);
        windowsItem.addActionListener(actionListener);
        windowsClassicItem.addActionListener(actionListener);
        motifClassicItem.addActionListener(actionListener);

        // 不需要再监听鼠标事件了
        textArea.setComponentPopupMenu(popupMenu);

        JComboBox colorChoice = new JComboBox<String>();
        colorChoice.addItem("红色");
        colorChoice.addItem("绿色");
        colorChoice.addItem("蓝色");
        colorChoice.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                System.out.println("Choice ItemListener 选择了：" + e.getItem());
            }
        });

        ButtonGroup genderCg = new ButtonGroup();
        JRadioButton male = new JRadioButton("男", false);
        JRadioButton female = new JRadioButton("女", false);
        ItemListener genderListener = new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                System.out.println("性别：" + e.getItem());
            }
        };
        male.addItemListener(genderListener);
        female.addItemListener(genderListener);
        genderCg.add(male);
        genderCg.add(female);

        final JCheckBox isMarried = new JCheckBox("是否已婚？");
        isMarried.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                System.out.println("是否已婚：" + isMarried.isSelected());
            }
        });

        Box topBottomBox = Box.createHorizontalBox();
        topBottomBox.add(colorChoice);
        topBottomBox.add(male);
        topBottomBox.add(female);
        topBottomBox.add(isMarried);

        topLeftBox.add(textArea);
        topLeftBox.add(topBottomBox);

        JList colorList = new JList<String>(new String[]{"红色", "绿色", "蓝色", "黄色", "灰色"});
        colorList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                System.out.println("List ItemListener 选择了：" + e.getFirstIndex() + " | " + e.getLastIndex());
            }
        });

        Box topBox = Box.createHorizontalBox();
        topBox.add(topLeftBox);
        topBox.add(colorList);

        add(topBox); // Frame 默认为 BorderLayout 布局，不写第二个参数默认为 BorderLayout.CENTER
    }

    private void initBottomBox() {
        // 如何设置 placeholder？
        final JTextField nameTf = new JTextField("请输入名称", 20);
//        nameTf.addTextListener(new TextListener() {
//            @Override
//            public void textValueChanged(TextEvent e) {
//                System.out.println("名称：" + e.getSource().equals(nameTf) + " " + nameTf.getText());
//            }
//        });
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

        JButton confirmBtn = new JButton("确认", ResourceUtils.getImageIcon("avatar.png"));
        confirmBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("点解了确认按钮");
            }
        });

        Box bottomBox = Box.createHorizontalBox();
        bottomBox.add(nameTf);
        bottomBox.add(confirmBtn);

        bottomBox.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.RED, Color.GREEN, Color.BLUE, Color.GRAY));
        LineBorder lineBorder = new LineBorder(Color.ORANGE, 10, true);
        bottomBox.setBorder(lineBorder);
        bottomBox.setBorder(BorderFactory.createEmptyBorder(10, 5, 20, 10));
        bottomBox.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED, Color.RED, Color.GREEN));
        TitledBorder titledBorder = new TitledBorder(lineBorder, "测试标题", TitledBorder.LEFT, TitledBorder.BOTTOM, new Font("StSong", Font.BOLD, 18), Color.BLUE);
        bottomBox.setBorder(titledBorder);
        bottomBox.setBorder(BorderFactory.createMatteBorder(10, 5, 20, 10, Color.GREEN));
        bottomBox.setBorder(BorderFactory.createCompoundBorder(lineBorder, titledBorder));

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
