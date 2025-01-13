package duperez.moresigns.data;

import duperez.moresigns.MoreSigns;
import duperez.moresigns.common.registry.ModBlocks;
import duperez.moresigns.common.registry.ModEntityTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class BlockTags extends BlockTagsProvider
{
	public BlockTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
		super(output, lookupProvider, MoreSigns.MODID, existingFileHelper);
	}

	@Override
	protected void addTags(HolderLookup.Provider provider) {
		this.registerMinecraftTags();
	}

	protected void registerMinecraftTags() {
		tag(net.minecraft.tags.BlockTags.STANDING_SIGNS).add(
				ModBlocks.CANVAS_SIGN.get(),
				ModBlocks.WHITE_CANVAS_SIGN.get(),
				ModBlocks.ORANGE_CANVAS_SIGN.get(),
				ModBlocks.MAGENTA_CANVAS_SIGN.get(),
				ModBlocks.LIGHT_BLUE_CANVAS_SIGN.get(),
				ModBlocks.YELLOW_CANVAS_SIGN.get(),
				ModBlocks.LIME_CANVAS_SIGN.get(),
				ModBlocks.PINK_CANVAS_SIGN.get(),
				ModBlocks.GRAY_CANVAS_SIGN.get(),
				ModBlocks.LIGHT_GRAY_CANVAS_SIGN.get(),
				ModBlocks.CYAN_CANVAS_SIGN.get(),
				ModBlocks.PURPLE_CANVAS_SIGN.get(),
				ModBlocks.BLUE_CANVAS_SIGN.get(),
				ModBlocks.BROWN_CANVAS_SIGN.get(),
				ModBlocks.GREEN_CANVAS_SIGN.get(),
				ModBlocks.RED_CANVAS_SIGN.get(),
				ModBlocks.ORANGE_PURPLE_SIGN.get(),
				ModBlocks.GREEN_GRADIENT_SIGN.get(),
				ModBlocks.BLACK_CANVAS_SIGN.get(),
				ModBlocks.HOTPINK_SIGN.get(),
				ModBlocks.INDIGO_SIGN.get(),
				ModBlocks.LIGHT_PINK_SIGN.get(),
				ModBlocks.LOOSAZ_SIGN.get(),
				ModBlocks.SPADES_SIGN.get(),
				ModBlocks.JINX_SIGN.get(),
				ModBlocks.ANHANGA_SIGN.get(),
				ModBlocks.GOLD_SIGN.get(),
				ModBlocks.MABHETIEL_SIGN.get(),
				ModBlocks.POMPOM_SIGN.get(),
				ModBlocks.RABBIT_SIGN.get(),
				ModBlocks.SODA_SIGN.get(),
				ModBlocks.CYBER_SIGN.get(),
				ModBlocks.AUTISM_SIGN.get(),
				ModBlocks.MARY_SIGN.get(),
				ModBlocks.ANTHONY_SIGN.get());
		tag(net.minecraft.tags.BlockTags.WALL_SIGNS).add(
				ModBlocks.CANVAS_WALL_SIGN.get(),
				ModBlocks.WHITE_CANVAS_WALL_SIGN.get(),
				ModBlocks.ORANGE_CANVAS_WALL_SIGN.get(),
				ModBlocks.MAGENTA_CANVAS_WALL_SIGN.get(),
				ModBlocks.LIGHT_BLUE_CANVAS_WALL_SIGN.get(),
				ModBlocks.YELLOW_CANVAS_WALL_SIGN.get(),
				ModBlocks.LIME_CANVAS_WALL_SIGN.get(),
				ModBlocks.PINK_CANVAS_WALL_SIGN.get(),
				ModBlocks.GRAY_CANVAS_WALL_SIGN.get(),
				ModBlocks.LIGHT_GRAY_CANVAS_WALL_SIGN.get(),
				ModBlocks.CYAN_CANVAS_WALL_SIGN.get(),
				ModBlocks.PURPLE_CANVAS_WALL_SIGN.get(),
				ModBlocks.BLUE_CANVAS_WALL_SIGN.get(),
				ModBlocks.BROWN_CANVAS_WALL_SIGN.get(),
				ModBlocks.GREEN_CANVAS_WALL_SIGN.get(),
				ModBlocks.RED_CANVAS_WALL_SIGN.get(),
				ModBlocks.ORANGE_PURPLE_CANVAS_WALL_SIGN.get(),
				ModBlocks.GREEN_GRADIENT_WALL_SIGN.get(),
				ModBlocks.BLACK_CANVAS_WALL_SIGN.get(),
				ModBlocks.HOTPINK_WALL_SIGN.get(),
				ModBlocks.INDIGO_WALL_SIGN.get(),
				ModBlocks.LIGHT_PINK_WALL_SIGN.get(),
				ModBlocks.LOOSAZ_WALL_SIGN.get(),
				ModBlocks.SPADES_WALL_SIGN.get(),
				ModBlocks.JINX_WALL_SIGN.get(),
				ModBlocks.ANHANGA_WALL_SIGN.get(),
				ModBlocks.GOLD_WALL_SIGN.get(),
				ModBlocks.MABHETIEL_WALL_SIGN.get(),
				ModBlocks.POMPOM_WALL_SIGN.get(),
				ModBlocks.RABBIT_WALL_SIGN.get(),
				ModBlocks.SODA_WALL_SIGN.get(),
				ModBlocks.CYBER_WALL_SIGN.get(),
				ModBlocks.AUTISM_WALL_SIGN.get(),
				ModBlocks.MARY_WALL_SIGN.get(),
				ModBlocks.ANTHONY_WALL_SIGN.get());
	}
}
