package duperez.moresigns.common.tag;

import duperez.moresigns.MoreSigns;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModTags {
    /** Every canvas sign item. A dye recipe takes any of them as input. */
    public static final TagKey<Item> CANVAS_SIGNS = TagKey.create(Registries.ITEM,
            ResourceLocation.fromNamespaceAndPath(MoreSigns.MODID, "canvas_signs"));

    private ModTags() {
    }
}
