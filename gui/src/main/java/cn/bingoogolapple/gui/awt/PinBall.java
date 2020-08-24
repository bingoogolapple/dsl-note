package cn.bingoogolapple.gui.awt;

import cn.bingoogolapple.gui.utils.ScreenUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * 演示各种布局
 */
public class PinBall extends Frame {
    // 桌面宽高
    private final int TABLE_WIDTH = 300;
    private final int TABLE_HEIGHT = 400;

    // 球拍宽高和极限坐标
    private final int RACKET_WIDTH = 60;
    private final int RACKET_HEIGHT = 20;
    private final int RACKET_SPEED = 10;
    private final int MIN_RACKET_X = 0;
    private final int MAX_RACKET_X = TABLE_WIDTH - RACKET_WIDTH;

    // 球拍坐标
    private final int RACKET_Y = 340;

    // 小球大小和极限坐标
    private final int BALL_SIZE = 16;
    private final int MAX_BALL_X = TABLE_WIDTH - BALL_SIZE;
    private final int MAX_BALL_Y = RACKET_Y - BALL_SIZE;

    // 小球坐标
    private int ballX = 120;
    private int ballY = 20;

    // 小球在 x 和 y 方向上分别移动的速度
    private int speedX = 5;
    private int speedY = 5;

    // 球拍坐标
    private int racketX = 10;

    // 游戏是否结束
    private boolean isOver = false;

    // 定时器
    private Timer timer;

    private class MyCanvas extends Canvas {
        public MyCanvas() {
            addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    int keyCode = e.getKeyCode();
                    if (keyCode == KeyEvent.VK_LEFT) {
                        if (racketX > MIN_RACKET_X) {
                            racketX -= RACKET_SPEED;
                        }
                        racketX = Math.max(MIN_RACKET_X, racketX);
                    } else if (keyCode == KeyEvent.VK_RIGHT) {
                        if (racketX < MAX_RACKET_X) {
                            racketX += RACKET_SPEED;
                        }
                        racketX = Math.min(MAX_RACKET_X, racketX);
                    }
                }
            });

            timer = new Timer(100, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (ballX <= 0 || ballX >= MAX_BALL_X) {
                        speedX = -speedX;
                    }
                    if (ballY <= 0 ||  ballY >= MAX_BALL_Y && ballX >= racketX && ballX <= racketX + RACKET_WIDTH) {
                        speedY = -speedY;
                    }

                    if (ballY >= MAX_BALL_Y && (ballX < racketX || ballX > racketX + RACKET_WIDTH)) {
                        timer.stop();
                        isOver = true;
                        repaint();
                        return;
                    }

                    ballX += speedX;
                    ballY += speedY;
                    repaint();
                }
            });
            timer.start();
        }

        @Override
        public void paint(Graphics g) {
            if (isOver) {
                // 游戏中
                g.setColor(Color.BLACK);
                g.setFont(new Font("Times", Font.BOLD, 20));
                g.drawString("游戏结束！", 50, 200);
            } else {
                // 绘制小球
                g.setColor(Color.RED);
                g.fillOval(ballX, ballY, BALL_SIZE, BALL_SIZE);

                // 绘制球拍
                g.setColor(Color.PINK);
                g.fillRect(racketX, RACKET_Y, RACKET_WIDTH, RACKET_HEIGHT);
            }
        }
    }

    public PinBall() {
        setTitle("弹球游戏");
        setResizable(false);
        setSize(TABLE_WIDTH, TABLE_HEIGHT);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
        ScreenUtils.showOnScreen(1, this);
        setLayout(new BorderLayout());

        MyCanvas canvas = new MyCanvas();
        add(canvas);

//        pack(); // 设置 Frame 最佳大小
        setVisible(true);
    }

    public static void main(String[] args) {
        new PinBall();
        // 正常启动的话不需要加上 while true
    }
}
