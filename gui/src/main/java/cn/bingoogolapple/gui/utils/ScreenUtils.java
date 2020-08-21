package cn.bingoogolapple.gui.utils;

import java.awt.*;

public final class ScreenUtils {
    private ScreenUtils() {
    }

    public static void showOnScreen(int screen, Component component) {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] graphicsDevices = ge.getScreenDevices();
        if (screen > -1 && screen < graphicsDevices.length) { // 一个或多个屏幕
            component.setLocation(component.getX(), graphicsDevices[screen].getDefaultConfiguration().getBounds().y);
        } else if (graphicsDevices.length > 0) { // 只有一个屏幕
            component.setLocation(component.getX(), graphicsDevices[0].getDefaultConfiguration().getBounds().y);
        } else { // 未获取到屏幕信息
            throw new RuntimeException("No Screens Found");
        }
    }
}
