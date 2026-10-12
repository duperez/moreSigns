package duperez.moresigns.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import duperez.moresigns.client.gui.WorkbenchSprites.Sprite;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;

/** Draws parts of the workbench's sprite sheet. This version of Minecraft has no GUI sprite system, so the sheet is blitted by hand. */
final class SpriteDrawer {
    private SpriteDrawer() {
    }

    static void draw(PoseStack poseStack, Sprite sprite, int x, int y) {
        draw(poseStack, sprite, x, y, sprite.width(), sprite.height());
    }

    /** Draws the sprite stretched to the given size. */
    static void draw(PoseStack poseStack, Sprite sprite, int x, int y, int width, int height) {
        bind();
        GuiComponent.blit(poseStack, x, y, width, height, sprite.u(), sprite.v(), sprite.width(), sprite.height(),
                WorkbenchSprites.SHEET_SIZE, WorkbenchSprites.SHEET_SIZE);
    }

    /** Draws the sprite faintly, as the picture in an empty slot. */
    static void drawFaint(PoseStack poseStack, Sprite sprite, int x, int y, int size, float alpha) {
        RenderSystem.enableBlend();
        bind();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, alpha);
        GuiComponent.blit(poseStack, x, y, size, size, sprite.u(), sprite.v(), sprite.width(), sprite.height(),
                WorkbenchSprites.SHEET_SIZE, WorkbenchSprites.SHEET_SIZE);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    }

    /** Stretches a sprite to any size keeping its corners, each {@code border} pixels, undistorted. */
    static void drawNineSlice(PoseStack poseStack, Sprite sprite, int border, int x, int y, int width, int height) {
        bind();
        int innerWidth = width - 2 * border;
        int innerHeight = height - 2 * border;
        int spriteInnerWidth = sprite.width() - 2 * border;
        int spriteInnerHeight = sprite.height() - 2 * border;
        int u = sprite.u();
        int v = sprite.v();
        int right = u + sprite.width() - border;
        int bottom = v + sprite.height() - border;

        piece(poseStack, x, y, border, border, u, v, border, border);
        piece(poseStack, x + width - border, y, border, border, right, v, border, border);
        piece(poseStack, x, y + height - border, border, border, u, bottom, border, border);
        piece(poseStack, x + width - border, y + height - border, border, border, right, bottom, border, border);

        piece(poseStack, x + border, y, innerWidth, border, u + border, v, spriteInnerWidth, border);
        piece(poseStack, x + border, y + height - border, innerWidth, border, u + border, bottom, spriteInnerWidth, border);
        piece(poseStack, x, y + border, border, innerHeight, u, v + border, border, spriteInnerHeight);
        piece(poseStack, x + width - border, y + border, border, innerHeight, right, v + border, border, spriteInnerHeight);

        piece(poseStack, x + border, y + border, innerWidth, innerHeight, u + border, v + border, spriteInnerWidth, spriteInnerHeight);
    }

    private static void bind() {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, WorkbenchSprites.TEXTURE);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    }

    private static void piece(PoseStack poseStack, int x, int y, int width, int height, int u, int v, int uWidth, int vHeight) {
        GuiComponent.blit(poseStack, x, y, width, height, (float) u, (float) v, uWidth, vHeight,
                WorkbenchSprites.SHEET_SIZE, WorkbenchSprites.SHEET_SIZE);
    }
}
