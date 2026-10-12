package duperez.moresigns.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import duperez.moresigns.common.item.PartItem;
import duperez.moresigns.common.sign.Part;
import duperez.moresigns.common.sign.SignNbt;
import duperez.moresigns.common.sign.SignParts;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;

/** Draws sign frames, sign boards and custom signs as the item they are, with the materials stored in the stack. */
public class CustomSignItemRenderer extends BlockEntityWithoutLevelRenderer {
    /** The model sits slightly above the middle of its cube; this brings the whole sign to the center. */
    private static final float SIGN_DROP = 0.04F;
    /** A board on its own is the upper part of the sign; this brings it to the center. */
    private static final float BOARD_DROP = CustomSignModel.PLATE_CENTER_Y - 0.5F;

    public CustomSignItemRenderer() {
        super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
    }

    @Override
    public void renderByItem(ItemStack stack, ItemTransforms.TransformType context, PoseStack poseStack, MultiBufferSource buffers, int light, int overlay) {
        Optional<Part> frame;
        Optional<Part> board;
        float drop = SIGN_DROP;
        if (stack.getItem() instanceof PartItem part) {
            Part material = SignNbt.getPart(stack);
            boolean isFrame = part.kind() == PartItem.Kind.FRAME;
            frame = isFrame ? Optional.of(material) : Optional.empty();
            board = isFrame ? Optional.empty() : Optional.of(material);
            if (!isFrame) {
                drop = BOARD_DROP;
            }
        } else {
            SignParts parts = SignNbt.getSignParts(stack);
            frame = Optional.of(parts.frame());
            board = Optional.of(parts.board());
        }

        poseStack.pushPose();
        poseStack.translate(0.0F, -drop, 0.0F);
        CustomSignModel.render(buffers.getBuffer(CustomSignModel.RENDER_TYPE), poseStack.last(), frame, board, true, light, overlay);
        poseStack.popPose();
    }
}
