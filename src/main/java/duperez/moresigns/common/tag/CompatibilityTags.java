package duperez.moresigns.common.tag;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

/**
 * References to tags belonging to other mods, which Farmer's Delight innately supports.
 * These tags are used by other mods for their own mechanics.
 */
public class CompatibilityTags
{
	// Create
	public static final String CREATE = "create";
	@Deprecated // Legacy compatibility with Create 0.4. Remove on 1.19 and beyond.
	public static final TagKey<Block> CREATE_FAN_HEATERS = externalBlockTag(CREATE, "fan_heaters");


	// Tinker's Construct
	public static final String TINKERS_CONSTRUCT = "tconstruct";
	public static final TagKey<Item> TINKERS_CONSTRUCT_SEEDS = externalItemTag(TINKERS_CONSTRUCT, "seeds");

	private static TagKey<Item> externalItemTag(String modId, String path) {
		return ItemTags.create(new ResourceLocation(modId, path));
	}

	private static TagKey<Block> externalBlockTag(String modId, String path) {
		return BlockTags.create(new ResourceLocation(modId, path));
	}
}
