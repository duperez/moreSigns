package duperez.moresigns.client.gui;

import com.mojang.math.Axis;
import duperez.moresigns.client.renderer.CustomSignModel;
import duperez.moresigns.common.block.entity.CustomSignBlockEntity;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.SignEditScreen;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.state.BlockState;

import org.joml.Vector3f;

import java.util.Optional;

/** The vanilla sign editor, drawing the custom sign (with its own frame and board) instead of a wooden one. */
public class CustomSignEditScreen extends SignEditScreen {
    /** Pixels per block. Vanilla draws its model, 1.5 units wide, at 62.5 pixels per unit. */
    private static final float SCALE = 93.75F;

    private final CustomSignBlockEntity customSign;
    private final boolean front;

    public CustomSignEditScreen(CustomSignBlockEntity sign, boolean front, boolean filteringEnabled) {
        super(sign, front, filteringEnabled);
        this.customSign = sign;
        this.front = front;
    }

    /** The plate is smaller than a vanilla one, so the text is drawn smaller to stay inside it, as it is in the world. */
    @Override
    protected Vector3f getSignTextScale() {
        float scale = SignEditScreen.MAGIC_TEXT_SCALE * CustomSignModel.TEXT_SHRINK;
        return new Vector3f(scale, scale, scale);
    }

    @Override
    protected void renderSignBackground(GuiGraphics graphics, BlockState state) {
        graphics.pose().translate(0.0F, 31.0F, 0.0F);
        // Screen y points down while the model's points up, hence the negative y scale.
        graphics.pose().scale(SCALE, -SCALE, SCALE);
        if (!front) {
            graphics.pose().mulPose(Axis.YP.rotationDegrees(180.0F));
        }
        graphics.pose().translate(-0.5F, -CustomSignModel.PLATE_CENTER_Y + 1F / 3F, -0.5F);
        CustomSignModel.render(graphics.bufferSource().getBuffer(CustomSignModel.RENDER_TYPE), graphics.pose().last(),
                Optional.of(customSign.parts().frame()), Optional.of(customSign.parts().board()),
                state.getBlock() instanceof StandingSignBlock, 15728880, OverlayTexture.NO_OVERLAY);
    }
}
