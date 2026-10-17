package duperez.moresigns.common.tag;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

/**
 * References to tags under the Forge namespace.
 * These tags are generally used for crafting recipes across different mods.
 */
public class ForgeTags
{

	private static TagKey<Block> forgeBlockTag(String path) {
		return BlockTags.create(new ResourceLocation("forge", path));
	}

	private static TagKey<Item> forgeItemTag(String path) {
		return ItemTags.create(new ResourceLocation("forge", path));
	}


}
