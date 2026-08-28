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
				.add(ModItems.JAY_CANVAS_SIGN.get())
				.add(ModItems.CELYNE_CANVAS_SIGN.get())
				.add(ModItems.PEPPER_CANVAS_SIGN.get())
				.add(ModItems.EPSILON_CANVAS_SIGN.get())
				.add(ModItems.HAVARTI_CANVAS_SIGN.get())
				.add(ModItems.MINERVA_CANVAS_SIGN.get())
				.add(ModItems.RUNE_CANVAS_SIGN.get())
				.add(ModItems.LAMBDA_CANVAS_SIGN.get())
				.add(ModItems.OMICRON_CANVAS_SIGN.get())
				.add(ModItems.PSI_CANVAS_SIGN.get())
				.add(ModItems.TAU_CANVAS_SIGN.get())
				.add(ModItems.WICKY_CANVAS_SIGN.get())
				.add(ModItems.YUN_CANVAS_SIGN.get())
				.add(ModItems.JUPITER_CANVAS_SIGN.get())
				.add(ModItems.BENNY_CANVAS_SIGN.get())
				.add(ModItems.TINKER_CANVAS_SIGN.get())
				.add(ModItems.ABADOM_CANVAS_SIGN.get())
				.add(ModItems.APOLIOM_CANVAS_SIGN.get())
				.add(ModItems.CLOWN_CANVAS_SIGN.get())
				.add(ModItems.SILVIR_CANVAS_SIGN.get())
				.add(ModItems.MONSIEUR_CANVAS_SIGN.get())
				.add(ModItems.INKARIS_CANVAS_SIGN.get())
				.add(ModItems.LOKY_CANVAS_SIGN.get())
				.add(ModItems.DESGRACA_CANVAS_SIGN.get())
				.add(ModItems.AALIYAH_CANVAS_SIGN.get())
				.add(ModItems.ALBIOM_CANVAS_SIGN.get())
				.add(ModItems.LUIS_CANVAS_SIGN.get())
				.add(ModItems.RONALD_CANVAS_SIGN.get())
				.add(ModItems.SCRATCH_CANVAS_SIGN.get())
				.add(ModItems.MATIAS_CANVAS_SIGN.get())
				.add(ModItems.SARAH_CANVAS_SIGN.get())
				.add(ModItems.INDIBOT_CANVAS_SIGN.get())
				.add(ModItems.YAN_CANVAS_SIGN.get())
				.add(ModItems.AVALOM_CANVAS_SIGN.get())
				.add(ModItems.LOBO_CANVAS_SIGN.get())
				.add(ModItems.GUIDE_CANVAS_SIGN.get())
				.add(ModItems.ELYOLAI_CANVAS_SIGN.get())
				.add(ModItems.KUZCO_CANVAS_SIGN.get())
				.add(ModItems.PATO_CANVAS_SIGN.get())
				.add(ModItems.KEEPER_CANVAS_SIGN.get())
				.add(ModItems.ZEN_CANVAS_SIGN.get())
				.add(ModItems.ECHO_CANVAS_SIGN.get())
				.add(ModItems.BLACK_CANVAS_SIGN.get());
	}
}
