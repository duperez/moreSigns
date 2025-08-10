package duperez.moresigns.common.registry;

import duperez.moresigns.common.block.CanvasRugBlock;
import duperez.moresigns.common.block.StandingCanvasSignBlock;
import duperez.moresigns.common.block.WallCanvasSignBlock;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import duperez.moresigns.MoreSigns;

import java.util.function.ToIntFunction;

public class ModBlocks
{
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MoreSigns.MODID);

	private static ToIntFunction<BlockState> litBlockEmission(int lightValue) {
		return (state) -> state.getValue(BlockStateProperties.LIT) ? lightValue : 0;
	}
	public static final RegistryObject<Block> CANVAS_RUG = BLOCKS.register("canvas_rug",
			() -> new CanvasRugBlock(Block.Properties.copy(Blocks.WHITE_CARPET).sound(SoundType.GRASS).strength(0.2F)));

	// SIGN BLOCK classes declaration

	public static final RegistryObject<Block> CANVAS_SIGN = BLOCKS.register("canvas_sign",
			() -> new StandingCanvasSignBlock(null));
	public static final RegistryObject<Block> WHITE_CANVAS_SIGN = BLOCKS.register("white_canvas_sign",
			() -> new StandingCanvasSignBlock(DyeColor.WHITE.getName()));
	public static final RegistryObject<Block> ORANGE_CANVAS_SIGN = BLOCKS.register("orange_canvas_sign",
			() -> new StandingCanvasSignBlock(DyeColor.ORANGE.getName()));
	public static final RegistryObject<Block> MAGENTA_CANVAS_SIGN = BLOCKS.register("magenta_canvas_sign",
			() -> new StandingCanvasSignBlock(DyeColor.MAGENTA.getName()));
	public static final RegistryObject<Block> LIGHT_BLUE_CANVAS_SIGN = BLOCKS.register("light_blue_canvas_sign",
			() -> new StandingCanvasSignBlock(DyeColor.LIGHT_BLUE.getName()));
	public static final RegistryObject<Block> YELLOW_CANVAS_SIGN = BLOCKS.register("yellow_canvas_sign",
			() -> new StandingCanvasSignBlock(DyeColor.YELLOW.getName()));
	public static final RegistryObject<Block> LIME_CANVAS_SIGN = BLOCKS.register("lime_canvas_sign",
			() -> new StandingCanvasSignBlock(DyeColor.LIME.getName()));
	public static final RegistryObject<Block> PINK_CANVAS_SIGN = BLOCKS.register("pink_canvas_sign",
			() -> new StandingCanvasSignBlock(DyeColor.PINK.getName()));
	public static final RegistryObject<Block> GRAY_CANVAS_SIGN = BLOCKS.register("gray_canvas_sign",
			() -> new StandingCanvasSignBlock(DyeColor.GRAY.getName()));
	public static final RegistryObject<Block> LIGHT_GRAY_CANVAS_SIGN = BLOCKS.register("light_gray_canvas_sign",
			() -> new StandingCanvasSignBlock(DyeColor.LIGHT_GRAY.getName()));
	public static final RegistryObject<Block> CYAN_CANVAS_SIGN = BLOCKS.register("cyan_canvas_sign",
			() -> new StandingCanvasSignBlock(DyeColor.CYAN.getName()));
	public static final RegistryObject<Block> PURPLE_CANVAS_SIGN = BLOCKS.register("purple_canvas_sign",
			() -> new StandingCanvasSignBlock(DyeColor.PURPLE.getName()));
	public static final RegistryObject<Block> BLUE_CANVAS_SIGN = BLOCKS.register("blue_canvas_sign",
			() -> new StandingCanvasSignBlock(DyeColor.BLUE.getName()));
	public static final RegistryObject<Block> BROWN_CANVAS_SIGN = BLOCKS.register("brown_canvas_sign",
			() -> new StandingCanvasSignBlock(DyeColor.BROWN.getName()));
	public static final RegistryObject<Block> GREEN_CANVAS_SIGN = BLOCKS.register("green_canvas_sign",
			() -> new StandingCanvasSignBlock(DyeColor.GREEN.getName()));
	public static final RegistryObject<Block> RED_CANVAS_SIGN = BLOCKS.register("red_canvas_sign",
			() -> new StandingCanvasSignBlock(DyeColor.RED.getName()));
	public static final RegistryObject<Block> BLACK_CANVAS_SIGN = BLOCKS.register("black_canvas_sign",
			() -> new StandingCanvasSignBlock(DyeColor.BLACK.getName()));
	public static final RegistryObject<Block> ORANGE_PURPLE_SIGN = BLOCKS.register("orange_purple_canvas_sign",
			() -> new StandingCanvasSignBlock("orange_purple"));
	public static final RegistryObject<Block> GREEN_GRADIENT_SIGN = BLOCKS.register("green_gradient_canvas_sign",
			() -> new StandingCanvasSignBlock("green_gradient"));
	public static final RegistryObject<Block> ANTHONY_SIGN = BLOCKS.register("anthony_canvas_sign",
			() -> new StandingCanvasSignBlock("anthony"));
	public static final RegistryObject<Block> HOTPINK_SIGN = BLOCKS.register("hotpink_canvas_sign",
			() -> new StandingCanvasSignBlock("hotpink"));
	public static final RegistryObject<Block> INDIGO_SIGN = BLOCKS.register("indigo_canvas_sign",
			() -> new StandingCanvasSignBlock("indigo"));
	public static final RegistryObject<Block> LIGHT_PINK_SIGN = BLOCKS.register("light_pink_canvas_sign",
			() -> new StandingCanvasSignBlock("light_pink"));
	public static final RegistryObject<Block> LOOSAZ_SIGN = BLOCKS.register("loosaz_canvas_sign",
			() -> new StandingCanvasSignBlock("loosaz"));
	public static final RegistryObject<Block> SPADES_SIGN = BLOCKS.register("spades_canvas_sign",
			() -> new StandingCanvasSignBlock("spades"));
	public static final RegistryObject<Block> JINX_SIGN = BLOCKS.register("jinx_canvas_sign",
			() -> new StandingCanvasSignBlock("jinx"));
	public static final RegistryObject<Block> ANHANGA_SIGN = BLOCKS.register("anhanga_canvas_sign",
			() -> new StandingCanvasSignBlock("anhanga"));
	public static final RegistryObject<Block> GOLD_SIGN = BLOCKS.register("gold_canvas_sign",
			() -> new StandingCanvasSignBlock("gold"));
	public static final RegistryObject<Block> MABHETIEL_SIGN = BLOCKS.register("mabhetiel_canvas_sign",
			() -> new StandingCanvasSignBlock("mabhetiel"));
	public static final RegistryObject<Block> POMPOM_SIGN = BLOCKS.register("pompom_canvas_sign",
			() -> new StandingCanvasSignBlock("pompom"));
	public static final RegistryObject<Block> RABBIT_SIGN = BLOCKS.register("rabbit_canvas_sign",
			() -> new StandingCanvasSignBlock("rabbit"));
	public static final RegistryObject<Block> SODA_SIGN = BLOCKS.register("soda_canvas_sign",
			() -> new StandingCanvasSignBlock("soda"));
	public static final RegistryObject<Block> CYBER_SIGN = BLOCKS.register("cyber_canvas_sign",
			() -> new StandingCanvasSignBlock("cyber"));
	public static final RegistryObject<Block> AUTISM_SIGN = BLOCKS.register("autism_canvas_sign",
			() -> new StandingCanvasSignBlock("autism"));
	public static final RegistryObject<Block> MARY_SIGN = BLOCKS.register("mary_canvas_sign",
			() -> new StandingCanvasSignBlock("mary"));
	public static final RegistryObject<Block> MEG_SIGN = BLOCKS.register("meg_canvas_sign",
			() -> new StandingCanvasSignBlock("meg"));
	public static final RegistryObject<Block> DELTA_SIGN = BLOCKS.register("delta_canvas_sign",
			() -> new StandingCanvasSignBlock("delta"));
	public static final RegistryObject<Block> JACK_SIGN = BLOCKS.register("jack_canvas_sign",
			() -> new StandingCanvasSignBlock("jack"));
	public static final RegistryObject<Block> KALYPSO_SIGN = BLOCKS.register("kalypso_canvas_sign",
			() -> new StandingCanvasSignBlock("kalypso"));
	public static final RegistryObject<Block> M1_SIGN = BLOCKS.register("m1_canvas_sign",
			() -> new StandingCanvasSignBlock("m1"));
	public static final RegistryObject<Block> MICHAEL_SIGN = BLOCKS.register("michael_canvas_sign",
			() -> new StandingCanvasSignBlock("michael"));
	public static final RegistryObject<Block> MONA_SIGN = BLOCKS.register("mona_canvas_sign",
			() -> new StandingCanvasSignBlock("mona"));
	public static final RegistryObject<Block> MOON_SIGN = BLOCKS.register("moon_canvas_sign",
			() -> new StandingCanvasSignBlock("moon"));
	public static final RegistryObject<Block> UKO_SIGN = BLOCKS.register("uko_canvas_sign",
			() -> new StandingCanvasSignBlock("uko"));
	public static final RegistryObject<Block> JAY_SIGN = BLOCKS.register("jay_canvas_sign",
			() -> new StandingCanvasSignBlock("jay"));
	public static final RegistryObject<Block> CELYNE_SIGN = BLOCKS.register("celyne_canvas_sign",
			() -> new StandingCanvasSignBlock("celyne"));
	public static final RegistryObject<Block> PEPPER_SIGN = BLOCKS.register("pepper_canvas_sign",
			() -> new StandingCanvasSignBlock("pepper"));
	public static final RegistryObject<Block> EPSILON_SIGN = BLOCKS.register("epsilon_canvas_sign",
			() -> new StandingCanvasSignBlock("epsilon"));
	public static final RegistryObject<Block> HAVARTI_SIGN = BLOCKS.register("havarti_canvas_sign",
			() -> new StandingCanvasSignBlock("havarti"));
	public static final RegistryObject<Block> MINERVA_SIGN = BLOCKS.register("minerva_canvas_sign",
			() -> new StandingCanvasSignBlock("minerva"));
	public static final RegistryObject<Block> RUNE_SIGN = BLOCKS.register("rune_canvas_sign",
			() -> new StandingCanvasSignBlock("rune"));
	public static final RegistryObject<Block> LAMBDA_SIGN = BLOCKS.register("lambda_canvas_sign",
			() -> new StandingCanvasSignBlock("lambda"));
	public static final RegistryObject<Block> OMICRON_SIGN = BLOCKS.register("omicron_canvas_sign",
			() -> new StandingCanvasSignBlock("omicron"));
	public static final RegistryObject<Block> PSI_SIGN = BLOCKS.register("psi_canvas_sign",
			() -> new StandingCanvasSignBlock("psi"));
	public static final RegistryObject<Block> TAU_SIGN = BLOCKS.register("tau_canvas_sign",
			() -> new StandingCanvasSignBlock("tau"));
	public static final RegistryObject<Block> WICKY_SIGN = BLOCKS.register("wicky_canvas_sign",
			() -> new StandingCanvasSignBlock("wicky"));
	public static final RegistryObject<Block> YUN_SIGN = BLOCKS.register("yun_canvas_sign",
			() -> new StandingCanvasSignBlock("yun"));
	public static final RegistryObject<Block> JUPITER_SIGN = BLOCKS.register("jupiter_canvas_sign",
			() -> new StandingCanvasSignBlock("jupiter"));
	public static final RegistryObject<Block> BENNY_SIGN = BLOCKS.register("benny_canvas_sign",
			() -> new StandingCanvasSignBlock("benny"));
	public static final RegistryObject<Block> TINKER_SIGN = BLOCKS.register("tinker_canvas_sign",
			() -> new StandingCanvasSignBlock("tinker"));

	// WALL_SIGN classes declaration

	public static final RegistryObject<Block> CANVAS_WALL_SIGN = BLOCKS.register("canvas_wall_sign",
			() -> new WallCanvasSignBlock(Block.Properties.copy(Blocks.OAK_SIGN).lootFrom(CANVAS_SIGN)));
	public static final RegistryObject<Block> WHITE_CANVAS_WALL_SIGN = BLOCKS.register("white_canvas_wall_sign",
			() -> new WallCanvasSignBlock(Block.Properties.copy(Blocks.OAK_SIGN).lootFrom(WHITE_CANVAS_SIGN), DyeColor.WHITE));
	public static final RegistryObject<Block> ORANGE_CANVAS_WALL_SIGN = BLOCKS.register("orange_canvas_wall_sign",
			() -> new WallCanvasSignBlock(Block.Properties.copy(Blocks.OAK_SIGN).lootFrom(ORANGE_CANVAS_SIGN), DyeColor.ORANGE));
	public static final RegistryObject<Block> MAGENTA_CANVAS_WALL_SIGN = BLOCKS.register("magenta_canvas_wall_sign",
			() -> new WallCanvasSignBlock(Block.Properties.copy(Blocks.OAK_SIGN).lootFrom(MAGENTA_CANVAS_SIGN), DyeColor.MAGENTA));
	public static final RegistryObject<Block> LIGHT_BLUE_CANVAS_WALL_SIGN = BLOCKS.register("light_blue_canvas_wall_sign",
			() -> new WallCanvasSignBlock(Block.Properties.copy(Blocks.OAK_SIGN).lootFrom(LIGHT_BLUE_CANVAS_SIGN), DyeColor.LIGHT_BLUE));
	public static final RegistryObject<Block> YELLOW_CANVAS_WALL_SIGN = BLOCKS.register("yellow_canvas_wall_sign",
			() -> new WallCanvasSignBlock(Block.Properties.copy(Blocks.OAK_SIGN).lootFrom(YELLOW_CANVAS_SIGN), DyeColor.YELLOW));
	public static final RegistryObject<Block> LIME_CANVAS_WALL_SIGN = BLOCKS.register("lime_canvas_wall_sign",
			() -> new WallCanvasSignBlock(Block.Properties.copy(Blocks.OAK_SIGN).lootFrom(LIME_CANVAS_SIGN), DyeColor.LIME));
	public static final RegistryObject<Block> PINK_CANVAS_WALL_SIGN = BLOCKS.register("pink_canvas_wall_sign",
			() -> new WallCanvasSignBlock(Block.Properties.copy(Blocks.OAK_SIGN).lootFrom(PINK_CANVAS_SIGN), DyeColor.PINK));
	public static final RegistryObject<Block> GRAY_CANVAS_WALL_SIGN = BLOCKS.register("gray_canvas_wall_sign",
			() -> new WallCanvasSignBlock(Block.Properties.copy(Blocks.OAK_SIGN).lootFrom(GRAY_CANVAS_SIGN), DyeColor.GRAY));
	public static final RegistryObject<Block> LIGHT_GRAY_CANVAS_WALL_SIGN = BLOCKS.register("light_gray_canvas_wall_sign",
			() -> new WallCanvasSignBlock(Block.Properties.copy(Blocks.OAK_SIGN).lootFrom(LIGHT_GRAY_CANVAS_SIGN), DyeColor.LIGHT_GRAY));
	public static final RegistryObject<Block> CYAN_CANVAS_WALL_SIGN = BLOCKS.register("cyan_canvas_wall_sign",
			() -> new WallCanvasSignBlock(Block.Properties.copy(Blocks.OAK_SIGN).lootFrom(CYAN_CANVAS_SIGN), DyeColor.CYAN));
	public static final RegistryObject<Block> PURPLE_CANVAS_WALL_SIGN = BLOCKS.register("purple_canvas_wall_sign",
			() -> new WallCanvasSignBlock(Block.Properties.copy(Blocks.OAK_SIGN).lootFrom(PURPLE_CANVAS_SIGN), DyeColor.PURPLE));
	public static final RegistryObject<Block> BLUE_CANVAS_WALL_SIGN = BLOCKS.register("blue_canvas_wall_sign",
			() -> new WallCanvasSignBlock(Block.Properties.copy(Blocks.OAK_SIGN).lootFrom(BLUE_CANVAS_SIGN), DyeColor.BLUE));
	public static final RegistryObject<Block> BROWN_CANVAS_WALL_SIGN = BLOCKS.register("brown_canvas_wall_sign",
			() -> new WallCanvasSignBlock(Block.Properties.copy(Blocks.OAK_SIGN).lootFrom(BROWN_CANVAS_SIGN), DyeColor.BROWN));
	public static final RegistryObject<Block> GREEN_CANVAS_WALL_SIGN = BLOCKS.register("green_canvas_wall_sign",
			() -> new WallCanvasSignBlock(Block.Properties.copy(Blocks.OAK_SIGN).lootFrom(GREEN_CANVAS_SIGN), DyeColor.GREEN));
	public static final RegistryObject<Block> RED_CANVAS_WALL_SIGN = BLOCKS.register("red_canvas_wall_sign",
			() -> new WallCanvasSignBlock(Block.Properties.copy(Blocks.OAK_SIGN).lootFrom(RED_CANVAS_SIGN), DyeColor.RED));
	public static final RegistryObject<Block> BLACK_CANVAS_WALL_SIGN = BLOCKS.register("black_canvas_wall_sign",
			() -> new WallCanvasSignBlock(Block.Properties.copy(Blocks.OAK_SIGN).lootFrom(BLACK_CANVAS_SIGN), DyeColor.BLACK));
	public static final RegistryObject<Block> ORANGE_PURPLE_CANVAS_WALL_SIGN = BLOCKS.register("orange_purple_canvas_wall_sign",
			() -> new WallCanvasSignBlock(Block.Properties.copy(Blocks.OAK_SIGN).lootFrom(ORANGE_PURPLE_SIGN), "orange_purple"));
	public static final RegistryObject<Block> GREEN_GRADIENT_WALL_SIGN = BLOCKS.register("green_gradient_canvas_wall_sign",
			() -> new WallCanvasSignBlock(Block.Properties.copy(Blocks.OAK_SIGN).lootFrom(GREEN_GRADIENT_SIGN), "green_gradient"));
	public static final RegistryObject<Block> ANTHONY_WALL_SIGN = BLOCKS.register("anthony_canvas_wall_sign",
			() -> new WallCanvasSignBlock(Block.Properties.copy(Blocks.OAK_SIGN).lootFrom(ANTHONY_SIGN), "anthony"));
	public static final RegistryObject<Block> HOTPINK_WALL_SIGN = BLOCKS.register("hotpink_canvas_wall_sign",
			() -> new WallCanvasSignBlock(Block.Properties.copy(Blocks.OAK_SIGN).lootFrom(HOTPINK_SIGN), "hotpink"));
	public static final RegistryObject<Block> INDIGO_WALL_SIGN = BLOCKS.register("indigo_canvas_wall_sign",
			() -> new WallCanvasSignBlock(Block.Properties.copy(Blocks.OAK_SIGN).lootFrom(INDIGO_SIGN), "indigo"));
	public static final RegistryObject<Block> LIGHT_PINK_WALL_SIGN = BLOCKS.register("light_pink_canvas_wall_sign",
			() -> new WallCanvasSignBlock(Block.Properties.copy(Blocks.OAK_SIGN).lootFrom(LIGHT_PINK_SIGN), "light_pink"));
	public static final RegistryObject<Block> LOOSAZ_WALL_SIGN = BLOCKS.register("loosaz_canvas_wall_sign",
			() -> new WallCanvasSignBlock(Block.Properties.copy(Blocks.OAK_SIGN).lootFrom(LOOSAZ_SIGN), "loosaz"));
	public static final RegistryObject<Block> SPADES_WALL_SIGN = BLOCKS.register("spades_canvas_wall_sign",
			() -> new WallCanvasSignBlock(Block.Properties.copy(Blocks.OAK_SIGN).lootFrom(SPADES_SIGN), "spades"));
	public static final RegistryObject<Block> JINX_WALL_SIGN = BLOCKS.register("jinx_canvas_wall_sign",
			() -> new WallCanvasSignBlock(Block.Properties.copy(Blocks.OAK_SIGN).lootFrom(JINX_SIGN), "jinx"));
	public static final RegistryObject<Block> ANHANGA_WALL_SIGN = BLOCKS.register("anhanga_canvas_wall_sign",
			() -> new WallCanvasSignBlock(Block.Properties.copy(Blocks.OAK_SIGN).lootFrom(ANHANGA_SIGN), "anhanga"));
	public static final RegistryObject<Block> GOLD_WALL_SIGN = BLOCKS.register("gold_canvas_wall_sign",
			() -> new WallCanvasSignBlock(Block.Properties.copy(Blocks.OAK_SIGN).lootFrom(GOLD_SIGN), "gold"));
	public static final RegistryObject<Block> MABHETIEL_WALL_SIGN = BLOCKS.register("mabhetiel_canvas_wall_sign",
			() -> new WallCanvasSignBlock(Block.Properties.copy(Blocks.OAK_SIGN).lootFrom(MABHETIEL_SIGN), "mabhetiel"));
	public static final RegistryObject<Block> POMPOM_WALL_SIGN = BLOCKS.register("pompom_canvas_wall_sign",
			() -> new WallCanvasSignBlock(Block.Properties.copy(Blocks.OAK_SIGN).lootFrom(POMPOM_SIGN), "pompom"));
	public static final RegistryObject<Block> RABBIT_WALL_SIGN = BLOCKS.register("rabbit_canvas_wall_sign",
			() -> new WallCanvasSignBlock(Block.Properties.copy(Blocks.OAK_SIGN).lootFrom(RABBIT_SIGN), "rabbit"));
	public static final RegistryObject<Block> SODA_WALL_SIGN = BLOCKS.register("soda_canvas_wall_sign",
			() -> new WallCanvasSignBlock(Block.Properties.copy(Blocks.OAK_SIGN).lootFrom(SODA_SIGN), "soda"));
	public static final RegistryObject<Block> CYBER_WALL_SIGN = BLOCKS.register("cyber_canvas_wall_sign",
			() -> new WallCanvasSignBlock(Block.Properties.copy(Blocks.OAK_SIGN).lootFrom(CYBER_SIGN), "cyber"));
	public static final RegistryObject<Block> AUTISM_WALL_SIGN = BLOCKS.register("autism_canvas_wall_sign",
			() -> new WallCanvasSignBlock(Block.Properties.copy(Blocks.OAK_SIGN).lootFrom(AUTISM_SIGN), "autism"));
	public static final RegistryObject<Block> MARY_WALL_SIGN = BLOCKS.register("mary_canvas_wall_sign",
			() -> new WallCanvasSignBlock(Block.Properties.copy(Blocks.OAK_SIGN).lootFrom(MARY_SIGN), "mary"));
	public static final RegistryObject<Block> MEG_WALL_SIGN = BLOCKS.register("meg_canvas_wall_sign",
			() -> new WallCanvasSignBlock(Block.Properties.copy(Blocks.OAK_SIGN).lootFrom(MEG_SIGN), "meg"));
	public static final RegistryObject<Block> DELTA_WALL_SIGN = BLOCKS.register("delta_canvas_wall_sign",
			() -> new WallCanvasSignBlock(Block.Properties.copy(Blocks.OAK_SIGN).lootFrom(DELTA_SIGN), "delta"));
	public static final RegistryObject<Block> JACK_WALL_SIGN = BLOCKS.register("jack_canvas_wall_sign",
			() -> new WallCanvasSignBlock(Block.Properties.copy(Blocks.OAK_SIGN).lootFrom(JACK_SIGN), "jack"));
	public static final RegistryObject<Block> KALYPSO_WALL_SIGN = BLOCKS.register("kalypso_canvas_wall_sign",
			() -> new WallCanvasSignBlock(Block.Properties.copy(Blocks.OAK_SIGN).lootFrom(KALYPSO_SIGN), "kalypso"));
	public static final RegistryObject<Block> M1_WALL_SIGN = BLOCKS.register("m1_canvas_wall_sign",
			() -> new WallCanvasSignBlock(Block.Properties.copy(Blocks.OAK_SIGN).lootFrom(M1_SIGN), "m1"));
	public static final RegistryObject<Block> MICHAEL_WALL_SIGN = BLOCKS.register("michael_canvas_wall_sign",
			() -> new WallCanvasSignBlock(Block.Properties.copy(Blocks.OAK_SIGN).lootFrom(MICHAEL_SIGN), "michael"));
	public static final RegistryObject<Block> MONA_WALL_SIGN = BLOCKS.register("mona_canvas_wall_sign",
			() -> new WallCanvasSignBlock(Block.Properties.copy(Blocks.OAK_SIGN).lootFrom(MONA_SIGN), "mona"));
	public static final RegistryObject<Block> MOON_WALL_SIGN = BLOCKS.register("moon_canvas_wall_sign",
			() -> new WallCanvasSignBlock(Block.Properties.copy(Blocks.OAK_SIGN).lootFrom(MOON_SIGN), "moon"));
	public static final RegistryObject<Block> UKO_WALL_SIGN = BLOCKS.register("uko_canvas_wall_sign",
			() -> new WallCanvasSignBlock(Block.Properties.copy(Blocks.OAK_SIGN).lootFrom(UKO_SIGN), "uko"));
	public static final RegistryObject<Block> JAY_WALL_SIGN = BLOCKS.register("jay_canvas_wall_sign",
			() -> new WallCanvasSignBlock(Block.Properties.copy(Blocks.OAK_SIGN).lootFrom(JAY_SIGN), "jay"));
	public static final RegistryObject<Block> CELYNE_WALL_SIGN = BLOCKS.register("celyne_canvas_wall_sign",
			() -> new WallCanvasSignBlock(Block.Properties.copy(Blocks.OAK_SIGN).lootFrom(CELYNE_SIGN), "celyne"));
	public static final RegistryObject<Block> PEPPER_WALL_SIGN = BLOCKS.register("pepper_canvas_wall_sign",
			() -> new WallCanvasSignBlock(Block.Properties.copy(Blocks.OAK_SIGN).lootFrom(PEPPER_SIGN), "pepper"));
	public static final RegistryObject<Block> EPSILON_WALL_SIGN = BLOCKS.register("epsilon_canvas_wall_sign",
			() -> new WallCanvasSignBlock(Block.Properties.copy(Blocks.OAK_SIGN).lootFrom(EPSILON_SIGN), "epsilon"));
	public static final RegistryObject<Block> HAVARTI_WALL_SIGN = BLOCKS.register("havarti_canvas_wall_sign",
			() -> new WallCanvasSignBlock(Block.Properties.copy(Blocks.OAK_SIGN).lootFrom(HAVARTI_SIGN), "havarti"));
	public static final RegistryObject<Block> MINERVA_WALL_SIGN = BLOCKS.register("minerva_canvas_wall_sign",
			() -> new WallCanvasSignBlock(Block.Properties.copy(Blocks.OAK_SIGN).lootFrom(MINERVA_SIGN), "minerva"));
	public static final RegistryObject<Block> RUNE_WALL_SIGN = BLOCKS.register("rune_canvas_wall_sign",
			() -> new WallCanvasSignBlock(Block.Properties.copy(Blocks.OAK_SIGN).lootFrom(RUNE_SIGN), "rune"));
	public static final RegistryObject<Block> LAMBDA_WALL_SIGN = BLOCKS.register("lambda_canvas_wall_sign",
			() -> new WallCanvasSignBlock(Block.Properties.copy(Blocks.OAK_SIGN).lootFrom(LAMBDA_SIGN), "lambda"));
	public static final RegistryObject<Block> OMICRON_WALL_SIGN = BLOCKS.register("omicron_canvas_wall_sign",
			() -> new WallCanvasSignBlock(Block.Properties.copy(Blocks.OAK_SIGN).lootFrom(OMICRON_SIGN), "omicron"));
	public static final RegistryObject<Block> PSI_WALL_SIGN = BLOCKS.register("psi_canvas_wall_sign",
			() -> new WallCanvasSignBlock(Block.Properties.copy(Blocks.OAK_SIGN).lootFrom(PSI_SIGN), "psi"));
	public static final RegistryObject<Block> TAU_WALL_SIGN = BLOCKS.register("tau_canvas_wall_sign",
			() -> new WallCanvasSignBlock(Block.Properties.copy(Blocks.OAK_SIGN).lootFrom(TAU_SIGN), "tau"));
	public static final RegistryObject<Block> WICKY_WALL_SIGN = BLOCKS.register("wicky_canvas_wall_sign",
			() -> new WallCanvasSignBlock(Block.Properties.copy(Blocks.OAK_SIGN).lootFrom(WICKY_SIGN), "wicky"));
	public static final RegistryObject<Block> YUN_WALL_SIGN = BLOCKS.register("yun_canvas_wall_sign",
			() -> new WallCanvasSignBlock(Block.Properties.copy(Blocks.OAK_SIGN).lootFrom(YUN_SIGN), "yun"));
	public static final RegistryObject<Block> JUPITER_WALL_SIGN = BLOCKS.register("jupiter_canvas_wall_sign",
			() -> new WallCanvasSignBlock(Block.Properties.copy(Blocks.OAK_SIGN).lootFrom(JUPITER_SIGN), "jupiter"));
	public static final RegistryObject<Block> BENNY_WALL_SIGN = BLOCKS.register("benny_canvas_wall_sign",
			() -> new WallCanvasSignBlock(Block.Properties.copy(Blocks.OAK_SIGN).lootFrom(BENNY_SIGN), "benny"));
	public static final RegistryObject<Block> TINKER_WALL_SIGN = BLOCKS.register("tinker_canvas_wall_sign",
			() -> new WallCanvasSignBlock(Block.Properties.copy(Blocks.OAK_SIGN).lootFrom(TINKER_SIGN), "tinker"));

}
