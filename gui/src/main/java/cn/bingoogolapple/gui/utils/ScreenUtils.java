package cn.bingoogolapple.gui.utils;

import java.awt.*;
import java.lang.reflect.Method;

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

    public static void showDual(Window window) {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] graphicsDevices = ge.getScreenDevices();
        if (graphicsDevices.length > 0) {
            Rectangle bounds = graphicsDevices[1].getDefaultConfiguration().getBounds();
            window.setBounds(bounds);
            window.setVisible(true);
            setFullScreenWindow(window);
        }
    }

    public static void setFullScreenWindow(Window window) {
        if (window == null) {
            return;
        }
        try {
            Class<?> util = Class.forName("com.apple.eawt.FullScreenUtilities");
            Method method = util.getMethod("setWindowCanFullScreen", Window.class, Boolean.TYPE);
            method.invoke(util, window, true);
            requestToggleFullScreen(window);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public static void requestToggleFullScreen(Window window){
        if (window == null) {
            return;
        }
        try {
            Class<?> windowClass = Class.forName("java.awt.Window");
            Class<?> appClass = Class.forName("com.apple.eawt.Application");
            Method get = appClass.getMethod("getApplication", (Class[]) null);
            Object app = get.invoke(null, (Object[]) null);
            Method requestToggleFullScreen = appClass.getMethod("requestToggleFullScreen", windowClass);
            requestToggleFullScreen.invoke(app, window);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
