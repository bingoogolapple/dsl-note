package cn.bingoogolapple.gui.swing;

import java.awt.AlphaComposite;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;
import javax.swing.JFrame;

import cn.bingoogolapple.gui.utils.ColorUtils;
import cn.bingoogolapple.gui.utils.ScreenUtils;

/**
 * 演示 Composite 用法
 */
public class DemoComposite extends JFrame {

    public DemoComposite() {
        setTitle("演示 Composite 用法");
        setResizable(false);
        setSize(600, 600);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
        ScreenUtils.showOnScreen(1, this);

        add(new CompositeCanvasTwo());

        setVisible(true);
    }

    private class CompositeCanvasOne extends Canvas {

        @Override
        public void paint(Graphics graphics) {
            setRenderingHint((Graphics2D) graphics);
//            incorrectUseAge((Graphics2D) graphics);
            correctUseAge((Graphics2D) graphics);
        }

        public void incorrectUseAge(Graphics2D g) {
            g.setColor(ColorUtils.toAwtColor(0xFFFFCC44));
            g.fillOval(0, 0, 200, 200);

            g = (Graphics2D) g.create(0, 0, getWidth(), getHeight());
            g.setComposite(AlphaComposite.Xor);

            g.setColor(ColorUtils.toAwtColor(0xFF66AAFF));
            g.fillRect(100, 100, 200, 200);
        }

        public void correctUseAge(Graphics2D graphics) {
            // 要对图片的 Graphics2D 设置 Composite 才会生效
            BufferedImage bufferedImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = (Graphics2D) bufferedImage.getGraphics();
            setRenderingHint(g);

            g.setColor(ColorUtils.toAwtColor(0xFFFFCC44));
            g.fillOval(0, 0, 200, 200);

            g = (Graphics2D) g.create(0, 0, getWidth(), getHeight());
            g.setComposite(AlphaComposite.Xor);

            g.setColor(ColorUtils.toAwtColor(0xFF66AAFF));
            g.fillRect(100, 100, 200, 200);

            graphics.drawImage(bufferedImage, 0, 0, null);
        }
    }

    private class CompositeCanvasTwo extends JComponent {
        private BufferedImage srcImage;
        private BufferedImage dstImage;

        private CompositeCanvasTwo() {
            srcImage = new BufferedImage(300, 300, BufferedImage.TYPE_INT_ARGB);
            dstImage = new BufferedImage(300, 300, BufferedImage.TYPE_INT_ARGB);

            // 创建源图
            createSrcImage();
            // 创建目标图
            createDstImage();
        }

        @Override
        public void paint(Graphics graphics) {
//            paint1(graphics);
//            paint2(graphics);
            paint3((Graphics2D) graphics);
        }

        public void paint3(Graphics2D g2d) {
//            // 1、先将目标图绘制到控件的 Graphics 上
//            g2d.drawImage(dstImage, 0, 0, null);
//            g2d = (Graphics2D) g2d.create();
//            // 2、再设置控件的 Graphics 的 Composite
//            g2d.setComposite(AlphaComposite.SrcAtop);
//            // 3、最后将源图绘制到控件的 Graphics 上
//            g2d.drawImage(srcImage, 0, 0, null);
//            // 恢复
//            g2d.setComposite(AlphaComposite.SrcOver);

            g2d.setColor(ColorUtils.toAwtColor(0x3300FFFF));
            g2d.fillRect(450, 150, 100, 100);
            g2d.fillOval(450, 150, 100, 100);
//            g2d.fillOval(450, 150, 100, 100);
//            g2d.fillOval(450, 150, 100, 100);
        }

        public void paint2(Graphics graphics) {
            BufferedImage image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = (Graphics2D)image.getGraphics();

            setRenderingHint(g2d);
//            drawBackground(g2d);

            // 1、先将目标图绘制到控件的 Graphics 上
            g2d.drawImage(dstImage, 0, 0, null);
            // 2、再设置控件的 Graphics 的 Composite
//            g2d = (Graphics2D) g2d.create();
            g2d.setComposite(AlphaComposite.SrcAtop);
            // 3、最后将源图绘制到控件的 Graphics 上
            g2d.drawImage(srcImage, 0, 0, null);

            // 恢复
            g2d.setComposite(AlphaComposite.SrcOver);

            // 1、绘制 src 圆
            g2d.setColor(ColorUtils.getDstColor());
            g2d.fillOval(400, 100, 100, 100);
            // 2、设置 Composite
            g2d = (Graphics2D) g2d.create();
            g2d.setComposite(AlphaComposite.SrcAtop);
//            g2d.setComposite(BlendComposite.Multiply);
            // 3、绘制 dst 正方形
            g2d.setColor(ColorUtils.getSrcColor());
            g2d.fillRect(450, 150, 100, 100);

            graphics.drawImage(image, 0, 0, null);
        }

        public void paint1(Graphics graphics) {
            Graphics2D g2d = (Graphics2D) graphics;
            setRenderingHint(g2d);
//            drawBackground(g2d);

            // 错误用法
            // 1、先将目标图绘制到控件的 Graphics 上
            g2d.drawImage(dstImage, 0, 0, null);
            g2d = (Graphics2D) g2d.create();
            // 2、再设置控件的 Graphics 的 Composite
            g2d.setComposite(AlphaComposite.SrcAtop);
            // 3、最后将源图绘制到控件的 Graphics 上
            g2d.drawImage(srcImage, 0, 0, null);


            // 恢复
            g2d.setComposite(AlphaComposite.SrcOver);


            // 正确用法
            // 1、先设置目标图的 Graphics 的 Composite
            Graphics2D dstG2d = (Graphics2D) dstImage.getGraphics().create();
            dstG2d.setComposite(AlphaComposite.SrcAtop);
            // 2、然后将源图绘制到目标图的 Graphics 上
            // 2.1、绘制源 - 图片
            dstG2d.drawImage(srcImage, 0, 0, null);
            // 2.2、绘制其他形状
            dstG2d.setComposite(AlphaComposite.Xor);
            dstG2d.setColor(Color.RED);
            dstG2d.fillRect(50, 50, 200, 200);
            // 3、最后将目标图绘制到控件上
            g2d.drawImage(dstImage, 0, 300, null);


            // 恢复
            g2d.setComposite(AlphaComposite.SrcOver);


            // 错误用法
            // 绘制 src 圆
            g2d.setColor(ColorUtils.getDstColor());
            g2d.fillOval(400, 100, 100, 100);
            // 设置 Composite
            g2d = (Graphics2D) g2d.create();
            g2d.setComposite(AlphaComposite.SrcAtop);
            // 绘制 dst 正方形
            g2d.setColor(ColorUtils.getSrcColor());
            g2d.fillRect(450, 150, 100, 100);
        }

        private void drawBackground(Graphics2D g2d) {
            int bgSize = 10;
            int xCount = getWidth() / bgSize;
            int yCount = getHeight() / bgSize;
            for (int i = 0; i < xCount; i++) {
                for (int j = 0; j < yCount; j++) {
                    g2d.setColor((i + j) % 2 == 0 ? Color.BLACK : Color.WHITE);
                    g2d.fillRect(i * bgSize, j * bgSize, bgSize, bgSize);
                }
            }
        }

        private void createDstImage() {
            Graphics2D g = dstImage.createGraphics();
            setRenderingHint(g);
            Polygon p = new Polygon();
            p.addPoint(0, 0);
            p.addPoint(dstImage.getWidth(), 0);
            p.addPoint(0, (int) (dstImage.getHeight() / 1.5));
            g.setColor(ColorUtils.getDstColor());
            g.fill(p);
            g.dispose();
        }

        private void createSrcImage() {
            Graphics2D g = srcImage.createGraphics();
            setRenderingHint(g);
            Polygon p = new Polygon();
            p.addPoint(0, 0);
            p.addPoint(srcImage.getWidth(), 0);
            p.addPoint(srcImage.getWidth(), (int) (srcImage.getHeight() / 1.5));
            g.setColor(ColorUtils.getSrcColor());
            g.fill(p);
            g.dispose();
        }
    }

    public static void setRenderingHint(Graphics2D g) {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
    }

    public static void main(String[] args) {
        new DemoComposite();
        // 正常启动的话不需要加上 while true
    }
}
