package duperez.moresigns.client.gui;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import duperez.moresigns.MoreSigns;
import duperez.moresigns.client.renderer.CustomSignModel;
import duperez.moresigns.common.inventory.SignWorkbenchLogic;
import duperez.moresigns.common.inventory.SignWorkbenchLogic.Mode;
import duperez.moresigns.common.inventory.SignWorkbenchMenu;
import duperez.moresigns.common.item.PartItem;
import duperez.moresigns.common.registry.ModItems;
import duperez.moresigns.common.sign.Part;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The sign workbench. Four tabs pick what to do; the sign being made turns slowly in the middle, so the player sees
 * the result before taking it. See docs/workbench-ui-design.md for the reasoning behind the layout.
 */
public class SignWorkbenchScreen extends AbstractContainerScreen<SignWorkbenchMenu> {
    private static final ResourceLocation SLOT = ResourceLocation.withDefaultNamespace("container/slot");
    private static final ResourceLocation PANEL = sprite("panel");
    private static final ResourceLocation PREVIEW = sprite("preview");
    private static final ResourceLocation OUTPUT = sprite("output");
    private static final ResourceLocation ARROW = sprite("arrow");
    private static final ResourceLocation ARROW_READY = sprite("arrow_ready");
    private static final ResourceLocation HINT_MATERIAL = sprite("hint_material");
    private static final ResourceLocation HINT_DYE = sprite("hint_dye");
    private static final ResourceLocation HINT_FRAME = sprite("hint_frame");
    private static final ResourceLocation HINT_BOARD = sprite("hint_board");
    private static final ResourceLocation HINT_SIGN = sprite("hint_sign");
    private static final ResourceLocation ICON_WASH = sprite("icon_wash");

    private static final int WIDTH = 176;
    private static final int HEIGHT = 174;

    /** The tabs stand on the left edge of the panel, so they add to the window's width, which there is more of, and not its height. */
    private static final int TAB_WIDTH = 30;
    private static final int TAB_HEIGHT = 28;
    private static final int TAB_VISIBLE = 26;
    private static final int TAB_GAP = 2;
    private static final int TAB_TOP = 8;

    private static final int PREVIEW_X = 56, PREVIEW_Y = 16, PREVIEW_SIZE = 52;
    private static final int ARROW_X = 110, ARROW_Y = 35;
    private static final int BUTTON_SIZE = 22;
    private static final int MAKE_FRAME_X = 8, MAKE_BOARD_X = 31, MAKE_BUTTONS_Y = 40;
    private static final int WASH_X = 84, WASH_Y = 44;
    private static final int HINT_Y = 71;

    private static final int LABEL_COLOR = 0x404040;
    private static final int READY_COLOR = 0x2F6B2F;

    private static final List<Tab> TABS = List.of(
            new Tab(Mode.MAKE, sprite("icon_make"), "make"),
            new Tab(Mode.PAINT, sprite("icon_paint"), "paint"),
            new Tab(Mode.ASSEMBLE, sprite("icon_assemble"), "assemble"),
            new Tab(Mode.DISASSEMBLE, sprite("icon_disassemble"), "disassemble"));

    private record Tab(Mode mode, ResourceLocation icon, String key) {
    }

    private ItemStack lastOutput = ItemStack.EMPTY;
    private long outputChangedAt;

    public SignWorkbenchScreen(SignWorkbenchMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
        this.imageWidth = WIDTH;
        this.imageHeight = HEIGHT;
        this.inventoryLabelY = 82;
    }

    private static ResourceLocation sprite(String name) {
        return ResourceLocation.fromNamespaceAndPath(MoreSigns.MODID, "workbench/" + name);
    }

    private static ResourceLocation sprite(String prefix, String state) {
        return sprite(prefix + "_" + state);
    }

    // ------------------------------------------------------------------------------------------ drawing

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        Mode mode = menu.mode();

        // Tabs that are not selected sit behind the panel, so the panel hides their inner edge.
        for (int i = 0; i < TABS.size(); i++) {
            if (TABS.get(i).mode() != mode) {
                boolean hover = isOverTab(i, mouseX, mouseY);
                graphics.blitSprite(sprite("tab", hover ? "hover" : "normal"), leftPos - TAB_VISIBLE, tabY(i), TAB_WIDTH, TAB_HEIGHT);
            }
        }
        graphics.blitSprite(PANEL, leftPos, topPos, WIDTH, HEIGHT);
        for (int i = 0; i < TABS.size(); i++) {
            boolean selected = TABS.get(i).mode() == mode;
            if (selected) {
                graphics.blitSprite(sprite("tab", "selected"), leftPos - TAB_WIDTH + 3, tabY(i), TAB_WIDTH, TAB_HEIGHT);
            }
            graphics.blitSprite(TABS.get(i).icon(), leftPos - (selected ? TAB_WIDTH - 3 : TAB_VISIBLE) + (selected ? 9 : 6), tabY(i) + 6, 16, 16);
        }

        for (Slot slot : menu.slots) {
            if (slot.index == SignWorkbenchMenu.RESULT_SLOT) {
                if (slot.isActive()) {
                    graphics.blitSprite(OUTPUT, leftPos + slot.x - 5, topPos + slot.y - 5, 26, 26);
                }
            } else if (slot.isActive()) {
                graphics.blitSprite(SLOT, leftPos + slot.x - 1, topPos + slot.y - 1, 18, 18);
                if (!slot.hasItem() && slot.index < SignWorkbenchMenu.INVENTORY_START) {
                    drawGhost(graphics, hintFor(slot.index), leftPos + slot.x, topPos + slot.y, 16, 0.55F);
                }
            }
        }

        SignWorkbenchLogic.Result result = menu.currentResult();
        graphics.blitSprite(result.isEmpty() ? ARROW : ARROW_READY, leftPos + ARROW_X, topPos + ARROW_Y, 22, 15);

        renderPreview(graphics, mode, result);

        if (mode == Mode.MAKE) {
            renderMakeButtons(graphics, mouseX, mouseY);
        } else if (mode == Mode.PAINT) {
            renderWashButton(graphics, mouseX, mouseY);
        }
    }

    /** The faint picture in an empty slot, saying what goes there. */
    private ResourceLocation hintFor(int slotIndex) {
        Mode mode = menu.mode();
        return switch (slotIndex) {
            case SignWorkbenchMenu.FIRST_SLOT -> switch (mode) {
                case MAKE -> HINT_MATERIAL;
                case PAINT, ASSEMBLE -> HINT_FRAME;
                case DISASSEMBLE -> HINT_SIGN;
            };
            case SignWorkbenchMenu.SECOND_SLOT -> mode == Mode.PAINT ? HINT_DYE : HINT_BOARD;
            case SignWorkbenchMenu.FRAME_RESULT_SLOT -> HINT_FRAME;
            case SignWorkbenchMenu.BOARD_RESULT_SLOT -> HINT_BOARD;
            default -> mode == Mode.MAKE && menu.selectedKind() == PartItem.Kind.BOARD ? HINT_BOARD : HINT_FRAME;
        };
    }

    private static void drawGhost(GuiGraphics graphics, ResourceLocation sprite, int x, int y, int size, float alpha) {
        RenderSystem.enableBlend();
        graphics.setColor(1.0F, 1.0F, 1.0F, alpha);
        graphics.blitSprite(sprite, x, y, size, size);
        graphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
    }

    private void renderPreview(GuiGraphics graphics, Mode mode, SignWorkbenchLogic.Result result) {
        int x = leftPos + PREVIEW_X;
        int y = topPos + PREVIEW_Y;
        graphics.blitSprite(PREVIEW, x, y, PREVIEW_SIZE, PREVIEW_SIZE);
        // A soft light from above, so the stage is not a flat slab.
        graphics.fillGradient(x + 3, y + 3, x + PREVIEW_SIZE - 3, y + PREVIEW_SIZE - 3, 0x2EFFFFFF, 0x00000000);

        if (!ItemStack.matches(lastOutput, result.output())) {
            lastOutput = result.output().copy();
            outputChangedAt = Util.getMillis();
        }

        if (result.frame().isEmpty() && result.board().isEmpty()) {
            ResourceLocation ghost = mode == Mode.MAKE && menu.selectedKind() == PartItem.Kind.BOARD ? HINT_BOARD
                    : mode == Mode.DISASSEMBLE ? HINT_SIGN : HINT_FRAME;
            drawGhost(graphics, ghost, x + PREVIEW_SIZE / 2 - 16, y + PREVIEW_SIZE / 2 - 16, 32, 0.3F);
            return;
        }

        long age = Util.getMillis() - outputChangedAt;
        float pop = 1.0F + 0.14F * Math.max(0.0F, 1.0F - age / 220.0F);
        float yaw = -24.0F + 16.0F * (float) Math.sin(Util.getMillis() / 750.0);

        graphics.enableScissor(x + 3, y + 3, x + PREVIEW_SIZE - 3, y + PREVIEW_SIZE - 3);
        drawSign(graphics, result.frame(), result.board(), mode == Mode.DISASSEMBLE, x + PREVIEW_SIZE / 2.0F, y + PREVIEW_SIZE / 2.0F + 1, 40.0F * pop, yaw);
        graphics.disableScissor();
    }

    /** Draws a sign, or one of its parts, in 3D at the given place on the screen. */
    private static void drawSign(GuiGraphics graphics, Optional<Part> frame, Optional<Part> board, boolean apart, float centerX, float centerY, float scale, float yawDegrees) {
        PoseStack pose = graphics.pose();
        pose.pushPose();
        pose.translate(centerX, centerY, 100.0F);
        pose.scale(scale, -scale, scale);
        pose.mulPose(Axis.YP.rotationDegrees(yawDegrees));
        // A board on its own is the upper part of the sign, so it is centered on the plate rather than on the whole sign.
        float centerHeight = frame.isEmpty() ? CustomSignModel.PLATE_CENTER_Y : 0.54F;
        pose.translate(-0.5F, -centerHeight, -0.5F);
        Lighting.setupFor3DItems();
        VertexConsumer consumer = graphics.bufferSource().getBuffer(CustomSignModel.RENDER_TYPE);
        if (apart) {
            // An exploded view: the board lifted off the frame, toward the viewer.
            CustomSignModel.render(consumer, pose.last(), frame, Optional.empty(), true, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY);
            pose.translate(0.0F, 0.1F, 0.4F);
            CustomSignModel.render(consumer, pose.last(), Optional.empty(), board, true, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY);
        } else {
            CustomSignModel.render(consumer, pose.last(), frame, board, true, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY);
        }
        graphics.flush();
        Lighting.setupFor3DItems(); // the state the GUI is in for the rest of the frame; flat lighting here would leave the slots' items flat
        pose.popPose();
    }

    private void renderMakeButtons(GuiGraphics graphics, int mouseX, int mouseY) {
        Part shown = Optional.of(menu.getSlot(SignWorkbenchMenu.FIRST_SLOT).getItem())
                .filter(SignWorkbenchLogic::isMaterial)
                .map(stack -> Part.of(BuiltInRegistries.BLOCK.getKey(((BlockItem) stack.getItem()).getBlock())))
                .orElse(Part.DEFAULT);
        drawIconButton(graphics, MAKE_FRAME_X, MAKE_BUTTONS_Y, menu.selectedKind() == PartItem.Kind.FRAME, mouseX, mouseY);
        drawSign(graphics, Optional.of(shown), Optional.empty(), false, leftPos + MAKE_FRAME_X + 11, topPos + MAKE_BUTTONS_Y + 12, 12.0F, -24.0F);
        drawIconButton(graphics, MAKE_BOARD_X, MAKE_BUTTONS_Y, menu.selectedKind() == PartItem.Kind.BOARD, mouseX, mouseY);
        drawSign(graphics, Optional.empty(), Optional.of(shown), false, leftPos + MAKE_BOARD_X + 11, topPos + MAKE_BUTTONS_Y + 11, 12.0F, -24.0F);
    }

    private void renderWashButton(GuiGraphics graphics, int mouseX, int mouseY) {
        // The 3D preview is drawn above the flat layer, so the button sits above both.
        graphics.pose().pushPose();
        graphics.pose().translate(0.0F, 0.0F, 200.0F);
        drawIconButton(graphics, WASH_X, WASH_Y, menu.washing(), mouseX, mouseY);
        graphics.blitSprite(ICON_WASH, leftPos + WASH_X + 3, topPos + WASH_Y + 3, 16, 16);
        graphics.pose().popPose();
    }

    private void drawIconButton(GuiGraphics graphics, int x, int y, boolean selected, int mouseX, int mouseY) {
        boolean hover = isOver(leftPos + x, topPos + y, BUTTON_SIZE, BUTTON_SIZE, mouseX, mouseY);
        String state = selected ? "selected" : hover ? "hover" : "normal";
        graphics.blitSprite(sprite("button", state), leftPos + x, topPos + y, BUTTON_SIZE, BUTTON_SIZE);
    }

    @Override
    protected void renderLabels(GuiGraphics graphics, int mouseX, int mouseY) {
        super.renderLabels(graphics, mouseX, mouseY);

        // One line saying what the bench will do, or what it still needs. Shrunk, rather than wrapped, if it is too long.
        SignWorkbenchLogic.Result result = menu.currentResult();
        FormattedCharSequence line = result.message().getVisualOrderText();
        float scale = Math.min(1.0F, (WIDTH - 12.0F) / Math.max(1, font.width(line)));
        graphics.pose().pushPose();
        graphics.pose().translate(WIDTH / 2.0F, HINT_Y, 0.0F);
        graphics.pose().scale(scale, scale, 1.0F);
        graphics.drawString(font, line, -font.width(line) / 2, 0, result.isEmpty() ? LABEL_COLOR : READY_COLOR, false);
        graphics.pose().popPose();
    }

    // ------------------------------------------------------------------------------------------ input

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        super.render(graphics, mouseX, mouseY, partialTick);
        renderTooltip(graphics, mouseX, mouseY);
        renderControlTooltips(graphics, mouseX, mouseY);
    }

    private void renderControlTooltips(GuiGraphics graphics, int mouseX, int mouseY) {
        for (int i = 0; i < TABS.size(); i++) {
            if (isOverTab(i, mouseX, mouseY)) {
                String key = "moresigns.workbench.tab." + TABS.get(i).key();
                tooltip(graphics, mouseX, mouseY, Component.translatable(key).withStyle(ChatFormatting.WHITE),
                        Component.translatable(key + ".tooltip").withStyle(ChatFormatting.GRAY));
                return;
            }
        }
        Mode mode = menu.mode();
        if (mode == Mode.MAKE) {
            if (isOver(leftPos + MAKE_FRAME_X, topPos + MAKE_BUTTONS_Y, BUTTON_SIZE, BUTTON_SIZE, mouseX, mouseY)) {
                makeTooltip(graphics, mouseX, mouseY, "frame", SignWorkbenchLogic.FRAME_YIELD, SignWorkbenchLogic.FRAME_COST);
            } else if (isOver(leftPos + MAKE_BOARD_X, topPos + MAKE_BUTTONS_Y, BUTTON_SIZE, BUTTON_SIZE, mouseX, mouseY)) {
                makeTooltip(graphics, mouseX, mouseY, "board", SignWorkbenchLogic.BOARD_YIELD, SignWorkbenchLogic.BOARD_COST);
            }
        } else if (mode == Mode.PAINT) {
            if (isOver(leftPos + WASH_X, topPos + WASH_Y, BUTTON_SIZE, BUTTON_SIZE, mouseX, mouseY)) {
                tooltip(graphics, mouseX, mouseY, Component.translatable(menu.washing()
                        ? "moresigns.workbench.button.wash.on" : "moresigns.workbench.button.wash").withStyle(ChatFormatting.WHITE));
            }
        }
        if (hoveredSlot != null && !hoveredSlot.hasItem() && hoveredSlot.isActive() && hoveredSlot.index < SignWorkbenchMenu.INVENTORY_START) {
            slotTooltip(graphics, mouseX, mouseY, hoveredSlot.index);
        }
    }

    /** Names an empty slot, since there are no labels over the slots to save room. */
    private void slotTooltip(GuiGraphics graphics, int mouseX, int mouseY, int slotIndex) {
        Mode mode = menu.mode();
        String key = switch (slotIndex) {
            case SignWorkbenchMenu.FIRST_SLOT -> switch (mode) {
                case MAKE -> "material";
                case PAINT -> "part";
                case ASSEMBLE -> "frame";
                case DISASSEMBLE -> "sign";
            };
            case SignWorkbenchMenu.SECOND_SLOT -> mode == Mode.PAINT ? "dye" : "board";
            case SignWorkbenchMenu.FRAME_RESULT_SLOT -> "frame";
            case SignWorkbenchMenu.BOARD_RESULT_SLOT -> "board";
            default -> null;
        };
        if (key == null) {
            return;
        }
        if (key.equals("dye")) {
            tooltip(graphics, mouseX, mouseY, Component.translatable("moresigns.workbench.slot.dye").withStyle(ChatFormatting.WHITE),
                    Component.translatable("moresigns.workbench.slot.dye.tooltip").withStyle(ChatFormatting.GRAY));
        } else {
            tooltip(graphics, mouseX, mouseY, Component.translatable("moresigns.workbench.slot." + key).withStyle(ChatFormatting.WHITE));
        }
    }

    private void makeTooltip(GuiGraphics graphics, int mouseX, int mouseY, String kind, int yield, int cost) {
        tooltip(graphics, mouseX, mouseY, Component.translatable("moresigns.workbench.button." + kind).withStyle(ChatFormatting.WHITE),
                Component.translatable("moresigns.workbench.button.cost", yield, cost).withStyle(ChatFormatting.GRAY));
    }

    private void tooltip(GuiGraphics graphics, int mouseX, int mouseY, Component... lines) {
        List<Component> list = new ArrayList<>(List.of(lines));
        graphics.renderComponentTooltip(font, list, mouseX, mouseY);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) {
            for (int i = 0; i < TABS.size(); i++) {
                if (isOverTab(i, mouseX, mouseY)) {
                    if (TABS.get(i).mode() != menu.mode()) {
                        press(i, SoundEvents.UI_BUTTON_CLICK.value());
                    }
                    return true;
                }
            }
            Mode mode = menu.mode();
            if (mode == Mode.MAKE) {
                if (isOver(leftPos + MAKE_FRAME_X, topPos + MAKE_BUTTONS_Y, BUTTON_SIZE, BUTTON_SIZE, mouseX, mouseY)) {
                    press(SignWorkbenchMenu.BUTTON_MAKE_FRAME, SoundEvents.UI_STONECUTTER_SELECT_RECIPE);
                    return true;
                }
                if (isOver(leftPos + MAKE_BOARD_X, topPos + MAKE_BUTTONS_Y, BUTTON_SIZE, BUTTON_SIZE, mouseX, mouseY)) {
                    press(SignWorkbenchMenu.BUTTON_MAKE_BOARD, SoundEvents.UI_STONECUTTER_SELECT_RECIPE);
                    return true;
                }
            } else if (mode == Mode.PAINT
                    && isOver(leftPos + WASH_X, topPos + WASH_Y, BUTTON_SIZE, BUTTON_SIZE, mouseX, mouseY)) {
                press(SignWorkbenchMenu.BUTTON_TOGGLE_WASH, SoundEvents.UI_LOOM_SELECT_PATTERN);
                return true;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    private void press(int buttonId, SoundEvent sound) {
        minecraft.getSoundManager().play(SimpleSoundInstance.forUI(sound, 1.0F));
        minecraft.gameMode.handleInventoryButtonClick(menu.containerId, buttonId);
    }

    private int tabY(int index) {
        return topPos + TAB_TOP + index * (TAB_HEIGHT + TAB_GAP);
    }

    private boolean isOverTab(int index, double mouseX, double mouseY) {
        return isOver(leftPos - TAB_VISIBLE, tabY(index), TAB_VISIBLE, TAB_HEIGHT, mouseX, mouseY);
    }

    private static boolean isOver(int x, int y, int width, int height, double mouseX, double mouseY) {
        return mouseX >= x && mouseX < x + width && mouseY >= y && mouseY < y + height;
    }
}
