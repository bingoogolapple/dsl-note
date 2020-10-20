package cn.bingoogolapple.gui.swing;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Arc2D;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JComponent;
import javax.swing.JFrame;

import cn.bingoogolapple.gui.utils.ScreenUtils;

/**
 * 演示绘图
 */
public class DemoFour extends JFrame {
    public DemoFour() {
        setTitle("绘图");
        setResizable(false);
        setSize(600, 600);
        ScreenUtils.showOnScreen(0, this);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setContentPane(new PaintComponent());

        setVisible(true);
    }

    public static void main(String[] args) {
        new DemoFour();
        // 正常启动的话不需要加上 while true
    }

    private static class PaintComponent extends JComponent {
        PaintComponent() {
            setLayout(null);
            setOpaque(false);
        }

        @Override
        public void repaint(int x, int y, int width, int height) {
        }

        @Override
        public void paint(Graphics graphics) {
            Graphics2D g = (Graphics2D) graphics;

            // 去锯齿
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            drawGrid(g);
            drawText(g);
            drawLine(g);
            drawRectAndArc(g);
            drawRectAndOval(g);
            drawRoundRect(g);
            drawPolygon(g);
            drawPath(g);
            drawArea(g);
        }

        private void drawArea(Graphics2D g) {
            g.setColor(Color.RED);
            Area allArea = new Area(new Rectangle(300, 300, 50, 50));
            // 添加指定区域
            allArea.add(new Area(new Ellipse2D.Float(350, 350, 50, 50)));
            // 减去指定区域
            allArea.subtract(new Area(new Rectangle(330, 330, 40, 40)));
            // 取交集
            allArea.intersect(new Area(new Rectangle(310, 310, 80, 80)));
            // 出去交集，包含两个区域剩余的部分
//            allArea.exclusiveOr(new Area(new Rectangle(310, 310, 80, 80)));
            g.fill(allArea);


            g.setColor(Color.MAGENTA);
            int bigX = 400, bigY = 400, bigSize = 50;
            int radiusDiff = 10;
            Ellipse2D.Double bitCircle = new Ellipse2D.Double(bigX, bigY, bigSize, bigSize);
            Ellipse2D.Double smallCircle = new Ellipse2D.Double(bigX + radiusDiff, bigY + radiusDiff, bigSize - radiusDiff * 2, bigSize - radiusDiff * 2);
            Area circle = new Area(bitCircle);
            circle.subtract(new Area(smallCircle));
            g.fill(circle);
        }

        private void drawPath(Graphics2D g) {
            Path2D path = new Path2D.Float();

            // 开始设置透明度组合，0.0f ~ 1.0f 从完全透明变成完全不透明
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.1f));

            g.setColor(Color.RED);
            path.append(new Ellipse2D.Float(20, 300, 50, 50), false);
            path.append(new Ellipse2D.Float(20, 400, 50, 50), false);
            g.draw(path);

            // 透明度设置结束
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));

            path = new Path2D.Float();
            g.setColor(Color.GREEN);
            path.append(new Ellipse2D.Float(120, 300, 50, 50), false);
            path.append(new Ellipse2D.Float(120, 400, 50, 50), true);
            g.draw(path);


            path = new Path2D.Float();
            g.setColor(Color.BLUE);

            // width = 60 height = 100
            int left = 220, top = 300, right = 280, bottom = 400;
            int topLeftRadius = 30, topRightRadius = 30, bottomRightRadius = 15, bottomLeftRadius = 0;
            float minSize = Math.min(right - left, bottom - top);

            // 起点为 3 点钟方向，逆时针方向为正方形
            float topLeftSize = Math.min(minSize, topLeftRadius * 2);
            if (topLeftSize > 0) {
                // x, y, width, height, 开始弧度，跨度
                path.append(new Arc2D.Float(left, top, topLeftSize, topLeftSize, 180, -90, Arc2D.OPEN), true);
            } else {
                path.moveTo(left, top);
            }

            float topRightSize = Math.min(minSize, topRightRadius * 2);
            if (topRightSize > 0) {
                path.append(new Arc2D.Float(right - topRightSize, top, topRightSize, topRightSize, 90, -90, Arc2D.OPEN), true);
            } else {
                path.lineTo(right, top);
            }

            float bottomRight = Math.min(minSize, bottomRightRadius * 2);
            if (bottomRight > 0) {
                path.append(new Arc2D.Float(right - bottomRight, bottom - bottomRight, bottomRight, bottomRight, 0, -90, Arc2D.OPEN), true);
            } else {
                path.lineTo(right, bottom);
            }

            float bottomLeft = Math.min(minSize, bottomLeftRadius * 2);
            if (bottomLeft > 0) {
                path.append(new Arc2D.Float(left, bottom - bottomLeft, bottomLeft, bottomLeft, -90, -90, Arc2D.OPEN), true);
            } else {
                path.lineTo(left, bottom);
            }

            path.closePath();
            g.draw(path);
        }

        private void drawPolygon(Graphics2D g) {
            int[] yPoints = new int[]{100, 150, 200, 250};

            int[] xPoints1 = new int[]{300, 300, 350, 350};
            g.setColor(Color.RED);
            g.drawPolygon(xPoints1, yPoints, xPoints1.length); // 绘制多边形，起点和终点封口

            int[] xPoints2 = new int[]{400, 400, 450, 450};
            g.setColor(Color.GREEN);
            g.drawPolyline(xPoints2, yPoints, xPoints2.length); // 绘制连续的多条线，起点和终点不封口


            Polygon polygon = new Polygon();
            polygon.addPoint(500, 100);
            polygon.addPoint(500, 150);
            polygon.addPoint(550, 200);
            polygon.addPoint(550, 250);

            g.setColor(Color.PINK);
            Rectangle bounds = polygon.getBounds();
            g.drawRect(bounds.x, bounds.y, bounds.width, bounds.height);

            // 设置线宽度
            g.setStroke(new BasicStroke(2));
            g.setColor(Color.BLUE);
            g.drawPolygon(polygon);
        }

        private void drawRoundRect(Graphics2D g) {
            g.setColor(Color.RED);
            // arcWidth、arcHeight 对应圆弧外矩形宽高
            g.drawRoundRect(20, 200, 50, 50, 25, 25); // 直接绘制圆角矩形
            g.setColor(Color.GREEN);
            g.drawOval(20, 200, 25, 25);

            g.setColor(Color.BLUE);
            g.drawRoundRect(80, 200, 50, 50, 50, 50);

            g.setColor(Color.PINK);
            g.draw(new RoundRectangle2D.Float(140, 200, 50, 50, 25, 25)); // 通过 Shape 绘制圆角矩形
        }

        private void drawRectAndOval(Graphics2D g) {
            g.setColor(Color.RED);
            g.drawRect(20, 120, 100, 50);
            g.setColor(Color.GREEN);
            /**
             * x、y 对应矩形左上角
             * width、height 对应圆弧外矩形宽高
             */
            g.drawOval(20, 120, 100, 50); // 直接绘制椭圆

            g.setColor(Color.PINK);
            g.drawRect(150, 120, 100, 50);
            g.setColor(Color.BLUE);
            g.draw(new Ellipse2D.Float(150, 120, 100, 50)); // 通过 Shape 绘制椭圆
        }

        private void drawRectAndArc(Graphics2D g) {
            g.setColor(Color.RED);
            // x、y 对应矩形左上角
            g.drawRect(20, 50, 100, 50); // 直接绘制矩形

            g.setColor(Color.GREEN);
            /**
             * x、y 对应矩形左上角
             * width、height 对应圆弧外矩形宽高
             * 起点为 3 点钟方向，逆时针方向为正方形
             */
            g.drawArc(20, 50, 100, 50, 0, 90); // 直接绘制圆弧
            g.setColor(Color.BLUE);
            g.fillArc(20, 50, 100, 50, 0, -90); // 如果是 fill 的话，起点和终点饼状图封口

            g.setColor(Color.MAGENTA);
            g.drawArc(20, 50, 100, 50, 90, 45);

            g.setColor(Color.CYAN);
            g.drawArc(20, 50, 100, 50, -90, -45);


            g.setColor(Color.ORANGE);
            g.draw(new Rectangle2D.Float(150, 50, 100, 50)); // 通过 Shape 绘制矩形

            g.setColor(Color.GREEN);
            /**
             * x、y 对应矩形左上角
             * width、height 对应圆弧外矩形宽高
             * 起点为 3 点钟方向，逆时针方向为正方形
             */
            g.draw(new Arc2D.Float(150, 50, 100, 50, 0, 90, Arc2D.OPEN)); // 通过 Shape 绘制圆弧，不封口
            g.setColor(Color.BLUE);
            g.fill(new Arc2D.Float(150, 50, 100, 50, 0, -90, Arc2D.OPEN)); // 如果是 fill 的话，Arc2D.OPEN 时也是起点和终点直接连线封口

            g.setColor(Color.MAGENTA);
            g.draw(new Arc2D.Float(150, 50, 100, 50, 90, 45, Arc2D.CHORD)); // 起点和终点直接连线封口

            g.setColor(Color.CYAN);
            g.draw(new Arc2D.Float(150, 50, 100, 50, -90, -45, Arc2D.PIE)); // 起点和终点饼状图封口

            g.setColor(Color.RED);
            g.fill(new Arc2D.Float(150, 50, 100, 50, -135, -90, Arc2D.PIE)); // 起点和终点饼状图封口
        }

        private void drawLine(Graphics2D g) {
            g.setColor(Color.RED);
            g.drawLine(20, 20, 70, 40); // 直接绘制线
            g.setColor(Color.GREEN);
            g.draw(new Line2D.Float(30, 30, 80, 50)); // 通过 Shape 绘制线
        }

        private void drawText(Graphics2D g) {
            g.setColor(Color.RED);
            // x、y 对应的是文字的左下角
            g.drawString("文字", 0, 12);
        }

        private void drawGrid(Graphics2D g) {
            // 绘制网格背景
            g.setPaint(Color.LIGHT_GRAY);
            for (int i = 10; i < getSize().width; i += 10) {
                // 竖线
                g.drawLine(i, 0, i, getSize().height);
            }
            for (int i = 10; i < getSize().height; i += 10) {
                // 横线
                g.drawLine(0, i, getSize().width, i);
            }
        }
    }
}
