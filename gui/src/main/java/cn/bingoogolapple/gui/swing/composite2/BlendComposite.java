package cn.bingoogolapple.gui.swing.composite2;

import java.awt.Composite;
import java.awt.CompositeContext;
import java.awt.RenderingHints;
import java.awt.image.ColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

public final class BlendComposite implements Composite {
    public enum BlendingMode {
        MULTIPLY(),
        SCREEN(),
        DARKEN(),
        LIGHTEN(),
        OVERLAY(),
        ADD();
        private final BlendComposite mComposite;

        BlendingMode() {
            mComposite = new BlendComposite(this);
        }

        BlendComposite getBlendComposite() {
            return mComposite;
        }
    }

    public static final BlendComposite Darken = new BlendComposite(BlendComposite.BlendingMode.DARKEN);
    public static final BlendComposite Lighten = new BlendComposite(BlendComposite.BlendingMode.LIGHTEN);
    public static final BlendComposite Multiply = new BlendComposite(BlendComposite.BlendingMode.MULTIPLY);
    public static final BlendComposite Screen = new BlendComposite(BlendComposite.BlendingMode.SCREEN);
    public static final BlendComposite Add = new BlendComposite(BlendComposite.BlendingMode.ADD);
    public static final BlendComposite Overlay = new BlendComposite(BlendComposite.BlendingMode.OVERLAY);

    private float alpha;
    private BlendingMode mode;

    private BlendComposite(BlendingMode mode) {
        this(mode, 1.0f);
    }

    private BlendComposite(BlendingMode mode, float alpha) {
        this.mode = mode;
        setAlpha(alpha);
    }

    public static BlendComposite getInstance(BlendingMode mode) {
        return mode.getBlendComposite();
    }

    public static BlendComposite getInstance(BlendingMode mode, float alpha) {
        if (alpha > 0.9999f) {
            return getInstance(mode);
        }
        return new BlendComposite(mode, alpha);
    }

    public float getAlpha() {
        return alpha;
    }

    public BlendingMode getMode() {
        return mode;
    }

    private void setAlpha(float alpha) {
        if (alpha < 0.0f || alpha > 1.0f) {
            assert false : "alpha must be comprised between 0.0f and 1.0f";
            alpha = Math.min(alpha, 1.0f);
            alpha = Math.max(alpha, 0.0f);
        }
        this.alpha = alpha;
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

    public CompositeContext createContext(ColorModel srcColorModel,
            ColorModel dstColorModel,
            RenderingHints hints) {
        return new BlendingContext(this);
    }

    private static final class BlendingContext implements CompositeContext {
        private final Blender blender;
        private final BlendComposite composite;

        private BlendingContext(BlendComposite composite) {
            this.composite = composite;
            this.blender = Blender.getBlenderFor(composite);
        }

        public void dispose() {
        }

        public void compose(Raster src, Raster dstIn, WritableRaster dstOut) {
            if (src.getSampleModel().getDataType() != DataBuffer.TYPE_INT ||
                    dstIn.getSampleModel().getDataType() != DataBuffer.TYPE_INT ||
                    dstOut.getSampleModel().getDataType() != DataBuffer.TYPE_INT) {
                throw new IllegalStateException(
                        "Source and destination must store pixels as INT.");
            }
            int width = Math.min(src.getWidth(), dstIn.getWidth());
            int height = Math.min(src.getHeight(), dstIn.getHeight());
            float alpha = composite.getAlpha();
            int[] srcPixel = new int[4];
            int[] dstPixel = new int[4];
            int[] result = new int[4];
            int[] srcPixels = new int[width];
            int[] dstPixels = new int[width];
            for (int y = 0; y < height; y++) {
                dstIn.getDataElements(0, y, width, 1, dstPixels);
                if (alpha != 0) {
                    src.getDataElements(0, y, width, 1, srcPixels);
                    for (int x = 0; x < width; x++) {
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
                        srcPixel[3] *= dstPixel[3] / 0xFF;
                        result = blender.blend(srcPixel, dstPixel, result);
                        if (alpha == 1) {
                            dstPixels[x] = (result[3] & 0xFF) << 24 |
                                    (result[0] & 0xFF) << 16 |
                                    (result[1] & 0xFF) << 8 |
                                    result[2] & 0xFF;
                        } else {
                            dstPixels[x] =
                                    ((int) (dstPixel[3] + (result[3] - dstPixel[3]) * alpha) & 0xFF) << 24 |
                                            ((int) (dstPixel[0] + (result[0] - dstPixel[0]) * alpha) & 0xFF) << 16 |
                                            ((int) (dstPixel[1] + (result[1] - dstPixel[1]) * alpha) & 0xFF) << 8 |
                                            (int) (dstPixel[2] + (result[2] - dstPixel[2]) * alpha) & 0xFF;
                        }
                    }
                }
                dstOut.setDataElements(0, y, width, 1, dstPixels);
            }
        }
    }

    private static abstract class Blender {
        public abstract int[] blend(int[] src, int[] dst, int[] result);

        public static Blender getBlenderFor(BlendComposite composite) {
            switch (composite.getMode()) {
                case ADD:
                    return new Blender() {
                        @Override
                        public int[] blend(int[] src, int[] dst, int[] result) {
                            for (int i = 0; i < 4; i++) {
                                result[i] = Math.min(255, src[i] + dst[i]);
                            }
                            return result;
                        }
                    };
                case DARKEN:
                    return new Blender() {
                        @Override
                        public int[] blend(int[] src, int[] dst, int[] result) {
                            for (int i = 0; i < 3; i++) {
                                result[i] = Math.min(src[i], dst[i]);
                            }
                            result[3] = Math.min(255, src[3] + dst[3]);
                            return result;
                        }
                    };
                case LIGHTEN:
                    return new Blender() {
                        @Override
                        public int[] blend(int[] src, int[] dst, int[] result) {
                            for (int i = 0; i < 3; i++) {
                                result[i] = Math.max(src[i], dst[i]);
                            }
                            result[3] = Math.min(255, src[3] + dst[3]);
                            return result;
                        }
                    };
                case MULTIPLY:
                    return new Blender() {
                        @Override
                        public int[] blend(int[] src, int[] dst, int[] result) {
                            for (int i = 0; i < 3; i++) {
                                result[i] = (src[i] * dst[i]) >> 8;
                            }
                            result[3] = Math.min(255, src[3] + dst[3] - (src[3] * dst[3]) / 255);
                            return result;
                        }
                    };
                case OVERLAY:
                    return new Blender() {
                        @Override
                        public int[] blend(int[] src, int[] dst, int[] result) {
                            for (int i = 0; i < 3; i++) {
                                result[i] = dst[i] < 128 ? dst[i] * src[i] >> 7 :
                                        255 - ((255 - dst[i]) * (255 - src[i]) >> 7);
                            }
                            result[3] = Math.min(255, src[3] + dst[3]);
                            return result;
                        }
                    };
                case SCREEN:
                    return new Blender() {
                        @Override
                        public int[] blend(int[] src, int[] dst, int[] result) {
                            result[0] = 255 - ((255 - src[0]) * (255 - dst[0]) >> 8);
                            result[1] = 255 - ((255 - src[1]) * (255 - dst[1]) >> 8);
                            result[2] = 255 - ((255 - src[2]) * (255 - dst[2]) >> 8);
                            result[3] = Math.min(255, src[3] + dst[3]);
                            return result;
                        }
                    };
                default:
                    assert false : "Blender not implement for " + composite.getMode().name();
                    return new Blender() {
                        @Override
                        public int[] blend(int[] src, int[] dst, int[] result) {
                            result[0] = dst[0];
                            result[1] = dst[1];
                            result[2] = dst[2];
                            result[3] = dst[3];
                            return result;
                        }
                    };
            }
        }
    }
}
