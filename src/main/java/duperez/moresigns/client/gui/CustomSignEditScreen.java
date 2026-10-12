package duperez.moresigns.client.gui;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import duperez.moresigns.client.renderer.CustomSignModel;
import duperez.moresigns.common.block.entity.CustomSignBlockEntity;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.font.TextFieldHelper;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.game.ServerboundSignUpdatePacket;
import net.minecraft.world.level.block.StandingSignBlock;
import org.lwjgl.glfw.GLFW;

import java.util.Optional;
import java.util.stream.IntStream;

/**
 * The sign editor for a custom sign. This version of the game draws the sign inside the editor's own render method, so the vanilla
 * screen cannot be asked to draw another model; this is the same editor (same keys, same packet), drawing the sign as it looks in the world.
 */
public class CustomSignEditScreen extends Screen {
    /** Pixels per block. Vanilla draws its model, a block wide, at 93.75 pixels per block. */
    private static final float SCALE = 93.75F;
    /** Where the middle of the plate sits on the screen, from the top. */
    private static final float PLATE_CENTER_Y = 91.8F;
    private static final int MAX_LINE_WIDTH = 90;
    private static final int LINE_HEIGHT = 10;
    private static final float TEXT_SCALE = 0.9765628F * CustomSignModel.TEXT_SHRINK;

    private final CustomSignBlockEntity sign;
    private final String[] messages;
    private int frame;
    private int line;
    private TextFieldHelper signField;

    public CustomSignEditScreen(CustomSignBlockEntity sign, boolean filteringEnabled) {
        super(new TranslatableComponent("sign.edit"));
        this.sign = sign;
        this.messages = IntStream.range(0, 4).mapToObj(i -> sign.getMessage(i, filteringEnabled)).map(component -> component.getString()).toArray(String[]::new);
    }

    @Override
    protected void init() {
        minecraft.keyboardHandler.setSendRepeatsToGui(true);
        addRenderableWidget(new Button(width / 2 - 100, height / 4 + 120, 200, 20, CommonComponents.GUI_DONE, button -> onDone()));
        sign.setEditable(false);
        signField = new TextFieldHelper(() -> messages[line], text -> {
            messages[line] = text;
            sign.setMessage(line, new TextComponent(text));
        }, TextFieldHelper.createClipboardGetter(minecraft), TextFieldHelper.createClipboardSetter(minecraft),
                text -> minecraft.font.width(text) <= MAX_LINE_WIDTH);
    }

    @Override
    public void removed() {
        minecraft.keyboardHandler.setSendRepeatsToGui(false);
        ClientPacketListener connection = minecraft.getConnection();
        if (connection != null) {
            connection.send(new ServerboundSignUpdatePacket(sign.getBlockPos(), messages[0], messages[1], messages[2], messages[3]));
        }
        sign.setEditable(true);
    }

    @Override
    public void tick() {
        frame++;
        if (!sign.getType().isValid(sign.getBlockState())) {
            onDone();
        }
    }

    private void onDone() {
        sign.setChanged();
        minecraft.setScreen(null);
    }

    @Override
    public boolean charTyped(char character, int modifiers) {
        signField.charTyped(character);
        return true;
    }

    @Override
    public void onClose() {
        onDone();
    }

    @Override
    public boolean keyPressed(int key, int scanCode, int modifiers) {
        if (key == GLFW.GLFW_KEY_UP) {
            line = (line - 1) & 3;
            signField.setCursorToEnd();
            return true;
        }
        if (key == GLFW.GLFW_KEY_DOWN || key == GLFW.GLFW_KEY_ENTER || key == GLFW.GLFW_KEY_KP_ENTER) {
            line = (line + 1) & 3;
            signField.setCursorToEnd();
            return true;
        }
        return signField.keyPressed(key) || super.keyPressed(key, scanCode, modifiers);
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        Lighting.setupForFlatItems();
        renderBackground(poseStack);
        drawCenteredString(poseStack, font, title, width / 2, 40, 0xFFFFFF);

        MultiBufferSource.BufferSource buffer = minecraft.renderBuffers().bufferSource();
        boolean standing = sign.getBlockState().getBlock() instanceof StandingSignBlock;

        poseStack.pushPose();
        poseStack.translate(width / 2.0, PLATE_CENTER_Y, 50.0);
        poseStack.pushPose();
        poseStack.scale(SCALE, -SCALE, SCALE);
        poseStack.translate(-0.5F, -CustomSignModel.PLATE_CENTER_Y, -0.5F);
        VertexConsumer consumer = buffer.getBuffer(CustomSignModel.RENDER_TYPE);
        CustomSignModel.render(consumer, poseStack.last(), Optional.of(sign.parts().frame()), Optional.of(sign.parts().board()),
                standing, 15728880, OverlayTexture.NO_OVERLAY);
        poseStack.popPose();

        poseStack.translate(0.0, 0.0, 4.0);
        poseStack.scale(TEXT_SCALE, TEXT_SCALE, TEXT_SCALE);
        int color = sign.getColor().getTextColor();
        boolean cursorShown = frame / 6 % 2 == 0;
        int cursor = signField.getCursorPos();
        int selection = signField.getSelectionPos();
        int cursorY = line * LINE_HEIGHT - messages.length * 5;
        Matrix4f matrix = poseStack.last().pose();

        for (int i = 0; i < messages.length; i++) {
            String text = messages[i];
            if (text == null) {
                continue;
            }
            if (font.isBidirectional()) {
                text = font.bidirectionalShaping(text);
            }
            float x = -font.width(text) / 2.0F;
            // The text is already shaped for right-to-left languages just above, so the font is told not to do it again.
            font.drawInBatch(text, x, i * LINE_HEIGHT - messages.length * 5, color, false, matrix, buffer, false, 0, 15728880, false);
            if (i == line && cursor >= 0 && cursorShown && cursor >= text.length()) {
                int cursorX = font.width(text.substring(0, Math.max(Math.min(cursor, text.length()), 0))) - font.width(text) / 2;
                font.drawInBatch("_", cursorX, cursorY, color, false, matrix, buffer, false, 0, 15728880, false);
            }
        }
        buffer.endBatch();

        for (int i = 0; i < messages.length; i++) {
            String text = messages[i];
            if (text == null || i != line || cursor < 0) {
                continue;
            }
            int cursorX = font.width(text.substring(0, Math.max(Math.min(cursor, text.length()), 0))) - font.width(text) / 2;
            if (cursorShown && cursor < text.length()) {
                fill(poseStack, cursorX, cursorY - 1, cursorX + 1, cursorY + LINE_HEIGHT - 1, 0xFF000000 | color);
            }
            if (selection != cursor) {
                int from = Math.min(cursor, selection);
                int to = Math.max(cursor, selection);
                int fromX = font.width(text.substring(0, from)) - font.width(text) / 2;
                int toX = font.width(text.substring(0, to)) - font.width(text) / 2;
                fill(poseStack, Math.min(fromX, toX), cursorY, Math.max(fromX, toX), cursorY + LINE_HEIGHT, 0x663399FF);
            }
        }

        poseStack.popPose();
        Lighting.setupFor3DItems();
        super.render(poseStack, mouseX, mouseY, partialTick);
    }
}
