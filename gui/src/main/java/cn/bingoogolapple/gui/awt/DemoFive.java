package cn.bingoogolapple.gui.awt;

import cn.bingoogolapple.gui.utils.ResourceUtils;
import cn.bingoogolapple.gui.utils.ScreenUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

/**
 * 演示绘图
 */
public class DemoFive extends Frame {
    public DemoFive() {
        setTitle("演示绘图");
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

//        showBasicCanvasDemo();
        showBufferedImageDemo();
    }

    private void addDemoBtn() {
        addButton("演示绘图基础", new Runnable() {
            @Override
            public void run() {
                showBasicCanvasDemo();
            }
        });
        addButton("处理位图", new Runnable() {
            @Override
            public void run() {
                showBufferedImageDemo();
            }
        });
    }

    private void showBufferedImageDemo() {
        showDemoDialog("处理位图", new IDemoContent() {
            @Override
            public void addDemoContent(Dialog dialog) {
                final BufferedImageCanvas canvas = new BufferedImageCanvas();
                dialog.add(canvas); // Dialog 默认为 BorderLayout，不写方位时默认在 CENTER 区域
            }
        });
    }

    private void showBasicCanvasDemo() {
        showDemoDialog("演示绘图础", new IDemoContent() {
            @Override
            public void addDemoContent(Dialog dialog) {
                final BasicCanvas basicCanvas = new BasicCanvas();
                basicCanvas.setBackground(Color.CYAN);
                dialog.add(basicCanvas); // Dialog 默认为 BorderLayout，不写方位时默认在 CENTER 区域

                String[] actions = new String[]{"绘制矩形", "绘制椭圆"};
                ActionListener actionListener = new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String command = e.getActionCommand();
                        System.out.println(command);
                        switch (command) {
                            case "绘制矩形":
                                basicCanvas.setShape(0);
                                break;
                            case "绘制椭圆":
                                basicCanvas.setShape(1);
                                break;
                            default:
                                break;
                        }
                    }
                };

                Panel controlPanel = new Panel();
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
        new DemoFive();
        // 正常启动的话不需要加上 while true
    }

    interface IDemoContent {
        void addDemoContent(Dialog dialog);
    }

    private class BasicCanvas extends Canvas {
        private int shape = 0;

        private void setShape(int shape) {
            this.shape = shape;
            repaint();
        }

        @Override
        public void paint(Graphics g) {
//            super.paint(g); // 默认为 g.clearRect(0, 0, width, height)

            if (shape == 0) { // 绘制矩形
                g.setColor(Color.BLACK);
                g.drawRect(100, 50, 200, 100);
            } else if (shape == 1) { // 绘制椭圆
                g.setColor(Color.RED);
                g.drawOval(100, 50, 200, 100);
            }
        }
    }

    private class BufferedImageCanvas extends Canvas {
        private final int AREA_WIDTH = 500;
        private final int AREA_HEIGHT = 400;
        private BufferedImage image = new BufferedImage(AREA_WIDTH, AREA_HEIGHT, BufferedImage.TYPE_INT_RGB);
        private Graphics imageG = image.getGraphics();
        private Color currentColor = Color.BLACK;
        private int preX;
        private int preY;

        private BufferedImageCanvas() {
            initMenuBar();
            initPopupMenu();
            initBufferedImage();
        }

        private void initBufferedImage() {
            imageG.setColor(Color.WHITE);
            imageG.fillRect(0, 0, AREA_WIDTH, AREA_HEIGHT);

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    preX = -1;
                    preY = -1;
                }
            });
            addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    System.out.println("mouseDragged " + e.getX() + ", " + e.getY());
                    if (preX > 0 && preY > 0) {
                        imageG.setColor(currentColor);
                        imageG.drawLine(preX, preY, e.getX(), e.getY());
                    }

                    preX = e.getX();
                    preY = e.getY();

                    repaint();
                }
            });
        }

        private void initPopupMenu() {
            final PopupMenu colorMenu = new PopupMenu();
            MenuItem redItem = new MenuItem("红色");
            MenuItem greenItem = new MenuItem("绿色");
            MenuItem blueItem = new MenuItem("蓝色");
            ActionListener actionListener = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String actionCommand = e.getActionCommand();
                    switch (actionCommand) {
                        case "红色":
                            currentColor = Color.RED;
                            break;
                        case "绿色":
                            currentColor = Color.GREEN;
                            break;
                        case "蓝色":
                            currentColor = Color.BLUE;
                            break;
                    }
                }
            };
            redItem.addActionListener(actionListener);
            greenItem.addActionListener(actionListener);
            blueItem.addActionListener(actionListener);
            colorMenu.add(redItem);
            colorMenu.add(greenItem);
            colorMenu.add(blueItem);

            add(colorMenu);

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
//                    if (e.isPopupTrigger()) {
                    colorMenu.show(BufferedImageCanvas.this, e.getX(), e.getY());
//                    }
                }
            });
        }

        private void initMenuBar() {
            Menu fileMenu = new Menu("文件");
            MenuItem openImage = new MenuItem("打开图片");
            MenuItem saveImage = new MenuItem("保存图片");
            MenuItem openInnerImage = new MenuItem("打开内部文件");
            ActionListener actionListener = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String actionCommand = e.getActionCommand();
                    switch (actionCommand) {
                        case "打开图片":
                            FileDialog openFileDialog = new FileDialog(DemoFive.this, "打开图片", FileDialog.LOAD);
                            openFileDialog.setFilenameFilter(new FilenameFilter() {
                                @Override
                                public boolean accept(File dir, String name) {
                                    return name.endsWith(".png") || name.endsWith(".jpeg") || name.endsWith(".jpg");
                                }
                            });
                            openFileDialog.setVisible(true);

                            String openFileDir = openFileDialog.getDirectory();
                            String openFileName = openFileDialog.getFile();

                            try {
                                image = ImageIO.read(new File(openFileDir, openFileName));
                                imageG = image.getGraphics();
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                            repaint();
                            break;
                        case "保存图片":
                            FileDialog saveFileDialog = new FileDialog(DemoFive.this, "保存图片", FileDialog.SAVE);
                            saveFileDialog.setVisible(true);

                            String saveFileDir = saveFileDialog.getDirectory();
                            String saveFileName = saveFileDialog.getFile();
                            String extension = saveFileName.substring(saveFileName.lastIndexOf('.') + 1);
                            try {
                                ImageIO.write(image, extension, new File(saveFileDir, saveFileName));
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                            break;
                        case "打开内部文件":
                            try {
                                image = ImageIO.read(new File(ResourceUtils.getResourcePath("avatar.png")));
                                imageG = image.getGraphics();
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                            repaint();
                            break;
                    }
                }
            };
            openImage.addActionListener(actionListener);
            saveImage.addActionListener(actionListener);
            openInnerImage.addActionListener(actionListener);
            fileMenu.add(openImage);
            fileMenu.add(saveImage);
            fileMenu.add(openInnerImage);

            MenuBar menuBar = new MenuBar();
            menuBar.add(fileMenu);
            setMenuBar(menuBar);
        }

        @Override
        public void paint(Graphics g) {
            g.drawImage(image, 0, 0, null);
        }
    }
}
