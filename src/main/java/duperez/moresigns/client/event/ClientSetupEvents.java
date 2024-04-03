package duperez.moresigns.client.event;

import duperez.moresigns.client.renderer.CanvasSignRenderer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import duperez.moresigns.MoreSigns;
import duperez.moresigns.common.registry.ModAtlases;
import duperez.moresigns.common.registry.ModBlockEntityTypes;

@Mod.EventBusSubscriber(modid = MoreSigns.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetupEvents {

    @SubscribeEvent
    public static void onStitchEvent(TextureStitchEvent.Pre event) {
        ResourceLocation stitching = event.getAtlas().location();
        if (stitching.equals(new ResourceLocation("textures/atlas/signs.png"))) {
            event.addSprite(ModAtlases.BLANK_CANVAS_SIGN_MATERIAL.texture());
            for (Material material : ModAtlases.DYED_CANVAS_SIGN_MATERIALS.values()) {
                event.addSprite(material.texture());
            }
        }
        if (!stitching.equals(TextureAtlas.LOCATION_BLOCKS)) {
            return;
        }
    }

    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModBlockEntityTypes.CANVAS_SIGN.get(), CanvasSignRenderer::new);
    }
}
