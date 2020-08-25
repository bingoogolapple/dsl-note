package cn.bingoogolapple.gui.utils;

import javax.swing.*;

public final class ResourceUtils {
    private static final String RESOURCE_MAIN = ClassLoader.getSystemResource(".").getPath().replace("classes/java/main/", "resources/main/");

    private ResourceUtils() {
    }

    public static String getResourcePath(String relativePath) {
        return RESOURCE_MAIN + relativePath;
    }

    public static ImageIcon getImageIcon(String relativePath) {
        return new ImageIcon(RESOURCE_MAIN + relativePath);
    }
}
