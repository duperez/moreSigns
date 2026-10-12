package duperez.moresigns.common.sign;

/**
 * Picks the default text color of a sign from the color of its board. Free of Minecraft types so it can be unit tested.
 * <p>
 * Signs never draw text at its full color: black is drawn black, but white is darkened to about 40%
 * ({@value #RENDERED_WHITE} of 255). The choice therefore compares the contrast of black text and of that
 * dimmed white against the board, rather than assuming pure white.
 */
public final class TextContrast {
    private static final int RENDERED_WHITE = 102;

    private TextContrast() {
    }

    /** The color a texture of average color {@code rgb} has once multiplied by a tint, both as 0xRRGGBB. */
    public static int tinted(int rgb, int tintRgb) {
        int r = (rgb >> 16 & 0xFF) * (tintRgb >> 16 & 0xFF) / 255;
        int g = (rgb >> 8 & 0xFF) * (tintRgb >> 8 & 0xFF) / 255;
        int b = (rgb & 0xFF) * (tintRgb & 0xFF) / 255;
        return r << 16 | g << 8 | b;
    }

    /** @param boardRgb the board's color as 0xRRGGBB. */
    public static boolean prefersWhiteText(int boardRgb) {
        double board = luminance(boardRgb);
        double black = contrast(board, 0.0);
        double white = contrast(board, luminance(RENDERED_WHITE << 16 | RENDERED_WHITE << 8 | RENDERED_WHITE));
        return white > black;
    }

    /** WCAG relative luminance. */
    static double luminance(int rgb) {
        return 0.2126 * linear(rgb >> 16 & 0xFF) + 0.7152 * linear(rgb >> 8 & 0xFF) + 0.0722 * linear(rgb & 0xFF);
    }

    private static double linear(int channel) {
        double c = channel / 255.0;
        return c <= 0.03928 ? c / 12.92 : Math.pow((c + 0.055) / 1.055, 2.4);
    }

    private static double contrast(double a, double b) {
        return (Math.max(a, b) + 0.05) / (Math.min(a, b) + 0.05);
    }
}
