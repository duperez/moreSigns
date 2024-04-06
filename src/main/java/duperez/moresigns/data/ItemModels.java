package duperez.moresigns.data;

import com.google.common.collect.Sets;
import duperez.moresigns.common.registry.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import duperez.moresigns.MoreSigns;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Credits to Vazkii and team for some references on mass-reading blocks to datagen!
 */
public class ItemModels extends ItemModelProvider
{
	public static final String GENERATED = "item/generated";

	public ItemModels(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator, MoreSigns.MODID, existingFileHelper);
	}

	@Override
	protected void registerModels() {
		Set<Item> items = ForgeRegistries.ITEMS.getValues().stream().filter(i -> MoreSigns.MODID.equals(ForgeRegistries.ITEMS.getKey(i).getNamespace()))
				.collect(Collectors.toSet());


		// Blocks with special item sprites
		Set<Item> spriteBlockItems = Sets.newHashSet(
				ModItems.CANVAS_SIGN.get(),
				ModItems.WHITE_CANVAS_SIGN.get(),
				ModItems.ORANGE_CANVAS_SIGN.get(),
				ModItems.MAGENTA_CANVAS_SIGN.get(),
				ModItems.LIGHT_BLUE_CANVAS_SIGN.get(),
				ModItems.YELLOW_CANVAS_SIGN.get(),
				ModItems.LIME_CANVAS_SIGN.get(),
				ModItems.PINK_CANVAS_SIGN.get(),
				ModItems.GRAY_CANVAS_SIGN.get(),
				ModItems.LIGHT_GRAY_CANVAS_SIGN.get(),
				ModItems.ORANGE_PURPLE_CANVAS_SIGN.get(),
				ModItems.CYAN_CANVAS_SIGN.get(),
				ModItems.PURPLE_CANVAS_SIGN.get(),
				ModItems.BLUE_CANVAS_SIGN.get(),
				ModItems.BROWN_CANVAS_SIGN.get(),
				ModItems.GREEN_CANVAS_SIGN.get(),
				ModItems.RED_CANVAS_SIGN.get(),
				ModItems.GREEN_GRADIENT_CANVAS_SIGN.get(),
				ModItems.BLACK_CANVAS_SIGN.get()
		);
		takeAll(items, spriteBlockItems.toArray(new Item[0])).forEach(item -> withExistingParent(itemName(item), GENERATED).texture("layer0", resourceItem(itemName(item))));

		// Generated items
		items.forEach(item -> itemGeneratedModel(item, resourceItem(itemName(item))));
	}

	public void itemGeneratedModel(Item item, ResourceLocation texture) {
		withExistingParent(itemName(item), GENERATED).texture("layer0", texture);
	}

	private String itemName(Item item) {
		return item.getRegistryName().getPath();
	}

	public ResourceLocation resourceItem(String path) {
		return new ResourceLocation(MoreSigns.MODID, "item/" + path);
	}

	@SafeVarargs
	@SuppressWarnings("varargs")
	public static <T> Collection<T> takeAll(Set<? extends T> src, T... items) {
		List<T> ret = Arrays.asList(items);
		for (T item : items) {
			if (!src.contains(item)) {
				MoreSigns.LOGGER.warn("Item {} not found in set", item);
			}
		}
		if (!src.removeAll(ret)) {
			MoreSigns.LOGGER.warn("takeAll array didn't yield anything ({})", Arrays.toString(items));
		}
		return ret;
	}
}