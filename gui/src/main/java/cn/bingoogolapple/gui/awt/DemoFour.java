package cn.bingoogolapple.gui.awt;

import cn.bingoogolapple.gui.utils.ScreenUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * 演示各种布局
 */
public class DemoFour extends Frame {
    public DemoFour() {
        setTitle("演示各种布局");
        setResizable(false);
        setSize(300, 300);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
        ScreenUtils.showOnScreen(1, this);
        setLayout(new FlowLayout(FlowLayout.LEFT));

        addDemoBtn();

        setVisible(true);

//        showFlowLayoutDemo();
//        showBorderLayoutDemo();
//        showGridLayoutDemo();
//        showCalculatorDemo();
//        showCardLayoutDemo();
//        showBoxLayoutDemo();
//        showBoxContainerDemo();
//        showAbsoluteLayoutDemo();
    }

    private void addDemoBtn() {
        addButton("演示 FlowLayout", new Runnable() {
            @Override
            public void run() {
                showFlowLayoutDemo();
            }
        });
        addButton("演示 BorderLayout", new Runnable() {
            @Override
            public void run() {
                showBorderLayoutDemo();
            }
        });
        addButton("演示 GridLayout", new Runnable() {
            @Override
            public void run() {
                showGridLayoutDemo();
            }
        });
        addButton("计算器案例", new Runnable() {
            @Override
            public void run() {
                showCalculatorDemo();
            }
        });
        addButton("演示 CardLayout", new Runnable() {
            @Override
            public void run() {
                showCardLayoutDemo();
            }
        });
        addButton("演示 BoxLayout", new Runnable() {
            @Override
            public void run() {
                showBoxLayoutDemo();
            }
        });
        addButton("演示 Box 容器", new Runnable() {
            @Override
            public void run() {
                showBoxContainerDemo();
            }
        });
        addButton("null 绝对布局", new Runnable() {
            @Override
            public void run() {
                showAbsoluteLayoutDemo();
            }
        });
        addButton("FileDialog 打开文件", new Runnable() {
            @Override
            public void run() {
                showOpenFileDialog();
            }
        });
        addButton("FileDialog 保存文件", new Runnable() {
            @Override
            public void run() {
                showSaveFileDialog();
            }
        });
    }

    private void showSaveFileDialog() {
        FileDialog fileDialog = new FileDialog(this, "选择要保存的位置", FileDialog.SAVE);
        fileDialog.setVisible(true); // 代码会阻塞在这里
        String directory = fileDialog.getDirectory();
        String filename = fileDialog.getFile();
        System.out.println("directory：" + directory);
        System.out.println("filename：" + filename);
    }

    private void showOpenFileDialog() {
        FileDialog fileDialog = new FileDialog(this, "选择要打开的文件", FileDialog.LOAD);
        fileDialog.setVisible(true); // 代码会阻塞在这里

        String directory = fileDialog.getDirectory();
        String filename = fileDialog.getFile();
        System.out.println("directory：" + directory);
        System.out.println("filename：" + filename);
    }

    private void showAbsoluteLayoutDemo() {
        showDemoDialog("演示 AbsoluteLayout", new IDemoContent() {
            @Override
            public void addDemoContent(Dialog dialog) {
                final Panel absolutePanel = new Panel();
                absolutePanel.setBackground(Color.red);
                absolutePanel.setLayout(null); // 设置布局管理器为 null 时就是表示绝对布局，设置为绝对布局后必须要设置宽高才能展示出来，只设置 Location 也是不会展示的
                Button button = new Button("测试绝对布局");

                // 左上角为坐标的起点
                // setLocation、setSize、setBounds 仅在绝对布局时才生效
//                button.setLocation(100, 100);
//                button.setSize(100, 40);
                button.setBounds(100, 50, 100, 40);
                absolutePanel.add(button);

                dialog.add(absolutePanel);
            }
        });
    }

    private void showBoxContainerDemo() {
        showDemoDialog("演示 Box 容器", new IDemoContent() {
            @Override
            public void addDemoContent(Dialog dialog) {
                final Box horizontalBox = Box.createHorizontalBox();
                horizontalBox.add(new Button("水平按钮11"));
                horizontalBox.add(Box.createHorizontalGlue());
                horizontalBox.add(new Button("水平按钮12"));
                horizontalBox.add(Box.createHorizontalStrut(80));
                horizontalBox.add(new Button("水平按钮13"));
                horizontalBox.add(Box.createGlue());  // 该分隔在水平和垂直方向上都可以添加分隔
                horizontalBox.add(new Button("水平按钮14"));

                dialog.add(horizontalBox, BorderLayout.NORTH);

                final Box verticalBox = Box.createVerticalBox();
                verticalBox.add(new Button("垂直按钮21"));
                verticalBox.add(Box.createVerticalGlue());
                verticalBox.add(new Button("垂直按钮22"));
                verticalBox.add(Box.createVerticalStrut(80));
                verticalBox.add(new Button("垂直按钮23"));
                verticalBox.add(Box.createGlue()); // 该分隔在水平和垂直方向上都可以添加分隔
                verticalBox.add(new Button("垂直按钮24"));

                dialog.add(verticalBox);
            }
        });
    }

    private void showBoxLayoutDemo() {
        showDemoDialog("演示 BoxLayout", new IDemoContent() {
            @Override
            public void addDemoContent(Dialog dialog) {
                final Panel boxPanel = new Panel();
                // 水平或垂直方向布局，超过一行或一列时不会只动换行，多余的部分控件看不见
                boxPanel.setLayout(new BoxLayout(boxPanel, BoxLayout.X_AXIS));
                for (int i = 1; i < 30; i++) {
                    boxPanel.add(new Button("按钮" + i));
                }
                dialog.add(boxPanel); // Dialog 默认为 BorderLayout，不写方位时默认在 CENTER 区域

                Panel controlPanel = new Panel();
                String[] actions = new String[]{"水平", "垂直"};
                ActionListener actionListener = new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String command = e.getActionCommand();
                        System.out.println(command);
                        switch (command) {
                            case "水平":
                                boxPanel.setLayout(new BoxLayout(boxPanel, BoxLayout.X_AXIS));
                                /**
                                 * 验证容器意味着布置其子组件。如果这个 Container 无效，此方法调用 validateTree 方法，并标记为 Container 为有效。否则，不执行任何操作。
                                 *
                                 * 与布局相关的更改，例如设置组件的边界，或者向容器添加组件，会自动使容器无效。
                                 * 请注意，容器的祖先也可能无效（详见 Component.invalidate()）。因此，要恢复层次结构的有效性，应在 validate() 结构的最上层无效容器上调用 validate() 方法。
                                 *
                                 * 验证容器可能是相当耗时的操作。出于性能原因，开发人员尽可能推迟层次结构的验证，直到一组布局相关的操作完成，例如在将所有子项添加到容器之后。
                                 */
                                boxPanel.validate();
                                break;
                            case "垂直":
                                boxPanel.setLayout(new BoxLayout(boxPanel, BoxLayout.Y_AXIS));
                                boxPanel.validate();
                                break;
                            default:
                                break;
                        }
                    }
                };
                Button actionButton;
                for (String action : actions) {
                    actionButton = new Button(action);
                    actionButton.addActionListener(actionListener);
                    controlPanel.add(actionButton); // Panel 默认为 FlowLayout，水平居中
                }

                dialog.add(controlPanel, BorderLayout.SOUTH);
            }
        });
    }

    private void showCardLayoutDemo() {
        showDemoDialog("演示 CardLayout", new IDemoContent() {
            @Override
            public void addDemoContent(Dialog dialog) {
                final Panel cardPanel = new Panel();
                // 将容器中的每个组件看作一张卡片，一次只能看到一张卡片【当容器第一次显示时，第一个添加到 CardLayout 对象的组件为可见组件】
                final CardLayout cardLayout = new CardLayout(10, 20);
                cardPanel.setLayout(cardLayout);
                for (int i = 1; i < 10; i++) {
                    cardPanel.add("page" + i, new Label("第" + i + "张"));
                }
                dialog.add(cardPanel); // Dialog 默认为 BorderLayout，不写方位时默认在 CENTER 区域

                Panel controlPanel = new Panel();
                String[] actions = new String[]{"上一张", "下一张", "第一张", "最后一张", "第三张"};
                ActionListener actionListener = new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String command = e.getActionCommand(); // 就是按钮文本
                        System.out.println(command);
                        switch (command) {
                            case "上一张":
                                // 如果已经是第一张，再触发一次就展示最后一张
                                cardLayout.previous(cardPanel);
                                break;
                            case "下一张":
                                // 如果已经是最后一张，再触发一次就展示第一张
                                cardLayout.next(cardPanel);
                                break;
                            case "第一张":
                                cardLayout.first(cardPanel);
                                break;
                            case "最后一张":
                                cardLayout.last(cardPanel);
                                break;
                            case "第三张":
                                cardLayout.show(cardPanel, "page3");
                                break;
                            default:
                                break;
                        }
                    }
                };
                Button actionButton;
                for (String action : actions) {
                    actionButton = new Button(action);
                    actionButton.addActionListener(actionListener);
                    controlPanel.add(actionButton); // Panel 默认为 FlowLayout，水平居中
                }

                dialog.add(controlPanel, BorderLayout.SOUTH);
            }
        });
    }

    private void showCalculatorDemo() {
        showDemoDialog("计算器案例", new IDemoContent() {
            @Override
            public void addDemoContent(Dialog dialog) {
                dialog.setLayout(new BorderLayout()); // Dialog 默认就是 BorderLayout

                dialog.add(new TextField(), BorderLayout.NORTH);

                Panel keyboardPanel = new Panel();
                keyboardPanel.setLayout(new GridLayout(3, 5, 5, 5));

                for (int i = 0; i < 10; i++) {
                    keyboardPanel.add(new Button("" + i));
                }
                keyboardPanel.add(new Button("+"));
                keyboardPanel.add(new Button("-"));
                keyboardPanel.add(new Button("*"));
                keyboardPanel.add(new Button("/"));
                keyboardPanel.add(new Button("="));

                dialog.add(keyboardPanel, BorderLayout.CENTER);
            }
        });
    }

    private void showGridLayoutDemo() {
        showDemoDialog("演示 GridLayout", new IDemoContent() {
            @Override
            public void addDemoContent(Dialog dialog) {
                Panel panel = new Panel();
                /**
                 * 从左往右，从上往下添加
                 * rows：设置是多少就是多少，即使没有占满行数，也会预留剩余行空间 && 剩余空间
                 * cols：默认就是设置的值
                 *      总的子控件个数大于 rows * cols 时【cols 会自动增大】以便保证所有子控件被展示，每个子控件大小会被压缩；
                 *      总的子控件个数小于等于 (rows - 1) * cols  时【cols 会自动减小】以便保证 rows 为固定值，每个子控件会被放大
                 */
                panel.setLayout(new GridLayout(3, 3, 10, 20));

                for (int i = 1; i < 13; i++) {
                    panel.add(new Button("按钮" + i));
                }

                dialog.add(panel);
            }
        });
    }

    private void showBorderLayoutDemo() {
        showDemoDialog("演示 BorderLayout", new IDemoContent() {
            @Override
            public void addDemoContent(Dialog dialog) {
                Panel panel = new Panel();
                /**
                 * 1、当改变 BorderLayout 的容器大小时，NORTH、SOUTH、CENTER 水平调整，WEST、EAST、CENTER 垂直调整
                 * 2、如果某个区域没有放置组件，则会被其他区域占用剩余空间
                 */
                panel.setLayout(new BorderLayout(10, 20));

                panel.add(new Button("我是 NORTH 按钮1"), BorderLayout.NORTH);
                panel.add(new Button("我是 NORTH 按钮2"), BorderLayout.NORTH); // 向同一区域添加多个组件时，后放入的会覆盖先放入的组件
                panel.add(new Button("我是 SOUTH 按钮"), BorderLayout.SOUTH);
//                panel.add(new Button("我是 CENTER 按钮"), BorderLayout.CENTER);
                panel.add(new Button("我是 CENTER 按钮")); // 如果没有指定添加到哪个区域中，则默认添加到中间区域中
                panel.add(new Button("我是 WEST 按钮"), BorderLayout.WEST);
                panel.add(new Button("我是 EAST 按钮"), BorderLayout.EAST);

                dialog.add(panel);
            }
        });
    }

    private void showFlowLayoutDemo() {
        showDemoDialog("演示 FlowLayout", new IDemoContent() {
            @Override
            public void addDemoContent(Dialog dialog) {
                Panel panel = new Panel();
                /**
                 * FlowLayout.CENTER：水平居中对齐【默认值】
                 * FlowLayout.LEFT：靠左对齐
                 * FlowLayout.RIGHT：靠右对齐
                 * FlowLayout.LEADING：与容器方向开始边对齐
                 * FlowLayout.TRAILING：与容器方向结束边对齐
                 */
                panel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 20));
                /**
                 * ComponentOrientation.LEFT_TO_RIGHT：左侧为开始边，右侧为结束边【默认值】
                 * ComponentOrientation.RIGHT_TO_LEFT：右侧为开始边，左侧为结束边
                 */
                panel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

                for (int i = 1; i < 100; i++) {
                    panel.add(new Button("按钮" + i));
                }

                dialog.add(panel);
            }
        });
    }

    private void addButton(String label, final Runnable runnable) {
        Button button = new Button(label);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                runnable.run();
            }
        });
        add(button);
    }

    private void showDemoDialog(String title, IDemoContent demoContent) {
        /**
         * 参数1：父窗体
         * 参数2：标题
         * 参数3：是否为模态窗口，阻塞操作父窗体，默认为 false
         */
        final Dialog dialog = new Dialog(this, title, false);
        dialog.setPreferredSize(new Dimension(500, 400));
        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dialog.dispose();
            }
        });

        demoContent.addDemoContent(dialog);

        dialog.pack(); // 设置最佳大小
        dialog.setVisible(true);
    }

    public static void main(String[] args) {
        new DemoFour();
        // 正常启动的话不需要加上 while true
    }

    interface IDemoContent {
        void addDemoContent(Dialog dialog);
    }
}
