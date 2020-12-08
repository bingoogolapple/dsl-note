package cn.bingoogolapple.gui.utils;

import java.awt.Color;

/**
 * 作者:王浩
 * 创建时间:2020/12/8
 * 描述:
 */
public class ColorUtils {
    private ColorUtils() {
    }

    public static Color getDstColor() {
        return toAwtColor(0xFFFFCC44);
    }

    public static Color getSrcColor() {
        return toAwtColor(0xFF66AAFF);
    }

    public static java.awt.Color toAwtColor(int color) {
        return new java.awt.Color(r(color), g(color), b(color), a(color));
    }

    public static int r(int color) {
        return (color >> 16) & 0xFF;
    }

    public static int g(int color) {
        return (color >> 8) & 0xFF;
    }

    public static int b(int color) {
        return color & 0xFF;
    }

    public static int a(int color) {
        return color >>> 24;
    }
}
