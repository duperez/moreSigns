package duperez.moresigns.common.tag;

import duperez.moresigns.MoreSigns;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {
    /** Every canvas sign item. A dye recipe takes any of them as input. */
    public static final TagKey<Item> CANVAS_SIGNS = TagKey.create(Registries.ITEM, new ResourceLocation(MoreSigns.MODID, "canvas_signs"));

    /** Blocks the sign workbench accepts as material for a frame or a board. */
    public static final TagKey<Block> SIGN_MATERIALS = TagKey.create(Registries.BLOCK, new ResourceLocation(MoreSigns.MODID, "sign_materials"));

    private ModTags() {
    }
}
