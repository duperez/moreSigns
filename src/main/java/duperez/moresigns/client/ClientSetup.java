package duperez.moresigns.client;

import duperez.moresigns.MoreSigns;
import duperez.moresigns.client.gui.SignWorkbenchScreen;
import duperez.moresigns.client.renderer.CustomSignRenderer;
import duperez.moresigns.common.registry.ModBlockEntityTypes;
import duperez.moresigns.common.registry.ModMenuTypes;
import duperez.moresigns.common.registry.ModWoodTypes;
import duperez.moresigns.common.variant.CanvasVariants;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = MoreSigns.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetup {

    /** Vanilla's renderer picks the right sprite for each canvas variant through its wood type. */
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModBlockEntityTypes.CANVAS_SIGN.get(), SignRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntityTypes.CUSTOM_SIGN.get(), CustomSignRenderer::new);
    }

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            MenuScreens.register(ModMenuTypes.SIGN_WORKBENCH.get(), SignWorkbenchScreen::new);
            // Makes sure the sign sprite sheet knows every wood type, in case it was built before ours were registered.
            CanvasVariants.ALL.forEach(variant -> Sheets.addWoodType(ModWoodTypes.get(variant)));
        });
    }
}
