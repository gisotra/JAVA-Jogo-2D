package shaders;

import java.awt.*;
import java.awt.image.BufferedImage;

public class AtmosphereShader {

    private static final float VIGNETTE_STRENGTH = 0.3f; // Menos forte que antes
    private static final float GLOW_INTENSITY = 0.6f;   // Intensidade do glow (ajustável)
    private static final int GLOW_THRESHOLD = 180;      // Limite de brilho para aplicar glow

    // Aplica o efeito de atmosfera no BufferedImage
    public static BufferedImage applyEffect(BufferedImage image) {
        BufferedImage vignette = applyVignette(image);
        BufferedImage glow = extractGlow(image);
        return blendGlow(vignette, glow, GLOW_INTENSITY);
    }

    // Aplica um efeito de vignette nas bordas
    private static BufferedImage applyVignette(BufferedImage image) {
        int w = image.getWidth();
        int h = image.getHeight();
        int cx = w / 2, cy = h / 2;
        float maxDist = (float) Math.sqrt(cx * cx + cy * cy); // Distância máxima até o centro

        BufferedImage result = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                Color c = new Color(image.getRGB(x, y), true);
                float dx = x - cx;
                float dy = y - cy;
                float dist = (float) Math.sqrt(dx * dx + dy * dy);
                float vignette = 1f - (dist / maxDist) * VIGNETTE_STRENGTH;
                vignette = Math.max(0, vignette); // Garante que o valor do vignette não seja negativo

                int r = (int) (c.getRed() * vignette);
                int g = (int) (c.getGreen() * vignette);
                int b = (int) (c.getBlue() * vignette);
                result.setRGB(x, y, new Color(r, g, b, c.getAlpha()).getRGB());
            }
        }

        return result;
    }

    // Extrai o brilho da imagem (áreas claras)
    private static BufferedImage extractGlow(BufferedImage image) {
        int w = image.getWidth();
        int h = image.getHeight();
        BufferedImage result = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                Color c = new Color(image.getRGB(x, y), true);
                int brightness = (c.getRed() + c.getGreen() + c.getBlue()) / 3;

                // Extrai apenas as áreas mais brilhantes
                if (brightness > GLOW_THRESHOLD) {
                    result.setRGB(x, y, new Color(c.getRed(), c.getGreen(), c.getBlue(), 255).getRGB());
                } else {
                    result.setRGB(x, y, new Color(0, 0, 0, 0).getRGB()); // Transparente
                }
            }
        }

        return result;
    }

    // Aplica o glow extra à imagem
    private static BufferedImage blendGlow(BufferedImage base, BufferedImage glow, float intensity) {
        int w = base.getWidth();
        int h = base.getHeight();
        BufferedImage result = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                Color baseColor = new Color(base.getRGB(x, y), true);
                Color glowColor = new Color(glow.getRGB(x, y), true);

                int r = Math.min((int) (baseColor.getRed() + glowColor.getRed() * intensity), 255);
                int g = Math.min((int) (baseColor.getGreen() + glowColor.getGreen() * intensity), 255);
                int b = Math.min((int) (baseColor.getBlue() + glowColor.getBlue() * intensity), 255);

                result.setRGB(x, y, new Color(r, g, b, baseColor.getAlpha()).getRGB());
            }
        }

        return result;
    }
}
