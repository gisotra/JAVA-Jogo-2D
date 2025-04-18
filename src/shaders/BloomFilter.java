package shaders;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BloomFilter {
    private static final int BLUR_RADIUS = 4; // um pouco maior, sem pesar demais
    private static final float BLOOM_INTENSITY = 0.5f; // mais visível

    public static BufferedImage applyBloom(BufferedImage image) {
        BufferedImage brightPass = extractBrightAreas(image);
        BufferedImage blurred = horizontalBlur(brightPass, BLUR_RADIUS);
        return combine(image, blurred, BLOOM_INTENSITY);
    }

    private static BufferedImage extractBrightAreas(BufferedImage image) {
        int w = image.getWidth();
        int h = image.getHeight();
        BufferedImage bright = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                int rgb = image.getRGB(x, y);
                int r = (rgb >> 16) & 0xFF;
                int g = (rgb >> 8) & 0xFF;
                int b = rgb & 0xFF;
                int brightness = (r + g + b) / 3;

                // pega mais brilho (reduz o limiar)
                if (brightness > 140) {
                    bright.setRGB(x, y, new Color(r, g, b, 255).getRGB());
                } else {
                    bright.setRGB(x, y, 0);
                }
            }
        }

        return bright;
    }

    private static BufferedImage horizontalBlur(BufferedImage image, int radius) {
        int w = image.getWidth();
        int h = image.getHeight();
        BufferedImage result = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                int r = 0, g = 0, b = 0, a = 0, count = 0;

                for (int dx = -radius; dx <= radius; dx++) {
                    int nx = x + dx;
                    if (nx >= 0 && nx < w) {
                        int rgb = image.getRGB(nx, y);
                        a += (rgb >> 24) & 0xFF;
                        r += (rgb >> 16) & 0xFF;
                        g += (rgb >> 8) & 0xFF;
                        b += rgb & 0xFF;
                        count++;
                    }
                }

                int avgA = a / count;
                int avgR = r / count;
                int avgG = g / count;
                int avgB = b / count;

                result.setRGB(x, y, (avgA << 24) | (avgR << 16) | (avgG << 8) | avgB);
            }
        }

        return result;
    }

    private static BufferedImage combine(BufferedImage original, BufferedImage glow, float intensity) {
        int w = original.getWidth();
        int h = original.getHeight();
        BufferedImage result = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = result.createGraphics();

        g.drawImage(original, 0, 0, null);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, intensity));
        g.drawImage(glow, 0, 0, null);
        g.dispose();

        return result;
    }
}
