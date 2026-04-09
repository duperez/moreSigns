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

    //@SubscribeEvent
    //public static void onTextureStitchEvent(TextureStitchEvent event) {
    //    ResourceLocation stitching = event.getAtlas().location();
    //    if (stitching.equals(TextureAtlas.LOCATION_BLOCKS)) {
    //        event.addSprite(ModAtlases.BLANK_CANVAS_SIGN_MATERIAL.texture());
    //        for (Material material : ModAtlases.DYED_CANVAS_SIGN_MATERIALS.values()) {
    //            event.addSprite(material.texture());
    //        }
    //    }
    //}

    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModBlockEntityTypes.CANVAS_SIGN.get(), CanvasSignRenderer::new);
    }
}
