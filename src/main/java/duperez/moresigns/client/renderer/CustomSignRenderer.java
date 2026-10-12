package duperez.moresigns.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import duperez.moresigns.common.block.entity.CustomSignBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.util.FastColor;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Optional;

/** Draws a custom sign in the world: its geometry from {@link CustomSignModel}, then its text. */
public class CustomSignRenderer implements BlockEntityRenderer<CustomSignBlockEntity> {
    private static final float TEXT_SCALE = 0.015625F * 0.6666667F * CustomSignModel.TEXT_SHRINK;
    private static final int LINE_HEIGHT = 10;
    private static final int MAX_LINE_WIDTH = 90;
    private static final int OUTLINE_RENDER_DISTANCE = Mth.square(16);
    private static final int BLACK_TEXT_OUTLINE_COLOR = -988212;

    private final Font font;

    public CustomSignRenderer(BlockEntityRendererProvider.Context context) {
        this.font = context.getFont();
    }

    @Override
    public void render(CustomSignBlockEntity sign, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        BlockState state = sign.getBlockState();
        boolean wall = state.getBlock() instanceof WallSignBlock;
        float yaw = wall ? -state.getValue(WallSignBlock.FACING).toYRot() : -state.getValue(StandingSignBlock.ROTATION) * 360.0F / 16.0F;

        poseStack.pushPose();
        poseStack.translate(0.5F, 0.0F, 0.5F);
        poseStack.mulPose(Vector3f.YP.rotationDegrees(yaw));
        poseStack.translate(-0.5F, 0.0F, -0.5F);
        if (wall) {
            poseStack.translate(0.0F, CustomSignModel.WALL_OFFSET_Y, CustomSignModel.WALL_OFFSET_Z);
        }
        CustomSignModel.render(bufferSource.getBuffer(CustomSignModel.RENDER_TYPE), poseStack.last(),
                Optional.of(sign.parts().frame()), Optional.of(sign.parts().board()), !wall, packedLight, packedOverlay);
        poseStack.popPose();

        poseStack.pushPose();
        poseStack.translate(0.5F, 0.5F, 0.5F);
        poseStack.mulPose(Vector3f.YP.rotationDegrees(yaw));
        if (wall) {
            poseStack.translate(0.0F, CustomSignModel.WALL_OFFSET_Y, CustomSignModel.WALL_OFFSET_Z);
        }
        renderText(sign, poseStack, bufferSource, packedLight);
        poseStack.popPose();
    }

    /** Mirrors vanilla's sign text drawing, which is not callable from outside its package. */
    private void renderText(CustomSignBlockEntity sign, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        poseStack.pushPose();
        poseStack.translate(0.0F, CustomSignModel.PLATE_CENTER_Y - 0.5F, CustomSignModel.TEXT_FRONT_OFFSET);
        poseStack.scale(TEXT_SCALE, -TEXT_SCALE, TEXT_SCALE);

        int darkColor = darkColor(sign);
        int firstLineY = 4 * LINE_HEIGHT / 2;
        FormattedCharSequence[] lines = sign.getRenderMessages(Minecraft.getInstance().isTextFilteringEnabled(), component -> {
            List<FormattedCharSequence> split = font.split(component, MAX_LINE_WIDTH);
            return split.isEmpty() ? FormattedCharSequence.EMPTY : split.get(0);
        });

        int color;
        boolean outline;
        int light;
        if (sign.hasGlowingText()) {
            color = sign.getColor().getTextColor();
            outline = isOutlineVisible(sign.getBlockPos(), color);
            light = 15728880;
        } else {
            color = darkColor;
            outline = false;
            light = packedLight;
        }

        Matrix4f matrix = poseStack.last().pose();
        for (int i = 0; i < 4; i++) {
            FormattedCharSequence line = lines[i];
            float x = -font.width(line) / 2.0F;
            float y = i * LINE_HEIGHT - firstLineY;
            if (outline) {
                font.drawInBatch8xOutline(line, x, y, color, darkColor, matrix, bufferSource, light);
            } else {
                font.drawInBatch(line, x, y, color, false, matrix, bufferSource, false, 0, light);
            }
        }
        poseStack.popPose();
    }

    private static int darkColor(CustomSignBlockEntity sign) {
        int color = sign.getColor().getTextColor();
        if (color == DyeColor.BLACK.getTextColor() && sign.hasGlowingText()) {
            return BLACK_TEXT_OUTLINE_COLOR;
        }
        return FastColor.ARGB32.color(0,
                (int) (FastColor.ARGB32.red(color) * 0.4), (int) (FastColor.ARGB32.green(color) * 0.4), (int) (FastColor.ARGB32.blue(color) * 0.4));
    }

    private static boolean isOutlineVisible(BlockPos pos, int textColor) {
        if (textColor == DyeColor.BLACK.getTextColor()) {
            return true;
        }
        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;
        if (player != null && minecraft.options.getCameraType().isFirstPerson() && player.isScoping()) {
            return true;
        }
        Entity camera = minecraft.getCameraEntity();
        return camera != null && camera.distanceToSqr(Vec3.atCenterOf(pos)) < OUTLINE_RENDER_DISTANCE;
    }
}
