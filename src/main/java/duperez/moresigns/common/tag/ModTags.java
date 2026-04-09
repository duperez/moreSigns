package duperez.moresigns.common.tag;

import duperez.moresigns.MoreSigns;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

/**
 * References to tags under the Farmer's Delight namespace.
 * These tags are used for mod mechanics.
 */
public class ModTags
{

	public static final TagKey<Item> CANVAS_SIGNS = modItemTag("canvas_signs");

	private static TagKey<Item> modItemTag(String path) {
		return ItemTags.create(new ResourceLocation(MoreSigns.MODID, path));
	}
}
