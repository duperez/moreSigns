package duperez.moresigns.data;

import com.google.common.collect.Sets;
import duperez.moresigns.common.registry.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
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

	public ItemModels(PackOutput output, ExistingFileHelper existingFileHelper) {
		super(output, MoreSigns.MODID, existingFileHelper);
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
				ModItems.ANTHONY_CANVAS_SIGN.get(),
				ModItems.HOTPINK_CANVAS_SIGN.get(),
				ModItems.INDIGO_CANVAS_SIGN.get(),
				ModItems.LIGHT_PINK_CANVAS_SIGN.get(),
				ModItems.LOOSAZ_CANVAS_SIGN.get(),
				ModItems.SPADES_CANVAS_SIGN.get(),
				ModItems.ANHANGA_CANVAS_SIGN.get(),
				ModItems.JINX_CANVAS_SIGN.get(),
				ModItems.GOLD_CANVAS_SIGN.get(),
				ModItems.MABHETIEL_CANVAS_SIGN.get(),
				ModItems.POMPOM_CANVAS_SIGN.get(),
				ModItems.RABBIT_CANVAS_SIGN.get(),
				ModItems.SODA_CANVAS_SIGN.get(),
				ModItems.CYBER_CANVAS_SIGN.get(),
				ModItems.AUTISM_CANVAS_SIGN.get(),
				ModItems.MARY_CANVAS_SIGN.get(),
				ModItems.MEG_CANVAS_SIGN.get(),
				ModItems.DELTA_CANVAS_SIGN.get(),
				ModItems.JACK_CANVAS_SIGN.get(),
				ModItems.KALYPSO_CANVAS_SIGN.get(),
				ModItems.M1_CANVAS_SIGN.get(),
				ModItems.MICHAEL_CANVAS_SIGN.get(),
				ModItems.MONA_CANVAS_SIGN.get(),
				ModItems.MOON_CANVAS_SIGN.get(),
				ModItems.UKO_CANVAS_SIGN.get(),
				ModItems.JAY_CANVAS_SIGN.get(),
				ModItems.CELYNE_CANVAS_SIGN.get(),
				ModItems.PEPPER_CANVAS_SIGN.get(),
				ModItems.EPSILON_CANVAS_SIGN.get(),
				ModItems.HAVARTI_CANVAS_SIGN.get(),
				ModItems.MINERVA_CANVAS_SIGN.get(),
				ModItems.RUNE_CANVAS_SIGN.get(),
				ModItems.LAMBDA_CANVAS_SIGN.get(),
				ModItems.OMICRON_CANVAS_SIGN.get(),
				ModItems.PSI_CANVAS_SIGN.get(),
				ModItems.TAU_CANVAS_SIGN.get(),
				ModItems.WICKY_CANVAS_SIGN.get(),
				ModItems.YUN_CANVAS_SIGN.get(),
				ModItems.JUPITER_CANVAS_SIGN.get(),
				ModItems.BENNY_CANVAS_SIGN.get(),
				ModItems.TINKER_CANVAS_SIGN.get(),
				ModItems.ABADOM_CANVAS_SIGN.get(),
				ModItems.APOLIOM_CANVAS_SIGN.get(),
				ModItems.CLOWN_CANVAS_SIGN.get(),
				ModItems.SILVIR_CANVAS_SIGN.get(),
				ModItems.MONSIEUR_CANVAS_SIGN.get(),
				ModItems.INKARIS_CANVAS_SIGN.get(),
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
		return ForgeRegistries.ITEMS.getKey(item).getPath();
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