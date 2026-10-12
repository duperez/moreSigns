package duperez.moresigns.client;

import duperez.moresigns.MoreSigns;
import duperez.moresigns.common.registry.ModBlockEntityTypes;
import duperez.moresigns.common.registry.ModWoodTypes;
import duperez.moresigns.common.variant.CanvasVariants;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = MoreSigns.MODID, value = Dist.CLIENT)
public class ClientSetup {

    /** Vanilla's renderer picks the right sprite for each variant through its wood type. */
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModBlockEntityTypes.CANVAS_SIGN.get(), SignRenderer::new);
    }

    /**
     * Makes sure the sign sprite sheet knows every wood type, in case it was built before ours were
     * registered. Harmless when it already knows them.
     */
    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> CanvasVariants.ALL.forEach(variant -> Sheets.addWoodType(ModWoodTypes.get(variant))));
    }
}
