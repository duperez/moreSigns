package duperez.moresigns.data;

import duperez.moresigns.MoreSigns;
import duperez.moresigns.common.registry.ModItems;
import duperez.moresigns.common.tag.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class ItemTags extends ItemTagsProvider
{
	public ItemTags(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, CompletableFuture<TagsProvider.TagLookup<Block>> blockTagProvider, @Nullable ExistingFileHelper existingFileHelper) {
		super(output, provider, blockTagProvider, MoreSigns.MODID, existingFileHelper);
	}

	@Override
	protected void addTags(HolderLookup.Provider provider) {
		this.registerModTags();
	}


	private void registerModTags() {
		tag(ModTags.CANVAS_SIGNS)
				.add(ModItems.CANVAS_SIGN.get())
				.add(ModItems.WHITE_CANVAS_SIGN.get())
				.add(ModItems.ORANGE_CANVAS_SIGN.get())
				.add(ModItems.MAGENTA_CANVAS_SIGN.get())
				.add(ModItems.LIGHT_BLUE_CANVAS_SIGN.get())
				.add(ModItems.YELLOW_CANVAS_SIGN.get())
				.add(ModItems.LIME_CANVAS_SIGN.get())
				.add(ModItems.PINK_CANVAS_SIGN.get())
				.add(ModItems.GRAY_CANVAS_SIGN.get())
				.add(ModItems.LIGHT_GRAY_CANVAS_SIGN.get())
				.add(ModItems.CYAN_CANVAS_SIGN.get())
				.add(ModItems.PURPLE_CANVAS_SIGN.get())
				.add(ModItems.BLUE_CANVAS_SIGN.get())
				.add(ModItems.BROWN_CANVAS_SIGN.get())
				.add(ModItems.GREEN_CANVAS_SIGN.get())
				.add(ModItems.RED_CANVAS_SIGN.get())
				.add(ModItems.GREEN_GRADIENT_CANVAS_SIGN.get())
				.add(ModItems.ORANGE_PURPLE_CANVAS_SIGN.get())
				.add(ModItems.ANTHONY_CANVAS_SIGN.get())
				.add(ModItems.HOTPINK_CANVAS_SIGN.get())
				.add(ModItems.INDIGO_CANVAS_SIGN.get())
				.add(ModItems.LIGHT_PINK_CANVAS_SIGN.get())
				.add(ModItems.LOOSAZ_CANVAS_SIGN.get())
				.add(ModItems.SPADES_CANVAS_SIGN.get())
				.add(ModItems.JINX_CANVAS_SIGN.get())
				.add(ModItems.ANHANGA_CANVAS_SIGN.get())
				.add(ModItems.GOLD_CANVAS_SIGN.get())
				.add(ModItems.MABHETIEL_CANVAS_SIGN.get())
				.add(ModItems.POMPOM_CANVAS_SIGN.get())
				.add(ModItems.RABBIT_CANVAS_SIGN.get())
				.add(ModItems.SODA_CANVAS_SIGN.get())
				.add(ModItems.CYBER_CANVAS_SIGN.get())
				.add(ModItems.AUTISM_CANVAS_SIGN.get())
				.add(ModItems.MARY_CANVAS_SIGN.get())
				.add(ModItems.MEG_CANVAS_SIGN.get())
				.add(ModItems.DELTA_CANVAS_SIGN.get())
				.add(ModItems.JACK_CANVAS_SIGN.get())
				.add(ModItems.KALYPSO_CANVAS_SIGN.get())
				.add(ModItems.M1_CANVAS_SIGN.get())
				.add(ModItems.MICHAEL_CANVAS_SIGN.get())
				.add(ModItems.MONA_CANVAS_SIGN.get())
				.add(ModItems.MOON_CANVAS_SIGN.get())
				.add(ModItems.UKO_CANVAS_SIGN.get())
				.add(ModItems.BLACK_CANVAS_SIGN.get());
	}
}
