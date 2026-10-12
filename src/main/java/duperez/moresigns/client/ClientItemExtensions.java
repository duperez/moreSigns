package duperez.moresigns.client;

import duperez.moresigns.client.renderer.CustomSignItemRenderer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

/** Client-only parts of the sign items. Only touched from {@code initializeClient}, which the server never calls. */
public final class ClientItemExtensions {
    private static final IClientItemExtensions CUSTOM_SIGN = new IClientItemExtensions() {
        private BlockEntityWithoutLevelRenderer renderer;

        @Override
        public BlockEntityWithoutLevelRenderer getCustomRenderer() {
            if (renderer == null) {
                renderer = new CustomSignItemRenderer();
            }
            return renderer;
        }
    };

    private ClientItemExtensions() {
    }

    /** Frames, boards and custom signs are drawn by code, from the materials in their stack. */
    public static IClientItemExtensions customSignRenderer() {
        return CUSTOM_SIGN;
    }
}
