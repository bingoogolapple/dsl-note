package cn.bingoogolapple.gui.swing.composite1;

import java.awt.Color;
import java.awt.Composite;
import java.awt.CompositeContext;
import java.awt.RenderingHints;
import java.awt.image.ColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DirectColorModel;
import java.awt.image.Raster;
import java.awt.image.RasterFormatException;
import java.awt.image.WritableRaster;

public final class BlendComposite implements Composite {
    public enum BlendingMode {
        AVERAGE,
        MULTIPLY,
        SCREEN,
        DARKEN,
        LIGHTEN,
        OVERLAY,
        HARD_LIGHT,
        SOFT_LIGHT,
        DIFFERENCE,
        NEGATION,
        EXCLUSION,
        COLOR_DODGE,
        INVERSE_COLOR_DODGE,
        SOFT_DODGE,
        COLOR_BURN,
        INVERSE_COLOR_BURN,
        SOFT_BURN,
        REFLECT,
        GLOW,
        FREEZE,
        HEAT,
        ADD,
        SUBTRACT,
        STAMP,
        RED,
        GREEN,
        BLUE,
        HUE,
        SATURATION,
        COLOR,
        LUMINOSITY
    }

    public static final BlendComposite Average = new BlendComposite(BlendingMode.AVERAGE);
    public static final BlendComposite Multiply = new BlendComposite(BlendingMode.MULTIPLY);
    public static final BlendComposite Screen = new BlendComposite(BlendingMode.SCREEN);
    public static final BlendComposite Darken = new BlendComposite(BlendingMode.DARKEN);
    public static final BlendComposite Lighten = new BlendComposite(BlendingMode.LIGHTEN);
    public static final BlendComposite Overlay = new BlendComposite(BlendingMode.OVERLAY);
    public static final BlendComposite HardLight = new BlendComposite(BlendingMode.HARD_LIGHT);
    public static final BlendComposite SoftLight = new BlendComposite(BlendingMode.SOFT_LIGHT);
    public static final BlendComposite Difference = new BlendComposite(BlendingMode.DIFFERENCE);
    public static final BlendComposite Negation = new BlendComposite(BlendingMode.NEGATION);
    public static final BlendComposite Exclusion = new BlendComposite(BlendingMode.EXCLUSION);
    public static final BlendComposite ColorDodge = new BlendComposite(BlendingMode.COLOR_DODGE);
    public static final BlendComposite InverseColorDodge = new BlendComposite(BlendingMode.INVERSE_COLOR_DODGE);
    public static final BlendComposite SoftDodge = new BlendComposite(BlendingMode.SOFT_DODGE);
    public static final BlendComposite ColorBurn = new BlendComposite(BlendingMode.COLOR_BURN);
    public static final BlendComposite InverseColorBurn = new BlendComposite(BlendingMode.INVERSE_COLOR_BURN);
    public static final BlendComposite SoftBurn = new BlendComposite(BlendingMode.SOFT_BURN);
    public static final BlendComposite Reflect = new BlendComposite(BlendingMode.REFLECT);
    public static final BlendComposite Glow = new BlendComposite(BlendingMode.GLOW);
    public static final BlendComposite Freeze = new BlendComposite(BlendingMode.FREEZE);
    public static final BlendComposite Heat = new BlendComposite(BlendingMode.HEAT);
    public static final BlendComposite Add = new BlendComposite(BlendingMode.ADD);
    public static final BlendComposite Subtract = new BlendComposite(BlendingMode.SUBTRACT);
    public static final BlendComposite Stamp = new BlendComposite(BlendingMode.STAMP);
    public static final BlendComposite Red = new BlendComposite(BlendingMode.RED);
    public static final BlendComposite Green = new BlendComposite(BlendingMode.GREEN);
    public static final BlendComposite Blue = new BlendComposite(BlendingMode.BLUE);
    public static final BlendComposite Hue = new BlendComposite(BlendingMode.HUE);
    public static final BlendComposite Saturation = new BlendComposite(BlendingMode.SATURATION);
    public static final BlendComposite Color = new BlendComposite(BlendingMode.COLOR);
    public static final BlendComposite Luminosity = new BlendComposite(BlendingMode.LUMINOSITY);
    private final float alpha;
    private final BlendingMode mode;

    private BlendComposite(BlendingMode mode) {
        this(mode, 1.0f);
    }

    private BlendComposite(BlendingMode mode, float alpha) {
        this.mode = mode;
        if (alpha < 0.0f || alpha > 1.0f) {
            throw new IllegalArgumentException(
                    "alpha must be comprised between 0.0f and 1.0f");
        }
        this.alpha = alpha;
    }

    public static BlendComposite getInstance(BlendingMode mode) {
        return new BlendComposite(mode);
    }

    public static BlendComposite getInstance(BlendingMode mode, float alpha) {
        return new BlendComposite(mode, alpha);
    }

    public BlendComposite derive(BlendingMode mode) {
        return this.mode == mode ? this : new BlendComposite(mode, getAlpha());
    }

    public BlendComposite derive(float alpha) {
        return this.alpha == alpha ? this : new BlendComposite(getMode(), alpha);
    }

    public float getAlpha() {
        return alpha;
    }

    public BlendingMode getMode() {
        return mode;
    }

    @Override
    public int hashCode() {
        return Float.floatToIntBits(alpha) * 31 + mode.ordinal();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof BlendComposite)) {
            return false;
        }
        BlendComposite bc = (BlendComposite) obj;
        return mode == bc.mode && alpha == bc.alpha;
    }

    private static boolean isRgbColorModel(ColorModel cm) {
        if (cm instanceof DirectColorModel &&
                cm.getTransferType() == DataBuffer.TYPE_INT) {
            DirectColorModel directCM = (DirectColorModel) cm;
            return directCM.getRedMask() == 0x00FF0000 &&
                    directCM.getGreenMask() == 0x0000FF00 &&
                    directCM.getBlueMask() == 0x000000FF &&
                    (directCM.getNumComponents() == 3 ||
                            directCM.getAlphaMask() == 0xFF000000);
        }
        return false;
    }

    private static boolean isBgrColorModel(ColorModel cm) {
        if (cm instanceof DirectColorModel &&
                cm.getTransferType() == DataBuffer.TYPE_INT) {
            DirectColorModel directCM = (DirectColorModel) cm;
            return directCM.getRedMask() == 0x000000FF &&
                    directCM.getGreenMask() == 0x0000FF00 &&
                    directCM.getBlueMask() == 0x00FF0000 &&
                    (directCM.getNumComponents() == 3 ||
                            directCM.getAlphaMask() == 0xFF000000);
        }
        return false;
    }

    public CompositeContext createContext(ColorModel srcColorModel,
            ColorModel dstColorModel,
            RenderingHints hints) {
        if (isRgbColorModel(srcColorModel) && isRgbColorModel(dstColorModel)) {
            return new BlendingRgbContext(this);
        } else if (isBgrColorModel(srcColorModel) && isBgrColorModel(dstColorModel)) {
            return new BlendingBgrContext(this);
        }
        throw new RasterFormatException("Incompatible color models");
    }

    private static abstract class BlendingContext implements CompositeContext {
        protected final Blender blender;
        protected final BlendComposite composite;

        private BlendingContext(BlendComposite composite) {
            this.composite = composite;
            this.blender = Blender.getBlenderFor(composite);
        }

        public void dispose() {
        }
    }

    private static class BlendingRgbContext extends BlendingContext {
        private BlendingRgbContext(BlendComposite composite) {
            super(composite);
        }

        public void compose(Raster src, Raster dstIn, WritableRaster dstOut) {
            int width = Math.min(src.getWidth(), dstIn.getWidth());
            int height = Math.min(src.getHeight(), dstIn.getHeight());
            float alpha = composite.getAlpha();
            int[] result = new int[4];
            int[] srcPixel = new int[4];
            int[] dstPixel = new int[4];
            int[] srcPixels = new int[width];
            int[] dstPixels = new int[width];
            for (int y = 0; y < height; y++) {
                src.getDataElements(0, y, width, 1, srcPixels);
                dstIn.getDataElements(0, y, width, 1, dstPixels);
                for (int x = 0; x < width; x++) {
                    // pixels are stored as INT_ARGB
                    // our arrays are [R, G, B, A]
                    int pixel = srcPixels[x];
                    srcPixel[0] = (pixel >> 16) & 0xFF;
                    srcPixel[1] = (pixel >> 8) & 0xFF;
                    srcPixel[2] = (pixel) & 0xFF;
                    srcPixel[3] = (pixel >> 24) & 0xFF;
                    pixel = dstPixels[x];
                    dstPixel[0] = (pixel >> 16) & 0xFF;
                    dstPixel[1] = (pixel >> 8) & 0xFF;
                    dstPixel[2] = (pixel) & 0xFF;
                    dstPixel[3] = (pixel >> 24) & 0xFF;
                    blender.blend(srcPixel, dstPixel, result);                    // mixes the result with the opacity
                    dstPixels[x] = ((int) (dstPixel[3] + (result[3] - dstPixel[3]) * alpha) & 0xFF) << 24 |
                            ((int) (dstPixel[0] + (result[0] - dstPixel[0]) * alpha) & 0xFF) << 16 |
                            ((int) (dstPixel[1] + (result[1] - dstPixel[1]) * alpha) & 0xFF) << 8 |
                            (int) (dstPixel[2] + (result[2] - dstPixel[2]) * alpha) & 0xFF;
                }
                dstOut.setDataElements(0, y, width, 1, dstPixels);
            }
        }
    }

    private static class BlendingBgrContext extends BlendingContext {
        private BlendingBgrContext(BlendComposite composite) {
            super(composite);
        }

        public void compose(Raster src, Raster dstIn, WritableRaster dstOut) {
            int width = Math.min(src.getWidth(), dstIn.getWidth());
            int height = Math.min(src.getHeight(), dstIn.getHeight());
            float alpha = composite.getAlpha();
            int[] result = new int[4];
            int[] srcPixel = new int[4];
            int[] dstPixel = new int[4];
            int[] srcPixels = new int[width];
            int[] dstPixels = new int[width];
            for (int y = 0; y < height; y++) {
                src.getDataElements(0, y, width, 1, srcPixels);
                dstIn.getDataElements(0, y, width, 1, dstPixels);
                for (int x = 0; x < width; x++) {
                    int pixel = srcPixels[x];
                    srcPixel[0] = (pixel) & 0xFF;
                    srcPixel[1] = (pixel >> 8) & 0xFF;
                    srcPixel[2] = (pixel >> 16) & 0xFF;
                    srcPixel[3] = (pixel >> 24) & 0xFF;
                    pixel = dstPixels[x];
                    dstPixel[0] = (pixel) & 0xFF;
                    dstPixel[1] = (pixel >> 8) & 0xFF;
                    dstPixel[2] = (pixel >> 16) & 0xFF;
                    dstPixel[3] = (pixel >> 24) & 0xFF;
                    blender.blend(srcPixel, dstPixel, result);                    // mixes the result with the opacity
                    dstPixels[x] = ((int) (dstPixel[3] + (result[3] - dstPixel[3]) * alpha) & 0xFF) << 24 |
                            ((int) (dstPixel[0] + (result[0] - dstPixel[0]) * alpha) & 0xFF) |
                            ((int) (dstPixel[1] + (result[1] - dstPixel[1]) * alpha) & 0xFF) << 8 |
                            ((int) (dstPixel[2] + (result[2] - dstPixel[2]) * alpha) & 0xFF) << 16;
                }
                dstOut.setDataElements(0, y, width, 1, dstPixels);
            }
        }
    }

    private static abstract class Blender {
        public abstract void blend(int[] src, int[] dst, int[] result);

        public static Blender getBlenderFor(BlendComposite composite) {
            switch (composite.getMode()) {
                case ADD:
                    return new BlendComposite.Blender() {
                        @Override
                        public void blend(int[] src, int[] dst, int[] result) {
                            result[0] = Math.min(255, src[0] + dst[0]);
                            result[1] = Math.min(255, src[1] + dst[1]);
                            result[2] = Math.min(255, src[2] + dst[2]);
                            result[3] = Math.min(255, src[3] + dst[3]);
                        }
                    };
                case AVERAGE:
                    return new BlendComposite.Blender() {
                        @Override
                        public void blend(int[] src, int[] dst, int[] result) {
                            result[0] = (src[0] + dst[0]) >> 1;
                            result[1] = (src[1] + dst[1]) >> 1;
                            result[2] = (src[2] + dst[2]) >> 1;
                            result[3] = Math.min(255, src[3] + dst[3] - (src[3] * dst[3]) / 255);
                        }
                    };
                case BLUE:
                    return new BlendComposite.Blender() {
                        @Override
                        public void blend(int[] src, int[] dst, int[] result) {
                            result[0] = dst[0];
                            result[1] = src[1];
                            result[2] = dst[2];
                            result[3] = Math.min(255, src[3] + dst[3] - (src[3] * dst[3]) / 255);
                        }
                    };
                case COLOR:
                    return new BlendComposite.Blender() {
                        @Override
                        public void blend(int[] src, int[] dst, int[] result) {
                            float[] srcHSL = new float[3];
                            ColorUtils.RGBtoHSL(src[0], src[1], src[2], srcHSL);
                            float[] dstHSL = new float[3];
                            ColorUtils.RGBtoHSL(dst[0], dst[1], dst[2], dstHSL);
                            ColorUtils.HSLtoRGB(srcHSL[0], srcHSL[1], dstHSL[2], result);
                            result[3] = Math.min(255, src[3] + dst[3] - (src[3] * dst[3]) / 255);
                        }
                    };
                case COLOR_BURN:
                    return new BlendComposite.Blender() {
                        @Override
                        public void blend(int[] src, int[] dst, int[] result) {
                            result[0] = src[0] == 0 ? 0 :
                                    Math.max(0, 255 - (((255 - dst[0]) << 8) / src[0]));
                            result[1] = src[1] == 0 ? 0 :
                                    Math.max(0, 255 - (((255 - dst[1]) << 8) / src[1]));
                            result[2] = src[2] == 0 ? 0 :
                                    Math.max(0, 255 - (((255 - dst[2]) << 8) / src[2]));
                            result[3] = Math.min(255, src[3] + dst[3] - (src[3] * dst[3]) / 255);
                        }
                    };
                case COLOR_DODGE:
                    return new BlendComposite.Blender() {
                        @Override
                        public void blend(int[] src, int[] dst, int[] result) {
                            result[0] = src[0] == 255 ? 255 :
                                    Math.min((dst[0] << 8) / (255 - src[0]), 255);
                            result[1] = src[1] == 255 ? 255 :
                                    Math.min((dst[1] << 8) / (255 - src[1]), 255);
                            result[2] = src[2] == 255 ? 255 :
                                    Math.min((dst[2] << 8) / (255 - src[2]), 255);
                            result[3] = Math.min(255, src[3] + dst[3] - (src[3] * dst[3]) / 255);
                        }
                    };
                case DARKEN:
                    return new BlendComposite.Blender() {
                        @Override
                        public void blend(int[] src, int[] dst, int[] result) {
                            result[0] = Math.min(src[0], dst[0]);
                            result[1] = Math.min(src[1], dst[1]);
                            result[2] = Math.min(src[2], dst[2]);
                            result[3] = Math.min(255, src[3] + dst[3] - (src[3] * dst[3]) / 255);
                        }
                    };
                case DIFFERENCE:
                    return new BlendComposite.Blender() {
                        @Override
                        public void blend(int[] src, int[] dst, int[] result) {
                            result[0] = Math.abs(dst[0] - src[0]);
                            result[1] = Math.abs(dst[1] - src[1]);
                            result[2] = Math.abs(dst[2] - src[2]);
                            result[3] = Math.min(255, src[3] + dst[3] - (src[3] * dst[3]) / 255);
                        }
                    };
                case EXCLUSION:
                    return new BlendComposite.Blender() {
                        @Override
                        public void blend(int[] src, int[] dst, int[] result) {
                            result[0] = dst[0] + src[0] - (dst[0] * src[0] >> 7);
                            result[1] = dst[1] + src[1] - (dst[1] * src[1] >> 7);
                            result[2] = dst[2] + src[2] - (dst[2] * src[2] >> 7);
                            result[3] = Math.min(255, src[3] + dst[3] - (src[3] * dst[3]) / 255);
                        }
                    };
                case FREEZE:
                    return new BlendComposite.Blender() {
                        @Override
                        public void blend(int[] src, int[] dst, int[] result) {
                            result[0] = src[0] == 0 ? 0 :
                                    Math.max(0, 255 - (255 - dst[0]) * (255 - dst[0]) / src[0]);
                            result[1] = src[1] == 0 ? 0 :
                                    Math.max(0, 255 - (255 - dst[1]) * (255 - dst[1]) / src[1]);
                            result[2] = src[2] == 0 ? 0 :
                                    Math.max(0, 255 - (255 - dst[2]) * (255 - dst[2]) / src[2]);
                            result[3] = Math.min(255, src[3] + dst[3] - (src[3] * dst[3]) / 255);
                        }
                    };
                case GLOW:
                    return new BlendComposite.Blender() {
                        @Override
                        public void blend(int[] src, int[] dst, int[] result) {
                            result[0] = dst[0] == 255 ? 255 :
                                    Math.min(255, src[0] * src[0] / (255 - dst[0]));
                            result[1] = dst[1] == 255 ? 255 :
                                    Math.min(255, src[1] * src[1] / (255 - dst[1]));
                            result[2] = dst[2] == 255 ? 255 :
                                    Math.min(255, src[2] * src[2] / (255 - dst[2]));
                            result[3] = Math.min(255, src[3] + dst[3] - (src[3] * dst[3]) / 255);
                        }
                    };
                case GREEN:
                    return new BlendComposite.Blender() {
                        @Override
                        public void blend(int[] src, int[] dst, int[] result) {
                            result[0] = dst[0];
                            result[1] = dst[1];
                            result[2] = src[2];
                            result[3] = Math.min(255, src[3] + dst[3] - (src[3] * dst[3]) / 255);
                        }
                    };
                case HARD_LIGHT:
                    return new BlendComposite.Blender() {
                        @Override
                        public void blend(int[] src, int[] dst, int[] result) {
                            result[0] = src[0] < 128 ? dst[0] * src[0] >> 7 :
                                    255 - ((255 - src[0]) * (255 - dst[0]) >> 7);
                            result[1] = src[1] < 128 ? dst[1] * src[1] >> 7 :
                                    255 - ((255 - src[1]) * (255 - dst[1]) >> 7);
                            result[2] = src[2] < 128 ? dst[2] * src[2] >> 7 :
                                    255 - ((255 - src[2]) * (255 - dst[2]) >> 7);
                            result[3] = Math.min(255, src[3] + dst[3] - (src[3] * dst[3]) / 255);
                        }
                    };
                case HEAT:
                    return new BlendComposite.Blender() {
                        @Override
                        public void blend(int[] src, int[] dst, int[] result) {
                            result[0] = dst[0] == 0 ? 0 :
                                    Math.max(0, 255 - (255 - src[0]) * (255 - src[0]) / dst[0]);
                            result[1] = dst[1] == 0 ? 0 :
                                    Math.max(0, 255 - (255 - src[1]) * (255 - src[1]) / dst[1]);
                            result[2] = dst[2] == 0 ? 0 :
                                    Math.max(0, 255 - (255 - src[2]) * (255 - src[2]) / dst[2]);
                            result[3] = Math.min(255, src[3] + dst[3] - (src[3] * dst[3]) / 255);
                        }
                    };
                case HUE:
                    return new BlendComposite.Blender() {
                        @Override
                        public void blend(int[] src, int[] dst, int[] result) {
                            float[] srcHSL = new float[3];
                            ColorUtils.RGBtoHSL(src[0], src[1], src[2], srcHSL);
                            float[] dstHSL = new float[3];
                            ColorUtils.RGBtoHSL(dst[0], dst[1], dst[2], dstHSL);
                            ColorUtils.HSLtoRGB(srcHSL[0], dstHSL[1], dstHSL[2], result);
                            result[3] = Math.min(255, src[3] + dst[3] - (src[3] * dst[3]) / 255);
                        }
                    };
                case INVERSE_COLOR_BURN:
                    return new BlendComposite.Blender() {
                        @Override
                        public void blend(int[] src, int[] dst, int[] result) {
                            result[0] = dst[0] == 0 ? 0 :
                                    Math.max(0, 255 - (((255 - src[0]) << 8) / dst[0]));
                            result[1] = dst[1] == 0 ? 0 :
                                    Math.max(0, 255 - (((255 - src[1]) << 8) / dst[1]));
                            result[2] = dst[2] == 0 ? 0 :
                                    Math.max(0, 255 - (((255 - src[2]) << 8) / dst[2]));
                            result[3] = Math.min(255, src[3] + dst[3] - (src[3] * dst[3]) / 255);
                        }
                    };
                case INVERSE_COLOR_DODGE:
                    return new BlendComposite.Blender() {
                        @Override
                        public void blend(int[] src, int[] dst, int[] result) {
                            result[0] = dst[0] == 255 ? 255 :
                                    Math.min((src[0] << 8) / (255 - dst[0]), 255);
                            result[1] = dst[1] == 255 ? 255 :
                                    Math.min((src[1] << 8) / (255 - dst[1]), 255);
                            result[2] = dst[2] == 255 ? 255 :
                                    Math.min((src[2] << 8) / (255 - dst[2]), 255);
                            result[3] = Math.min(255, src[3] + dst[3] - (src[3] * dst[3]) / 255);
                        }
                    };
                case LIGHTEN:
                    return new BlendComposite.Blender() {
                        @Override
                        public void blend(int[] src, int[] dst, int[] result) {
                            result[0] = Math.max(src[0], dst[0]);
                            result[1] = Math.max(src[1], dst[1]);
                            result[2] = Math.max(src[2], dst[2]);
                            result[3] = Math.min(255, src[3] + dst[3] - (src[3] * dst[3]) / 255);
                        }
                    };
                case LUMINOSITY:
                    return new BlendComposite.Blender() {
                        @Override
                        public void blend(int[] src, int[] dst, int[] result) {
                            float[] srcHSL = new float[3];
                            ColorUtils.RGBtoHSL(src[0], src[1], src[2], srcHSL);
                            float[] dstHSL = new float[3];
                            ColorUtils.RGBtoHSL(dst[0], dst[1], dst[2], dstHSL);
                            ColorUtils.HSLtoRGB(dstHSL[0], dstHSL[1], srcHSL[2], result);
                            result[3] = Math.min(255, src[3] + dst[3] - (src[3] * dst[3]) / 255);
                        }
                    };
                case MULTIPLY:
                    return new BlendComposite.Blender() {
                        @Override
                        public void blend(int[] src, int[] dst, int[] result) {
                            result[0] = (src[0] * dst[0]) >> 8;
                            result[1] = (src[1] * dst[1]) >> 8;
                            result[2] = (src[2] * dst[2]) >> 8;
                            result[3] = Math.min(255, src[3] + dst[3] - (src[3] * dst[3]) / 255);
                        }
                    };
                case NEGATION:
                    return new BlendComposite.Blender() {
                        @Override
                        public void blend(int[] src, int[] dst, int[] result) {
                            result[0] = 255 - Math.abs(255 - dst[0] - src[0]);
                            result[1] = 255 - Math.abs(255 - dst[1] - src[1]);
                            result[2] = 255 - Math.abs(255 - dst[2] - src[2]);
                            result[3] = Math.min(255, src[3] + dst[3] - (src[3] * dst[3]) / 255);
                        }
                    };
                case OVERLAY:
                    return new BlendComposite.Blender() {
                        @Override
                        public void blend(int[] src, int[] dst, int[] result) {
                            result[0] = dst[0] < 128 ? dst[0] * src[0] >> 7 :
                                    255 - ((255 - dst[0]) * (255 - src[0]) >> 7);
                            result[1] = dst[1] < 128 ? dst[1] * src[1] >> 7 :
                                    255 - ((255 - dst[1]) * (255 - src[1]) >> 7);
                            result[2] = dst[2] < 128 ? dst[2] * src[2] >> 7 :
                                    255 - ((255 - dst[2]) * (255 - src[2]) >> 7);
                            result[3] = Math.min(255, src[3] + dst[3] - (src[3] * dst[3]) / 255);
                        }
                    };
                case RED:
                    return new BlendComposite.Blender() {
                        @Override
                        public void blend(int[] src, int[] dst, int[] result) {
                            result[0] = src[0];
                            result[1] = dst[1];
                            result[2] = dst[2];
                            result[3] = Math.min(255, src[3] + dst[3] - (src[3] * dst[3]) / 255);
                        }
                    };
                case REFLECT:
                    return new BlendComposite.Blender() {
                        @Override
                        public void blend(int[] src, int[] dst, int[] result) {
                            result[0] = src[0] == 255 ? 255 :
                                    Math.min(255, dst[0] * dst[0] / (255 - src[0]));
                            result[1] = src[1] == 255 ? 255 :
                                    Math.min(255, dst[1] * dst[1] / (255 - src[1]));
                            result[2] = src[2] == 255 ? 255 :
                                    Math.min(255, dst[2] * dst[2] / (255 - src[2]));
                            result[3] = Math.min(255, src[3] + dst[3] - (src[3] * dst[3]) / 255);
                        }
                    };
                case SATURATION:
                    return new BlendComposite.Blender() {
                        @Override
                        public void blend(int[] src, int[] dst, int[] result) {
                            float[] srcHSL = new float[3];
                            ColorUtils.RGBtoHSL(src[0], src[1], src[2], srcHSL);
                            float[] dstHSL = new float[3];
                            ColorUtils.RGBtoHSL(dst[0], dst[1], dst[2], dstHSL);
                            ColorUtils.HSLtoRGB(dstHSL[0], srcHSL[1], dstHSL[2], result);
                            result[3] = Math.min(255, src[3] + dst[3] - (src[3] * dst[3]) / 255);
                        }
                    };
                case SCREEN:
                    return new BlendComposite.Blender() {
                        @Override
                        public void blend(int[] src, int[] dst, int[] result) {
                            result[0] = 255 - ((255 - src[0]) * (255 - dst[0]) >> 8);
                            result[1] = 255 - ((255 - src[1]) * (255 - dst[1]) >> 8);
                            result[2] = 255 - ((255 - src[2]) * (255 - dst[2]) >> 8);
                            result[3] = Math.min(255, src[3] + dst[3] - (src[3] * dst[3]) / 255);
                        }
                    };
                case SOFT_BURN:
                    return new BlendComposite.Blender() {
                        @Override
                        public void blend(int[] src, int[] dst, int[] result) {
                            result[0] = dst[0] + src[0] < 256 ?
                                    (dst[0] == 255 ? 255 :
                                            Math.min(255, (src[0] << 7) / (255 - dst[0]))) :
                                    Math.max(0, 255 - (((255 - dst[0]) << 7) / src[0]));
                            result[1] = dst[1] + src[1] < 256 ?
                                    (dst[1] == 255 ? 255 :
                                            Math.min(255, (src[1] << 7) / (255 - dst[1]))) :
                                    Math.max(0, 255 - (((255 - dst[1]) << 7) / src[1]));
                            result[2] = dst[2] + src[2] < 256 ?
                                    (dst[2] == 255 ? 255 :
                                            Math.min(255, (src[2] << 7) / (255 - dst[2]))) :
                                    Math.max(0, 255 - (((255 - dst[2]) << 7) / src[2]));
                            result[3] = Math.min(255, src[3] + dst[3] - (src[3] * dst[3]) / 255);
                        }
                    };
                case SOFT_DODGE:
                    return new BlendComposite.Blender() {
                        @Override
                        public void blend(int[] src, int[] dst, int[] result) {
                            result[0] = dst[0] + src[0] < 256 ?
                                    (src[0] == 255 ? 255 :
                                            Math.min(255, (dst[0] << 7) / (255 - src[0]))) :
                                    Math.max(0, 255 - (((255 - src[0]) << 7) / dst[0]));
                            result[1] = dst[1] + src[1] < 256 ?
                                    (src[1] == 255 ? 255 :
                                            Math.min(255, (dst[1] << 7) / (255 - src[1]))) :
                                    Math.max(0, 255 - (((255 - src[1]) << 7) / dst[1]));
                            result[2] = dst[2] + src[2] < 256 ?
                                    (src[2] == 255 ? 255 :
                                            Math.min(255, (dst[2] << 7) / (255 - src[2]))) :
                                    Math.max(0, 255 - (((255 - src[2]) << 7) / dst[2]));
                            result[3] = Math.min(255, src[3] + dst[3] - (src[3] * dst[3]) / 255);
                        }
                    };
                case SOFT_LIGHT:
                    return new BlendComposite.Blender() {
                        @Override
                        public void blend(int[] src, int[] dst, int[] result) {
                            int mRed = src[0] * dst[0] / 255;
                            int mGreen = src[1] * dst[1] / 255;
                            int mBlue = src[2] * dst[2] / 255;
                            result[0] = mRed + src[0] * (255 - ((255 - src[0]) * (255 - dst[0]) / 255) - mRed) / 255;
                            result[1] = mGreen + src[1] * (255 - ((255 - src[1]) * (255 - dst[1]) / 255) - mGreen) / 255;
                            result[2] = mBlue + src[2] * (255 - ((255 - src[2]) * (255 - dst[2]) / 255) - mBlue) / 255;
                            result[3] = Math.min(255, src[3] + dst[3] - (src[3] * dst[3]) / 255);
                        }
                    };
                case STAMP:
                    return new BlendComposite.Blender() {
                        @Override
                        public void blend(int[] src, int[] dst, int[] result) {
                            result[0] = Math.max(0, Math.min(255, dst[0] + 2 * src[0] - 256));
                            result[1] = Math.max(0, Math.min(255, dst[1] + 2 * src[1] - 256));
                            result[2] = Math.max(0, Math.min(255, dst[2] + 2 * src[2] - 256));
                            result[3] = Math.min(255, src[3] + dst[3] - (src[3] * dst[3]) / 255);
                        }
                    };
                case SUBTRACT:
                    return new BlendComposite.Blender() {
                        @Override
                        public void blend(int[] src, int[] dst, int[] result) {
                            result[0] = Math.max(0, src[0] + dst[0] - 256);
                            result[1] = Math.max(0, src[1] + dst[1] - 256);
                            result[2] = Math.max(0, src[2] + dst[2] - 256);
                            result[3] = Math.min(255, src[3] + dst[3] - (src[3] * dst[3]) / 255);
                        }
                    };
            }
            throw new IllegalArgumentException("Blender not implemented for " +
                    composite.getMode().name());
        }
    }

    private static final class ColorUtils {

        public static float[] RGBtoHSL(java.awt.Color color) {
            return RGBtoHSL(color.getRed(), color.getGreen(), color.getBlue(), null);
        }

        public static float[] RGBtoHSL(Color color, float[] hsl) {
            return RGBtoHSL(color.getRed(), color.getGreen(), color.getBlue(), hsl);
        }

        public static float[] RGBtoHSL(int r, int g, int b) {
            return RGBtoHSL(r, g, b, null);
        }

        public static float[] RGBtoHSL(int r, int g, int b, float[] hsl) {
            if (hsl == null) {
                hsl = new float[3];
            } else if (hsl.length < 3) {
                throw new IllegalArgumentException("hsl array must have a length of" +
                        " at least 3");
            }

            if (r < 0) {
                r = 0;
            } else if (r > 255) r = 255;
            if (g < 0) {
                g = 0;
            } else if (g > 255) g = 255;
            if (b < 0) {
                b = 0;
            } else if (b > 255) b = 255;

            float var_R = (r / 255f);
            float var_G = (g / 255f);
            float var_B = (b / 255f);

            float var_Min;
            float var_Max;
            float del_Max;

            if (var_R > var_G) {
                var_Min = var_G;
                var_Max = var_R;
            } else {
                var_Min = var_R;
                var_Max = var_G;
            }
            if (var_B > var_Max) {
                var_Max = var_B;
            }
            if (var_B < var_Min) {
                var_Min = var_B;
            }

            del_Max = var_Max - var_Min;

            float H, S, L;
            L = (var_Max + var_Min) / 2f;

            if (del_Max - 0.01f <= 0.0f) {
                H = 0;
                S = 0;
            } else {
                if (L < 0.5f) {
                    S = del_Max / (var_Max + var_Min);
                } else {
                    S = del_Max / (2 - var_Max - var_Min);
                }

                float del_R = (((var_Max - var_R) / 6f) + (del_Max / 2f)) / del_Max;
                float del_G = (((var_Max - var_G) / 6f) + (del_Max / 2f)) / del_Max;
                float del_B = (((var_Max - var_B) / 6f) + (del_Max / 2f)) / del_Max;

                if (var_R == var_Max) {
                    H = del_B - del_G;
                } else if (var_G == var_Max) {
                    H = (1 / 3f) + del_R - del_B;
                } else {
                    H = (2 / 3f) + del_G - del_R;
                }
                if (H < 0) {
                    H += 1;
                }
                if (H > 1) {
                    H -= 1;
                }
            }

            hsl[0] = H;
            hsl[1] = S;
            hsl[2] = L;

            return hsl;
        }

        public static Color HSLtoRGB(float h, float s, float l) {
            int[] rgb = HSLtoRGB(h, s, l, null);
            return new Color(rgb[0], rgb[1], rgb[2]);
        }

        public static int[] HSLtoRGB(float h, float s, float l, int[] rgb) {
            if (rgb == null) {
                rgb = new int[3];
            } else if (rgb.length < 3) {
                throw new IllegalArgumentException("rgb array must have a length of" +
                        " at least 3");
            }

            if (h < 0) {
                h = 0.0f;
            } else if (h > 1.0f) h = 1.0f;
            if (s < 0) {
                s = 0.0f;
            } else if (s > 1.0f) s = 1.0f;
            if (l < 0) {
                l = 0.0f;
            } else if (l > 1.0f) l = 1.0f;

            int R, G, B;

            if (s - 0.01f <= 0.0f) {
                R = (int) (l * 255.0f);
                G = (int) (l * 255.0f);
                B = (int) (l * 255.0f);
            } else {
                float var_1, var_2;
                if (l < 0.5f) {
                    var_2 = l * (1 + s);
                } else {
                    var_2 = (l + s) - (s * l);
                }
                var_1 = 2 * l - var_2;

                R = (int) (255.0f * hue2RGB(var_1, var_2, h + (1.0f / 3.0f)));
                G = (int) (255.0f * hue2RGB(var_1, var_2, h));
                B = (int) (255.0f * hue2RGB(var_1, var_2, h - (1.0f / 3.0f)));
            }

            rgb[0] = R;
            rgb[1] = G;
            rgb[2] = B;

            return rgb;
        }

        private static float hue2RGB(float v1, float v2, float vH) {
            if (vH < 0.0f) {
                vH += 1.0f;
            }
            if (vH > 1.0f) {
                vH -= 1.0f;
            }
            if ((6.0f * vH) < 1.0f) {
                return (v1 + (v2 - v1) * 6.0f * vH);
            }
            if ((2.0f * vH) < 1.0f) {
                return (v2);
            }
            if ((3.0f * vH) < 2.0f) {
                return (v1 + (v2 - v1) * ((2.0f / 3.0f) - vH) * 6.0f);
            }
            return (v1);
        }
    }
}