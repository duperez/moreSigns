package duperez.moresigns.client;

import duperez.moresigns.client.renderer.CustomSignItemRenderer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraftforge.client.IItemRenderProperties;

/** Client-only parts of the sign items. Only touched from {@code initializeClient}, which the server never calls. */
public final class ClientItemProperties {
    private static final IItemRenderProperties CUSTOM_SIGN = new IItemRenderProperties() {
        private BlockEntityWithoutLevelRenderer renderer;

        @Override
        public BlockEntityWithoutLevelRenderer getItemStackRenderer() {
            if (renderer == null) {
                renderer = new CustomSignItemRenderer();
            }
            return renderer;
        }
    };

    private ClientItemProperties() {
    }

    /** Frames, boards and custom signs are drawn by code, from the materials in their stack. */
    public static IItemRenderProperties customSignRenderer() {
        return CUSTOM_SIGN;
    }
}
