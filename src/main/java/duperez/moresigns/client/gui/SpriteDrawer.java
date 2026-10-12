package duperez.moresigns.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import duperez.moresigns.client.gui.WorkbenchSprites.Sprite;
import net.minecraft.client.gui.GuiGraphics;

/** Draws parts of the workbench's sprite sheet. This version of Minecraft has no GUI sprite system, so the sheet is blitted by hand. */
final class SpriteDrawer {
    private SpriteDrawer() {
    }

    static void draw(GuiGraphics graphics, Sprite sprite, int x, int y) {
        draw(graphics, sprite, x, y, sprite.width(), sprite.height());
    }

    /** Draws the sprite stretched to the given size. */
    static void draw(GuiGraphics graphics, Sprite sprite, int x, int y, int width, int height) {
        graphics.blit(WorkbenchSprites.TEXTURE, x, y, width, height, sprite.u(), sprite.v(), sprite.width(), sprite.height(),
                WorkbenchSprites.SHEET_SIZE, WorkbenchSprites.SHEET_SIZE);
    }

    /** Draws the sprite faintly, as the picture in an empty slot. */
    static void drawFaint(GuiGraphics graphics, Sprite sprite, int x, int y, int size, float alpha) {
        RenderSystem.enableBlend();
        graphics.setColor(1.0F, 1.0F, 1.0F, alpha);
        draw(graphics, sprite, x, y, size, size);
        graphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
    }

    /** Stretches a sprite to any size keeping its corners, each {@code border} pixels, undistorted. */
    static void drawNineSlice(GuiGraphics graphics, Sprite sprite, int border, int x, int y, int width, int height) {
        int innerWidth = width - 2 * border;
        int innerHeight = height - 2 * border;
        int spriteInnerWidth = sprite.width() - 2 * border;
        int spriteInnerHeight = sprite.height() - 2 * border;
        int u = sprite.u();
        int v = sprite.v();
        int right = u + sprite.width() - border;
        int bottom = v + sprite.height() - border;

        piece(graphics, x, y, border, border, u, v, border, border);
        piece(graphics, x + width - border, y, border, border, right, v, border, border);
        piece(graphics, x, y + height - border, border, border, u, bottom, border, border);
        piece(graphics, x + width - border, y + height - border, border, border, right, bottom, border, border);

        piece(graphics, x + border, y, innerWidth, border, u + border, v, spriteInnerWidth, border);
        piece(graphics, x + border, y + height - border, innerWidth, border, u + border, bottom, spriteInnerWidth, border);
        piece(graphics, x, y + border, border, innerHeight, u, v + border, border, spriteInnerHeight);
        piece(graphics, x + width - border, y + border, border, innerHeight, right, v + border, border, spriteInnerHeight);

        piece(graphics, x + border, y + border, innerWidth, innerHeight, u + border, v + border, spriteInnerWidth, spriteInnerHeight);
    }

    private static void piece(GuiGraphics graphics, int x, int y, int width, int height, int u, int v, int uWidth, int vHeight) {
        graphics.blit(WorkbenchSprites.TEXTURE, x, y, width, height, u, v, uWidth, vHeight,
                WorkbenchSprites.SHEET_SIZE, WorkbenchSprites.SHEET_SIZE);
    }
}
