package duperez.moresigns.client;

import duperez.moresigns.MoreSigns;
import duperez.moresigns.client.gui.CustomSignEditScreen;
import duperez.moresigns.client.gui.SignWorkbenchScreen;
import duperez.moresigns.client.renderer.CustomSignItemRenderer;
import duperez.moresigns.client.renderer.CustomSignRenderer;
import duperez.moresigns.common.block.entity.CustomSignBlockEntity;
import duperez.moresigns.common.registry.ModBlockEntityTypes;
import duperez.moresigns.common.registry.ModItems;
import duperez.moresigns.common.registry.ModMenuTypes;
import duperez.moresigns.common.registry.ModWoodTypes;
import duperez.moresigns.common.variant.CanvasVariants;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.SignEditScreen;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.event.ScreenEvent;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;

@EventBusSubscriber(modid = MoreSigns.MODID, value = Dist.CLIENT)
public class ClientSetup {

    /** Vanilla's renderer picks the right sprite for each variant through its wood type. */
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModBlockEntityTypes.CANVAS_SIGN.get(), SignRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntityTypes.CUSTOM_SIGN.get(), CustomSignRenderer::new);
    }

    /**
     * Makes sure the sign sprite sheet knows every wood type, in case it was built before ours were
     * registered. Harmless when it already knows them.
     */
    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> CanvasVariants.ALL.forEach(variant -> Sheets.addWoodType(ModWoodTypes.get(variant))));
    }

    /**
     * The game opens a vanilla {@link SignEditScreen} for every sign. For a custom sign, swap it for one that draws
     * the sign as it looks in the world. Exact class check so screens of other mods are left alone.
     */
    @SubscribeEvent
    public static void onScreenOpening(ScreenEvent.Opening event) {
        if (event.getNewScreen() != null && event.getNewScreen().getClass() == SignEditScreen.class) {
            SignEditScreen screen = (SignEditScreen) event.getNewScreen();
            if (screen.sign instanceof CustomSignBlockEntity sign) {
                event.setNewScreen(new CustomSignEditScreen(sign, screen.isFrontText, Minecraft.getInstance().isTextFilteringEnabled()));
            }
        }
    }

    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(ModMenuTypes.SIGN_WORKBENCH.get(), SignWorkbenchScreen::new);
    }

    /** Frames, boards and custom signs are drawn by code, from the materials in their stack. */
    @SubscribeEvent
    public static void registerItemExtensions(RegisterClientExtensionsEvent event) {
        event.registerItem(new IClientItemExtensions() {
            private BlockEntityWithoutLevelRenderer renderer;

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if (renderer == null) {
                    renderer = new CustomSignItemRenderer();
                }
                return renderer;
            }
        }, ModItems.SIGN_FRAME.get(), ModItems.SIGN_BOARD.get(), ModItems.CUSTOM_SIGN.get());
    }
}
