package duperez.moresigns.common.registry;

import com.google.common.collect.Sets;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SignItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import duperez.moresigns.MoreSigns;

import java.util.LinkedHashSet;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MoreSigns.MODID);
    public static LinkedHashSet<RegistryObject<Item>> CREATIVE_TAB_ITEMS = Sets.newLinkedHashSet();

    public static RegistryObject<Item> registerWithTab(final String name, final Supplier<Item> supplier) {
        RegistryObject<Item> block = ITEMS.register(name, supplier);
        CREATIVE_TAB_ITEMS.add(block);
        return block;
    }


    // Helper methods
    public static Item.Properties basicItem() {
        return new Item.Properties();
    }
    public static final RegistryObject<Item> CANVAS_SIGN = registerWithTab("canvas_sign",
            () -> new SignItem(basicItem(), ModBlocks.CANVAS_SIGN.get(), ModBlocks.CANVAS_WALL_SIGN.get()));
    public static final RegistryObject<Item> WHITE_CANVAS_SIGN = registerWithTab("white_canvas_sign",
            () -> new SignItem(basicItem(), ModBlocks.WHITE_CANVAS_SIGN.get(), ModBlocks.WHITE_CANVAS_WALL_SIGN.get()));
    public static final RegistryObject<Item> ORANGE_CANVAS_SIGN = registerWithTab("orange_canvas_sign",
            () -> new SignItem(basicItem(), ModBlocks.ORANGE_CANVAS_SIGN.get(), ModBlocks.ORANGE_CANVAS_WALL_SIGN.get()));
    public static final RegistryObject<Item> MAGENTA_CANVAS_SIGN = registerWithTab("magenta_canvas_sign",
            () -> new SignItem(basicItem(), ModBlocks.MAGENTA_CANVAS_SIGN.get(), ModBlocks.MAGENTA_CANVAS_WALL_SIGN.get()));
    public static final RegistryObject<Item> LIGHT_BLUE_CANVAS_SIGN = registerWithTab("light_blue_canvas_sign",
            () -> new SignItem(basicItem(), ModBlocks.LIGHT_BLUE_CANVAS_SIGN.get(), ModBlocks.LIGHT_BLUE_CANVAS_WALL_SIGN.get()));
    public static final RegistryObject<Item> YELLOW_CANVAS_SIGN = registerWithTab("yellow_canvas_sign",
            () -> new SignItem(basicItem(), ModBlocks.YELLOW_CANVAS_SIGN.get(), ModBlocks.YELLOW_CANVAS_WALL_SIGN.get()));
    public static final RegistryObject<Item> LIME_CANVAS_SIGN = registerWithTab("lime_canvas_sign",
            () -> new SignItem(basicItem(), ModBlocks.LIME_CANVAS_SIGN.get(), ModBlocks.LIME_CANVAS_WALL_SIGN.get()));
    public static final RegistryObject<Item> PINK_CANVAS_SIGN = registerWithTab("pink_canvas_sign",
            () -> new SignItem(basicItem(), ModBlocks.PINK_CANVAS_SIGN.get(), ModBlocks.PINK_CANVAS_WALL_SIGN.get()));
    public static final RegistryObject<Item> GRAY_CANVAS_SIGN = registerWithTab("gray_canvas_sign",
            () -> new SignItem(basicItem(), ModBlocks.GRAY_CANVAS_SIGN.get(), ModBlocks.GRAY_CANVAS_WALL_SIGN.get()));
    public static final RegistryObject<Item> LIGHT_GRAY_CANVAS_SIGN = registerWithTab("light_gray_canvas_sign",
            () -> new SignItem(basicItem(), ModBlocks.LIGHT_GRAY_CANVAS_SIGN.get(), ModBlocks.LIGHT_GRAY_CANVAS_WALL_SIGN.get()));
    public static final RegistryObject<Item> CYAN_CANVAS_SIGN = registerWithTab("cyan_canvas_sign",
            () -> new SignItem(basicItem(), ModBlocks.CYAN_CANVAS_SIGN.get(), ModBlocks.CYAN_CANVAS_WALL_SIGN.get()));
    public static final RegistryObject<Item> PURPLE_CANVAS_SIGN = registerWithTab("purple_canvas_sign",
            () -> new SignItem(basicItem(), ModBlocks.PURPLE_CANVAS_SIGN.get(), ModBlocks.PURPLE_CANVAS_WALL_SIGN.get()));
    public static final RegistryObject<Item> BLUE_CANVAS_SIGN = registerWithTab("blue_canvas_sign",
            () -> new SignItem(basicItem(), ModBlocks.BLUE_CANVAS_SIGN.get(), ModBlocks.BLUE_CANVAS_WALL_SIGN.get()));
    public static final RegistryObject<Item> BROWN_CANVAS_SIGN = registerWithTab("brown_canvas_sign",
            () -> new SignItem(basicItem(), ModBlocks.BROWN_CANVAS_SIGN.get(), ModBlocks.BROWN_CANVAS_WALL_SIGN.get()));
    public static final RegistryObject<Item> GREEN_CANVAS_SIGN = registerWithTab("green_canvas_sign",
            () -> new SignItem(basicItem(), ModBlocks.GREEN_CANVAS_SIGN.get(), ModBlocks.GREEN_CANVAS_WALL_SIGN.get()));
    public static final RegistryObject<Item> RED_CANVAS_SIGN = registerWithTab("red_canvas_sign",
            () -> new SignItem(basicItem(), ModBlocks.RED_CANVAS_SIGN.get(), ModBlocks.RED_CANVAS_WALL_SIGN.get()));
    public static final RegistryObject<Item> BLACK_CANVAS_SIGN = registerWithTab("black_canvas_sign",
            () -> new SignItem(basicItem(), ModBlocks.BLACK_CANVAS_SIGN.get(), ModBlocks.BLACK_CANVAS_WALL_SIGN.get()));
    public static final RegistryObject<Item> ORANGE_PURPLE_CANVAS_SIGN = registerWithTab("orange_purple_canvas_sign",
            () -> new SignItem(basicItem(), ModBlocks.ORANGE_PURPLE_SIGN.get(), ModBlocks.ORANGE_PURPLE_CANVAS_WALL_SIGN.get()));
    public static final RegistryObject<Item> GREEN_GRADIENT_CANVAS_SIGN = registerWithTab("green_gradient_canvas_sign",
            () -> new SignItem(basicItem(), ModBlocks.GREEN_GRADIENT_SIGN.get(), ModBlocks.GREEN_GRADIENT_WALL_SIGN.get()));
    public static final RegistryObject<Item> ANTHONY_CANVAS_SIGN = registerWithTab("anthony_canvas_sign",
            () -> new SignItem(basicItem(), ModBlocks.ANTHONY_SIGN.get(), ModBlocks.ANTHONY_WALL_SIGN.get()));
    public static final RegistryObject<Item> HOTPINK_CANVAS_SIGN = registerWithTab("hotpink_canvas_sign",
            () -> new SignItem(basicItem(), ModBlocks.HOTPINK_SIGN.get(), ModBlocks.HOTPINK_WALL_SIGN.get()));
    public static final RegistryObject<Item> INDIGO_CANVAS_SIGN = registerWithTab("indigo_canvas_sign",
            () -> new SignItem(basicItem(), ModBlocks.INDIGO_SIGN.get(), ModBlocks.INDIGO_WALL_SIGN.get()));
    public static final RegistryObject<Item> LIGHT_PINK_CANVAS_SIGN = registerWithTab("light_pink_canvas_sign",
            () -> new SignItem(basicItem(), ModBlocks.LIGHT_PINK_SIGN.get(), ModBlocks.LIGHT_PINK_WALL_SIGN.get()));
    public static final RegistryObject<Item> LOOSAZ_CANVAS_SIGN = registerWithTab("loosaz_canvas_sign",
            () -> new SignItem(basicItem(), ModBlocks.LOOSAZ_SIGN.get(), ModBlocks.LOOSAZ_WALL_SIGN.get()));
    public static final RegistryObject<Item> SPADES_CANVAS_SIGN = registerWithTab("spades_canvas_sign",
            () -> new SignItem(basicItem(), ModBlocks.SPADES_SIGN.get(), ModBlocks.SPADES_WALL_SIGN.get()));
    public static final RegistryObject<Item> JINX_CANVAS_SIGN = registerWithTab("jinx_canvas_sign",
            () -> new SignItem(basicItem(), ModBlocks.JINX_SIGN.get(), ModBlocks.JINX_WALL_SIGN.get()));
    public static final RegistryObject<Item> ANHANGA_CANVAS_SIGN = registerWithTab("anhanga_canvas_sign",
            () -> new SignItem(basicItem(), ModBlocks.ANHANGA_SIGN.get(), ModBlocks.ANHANGA_WALL_SIGN.get()));
    public static final RegistryObject<Item> GOLD_CANVAS_SIGN = registerWithTab("gold_canvas_sign",
            () -> new SignItem(basicItem(), ModBlocks.GOLD_SIGN.get(), ModBlocks.GOLD_WALL_SIGN.get()));
    public static final RegistryObject<Item> MABHETIEL_CANVAS_SIGN = registerWithTab("mabhetiel_canvas_sign",
            () -> new SignItem(basicItem(), ModBlocks.MABHETIEL_SIGN.get(), ModBlocks.MABHETIEL_WALL_SIGN.get()));
    public static final RegistryObject<Item> POMPOM_CANVAS_SIGN = registerWithTab("pompom_canvas_sign",
            () -> new SignItem(basicItem(), ModBlocks.POMPOM_SIGN.get(), ModBlocks.POMPOM_WALL_SIGN.get()));
    public static final RegistryObject<Item> RABBIT_CANVAS_SIGN = registerWithTab("rabbit_canvas_sign",
            () -> new SignItem(basicItem(), ModBlocks.RABBIT_SIGN.get(), ModBlocks.RABBIT_WALL_SIGN.get()));
    public static final RegistryObject<Item> SODA_CANVAS_SIGN = registerWithTab("soda_canvas_sign",
            () -> new SignItem(basicItem(), ModBlocks.SODA_SIGN.get(), ModBlocks.SODA_WALL_SIGN.get()));
    public static final RegistryObject<Item> CYBER_CANVAS_SIGN = registerWithTab("cyber_canvas_sign",
            () -> new SignItem(basicItem(), ModBlocks.CYBER_SIGN.get(), ModBlocks.CYBER_WALL_SIGN.get()));
    public static final RegistryObject<Item> AUTISM_CANVAS_SIGN = registerWithTab("autism_canvas_sign",
            () -> new SignItem(basicItem(), ModBlocks.AUTISM_SIGN.get(), ModBlocks.AUTISM_WALL_SIGN.get()));
    public static final RegistryObject<Item> MARY_CANVAS_SIGN = registerWithTab("mary_canvas_sign",
            () -> new SignItem(basicItem(), ModBlocks.MARY_SIGN.get(), ModBlocks.MARY_WALL_SIGN.get()));
    public static final RegistryObject<Item> MEG_CANVAS_SIGN = registerWithTab("meg_canvas_sign",
            () -> new SignItem(basicItem(), ModBlocks.MEG_SIGN.get(), ModBlocks.MEG_WALL_SIGN.get()));
    public static final RegistryObject<Item> DELTA_CANVAS_SIGN = registerWithTab("delta_canvas_sign",
            () -> new SignItem(basicItem(), ModBlocks.DELTA_SIGN.get(), ModBlocks.DELTA_WALL_SIGN.get()));
    public static final RegistryObject<Item> JACK_CANVAS_SIGN = registerWithTab("jack_canvas_sign",
            () -> new SignItem(basicItem(), ModBlocks.JACK_SIGN.get(), ModBlocks.JACK_WALL_SIGN.get()));
    public static final RegistryObject<Item> KALYPSO_CANVAS_SIGN = registerWithTab("kalypso_canvas_sign",
            () -> new SignItem(basicItem(), ModBlocks.KALYPSO_SIGN.get(), ModBlocks.KALYPSO_WALL_SIGN.get()));
    public static final RegistryObject<Item> M1_CANVAS_SIGN = registerWithTab("m1_canvas_sign",
            () -> new SignItem(basicItem(), ModBlocks.M1_SIGN.get(), ModBlocks.M1_WALL_SIGN.get()));
    public static final RegistryObject<Item> MICHAEL_CANVAS_SIGN = registerWithTab("michael_canvas_sign",
            () -> new SignItem(basicItem(), ModBlocks.MICHAEL_SIGN.get(), ModBlocks.MICHAEL_WALL_SIGN.get()));
    public static final RegistryObject<Item> MONA_CANVAS_SIGN = registerWithTab("mona_canvas_sign",
            () -> new SignItem(basicItem(), ModBlocks.MONA_SIGN.get(), ModBlocks.MONA_WALL_SIGN.get()));
    public static final RegistryObject<Item> MOON_CANVAS_SIGN = registerWithTab("moon_canvas_sign",
            () -> new SignItem(basicItem(), ModBlocks.MOON_SIGN.get(), ModBlocks.MOON_WALL_SIGN.get()));
    public static final RegistryObject<Item> UKO_CANVAS_SIGN = registerWithTab("uko_canvas_sign",
            () -> new SignItem(basicItem(), ModBlocks.UKO_SIGN.get(), ModBlocks.UKO_WALL_SIGN.get()));
    public static final RegistryObject<Item> JAY_CANVAS_SIGN = registerWithTab("jay_canvas_sign",
            () -> new SignItem(basicItem(), ModBlocks.JAY_SIGN.get(), ModBlocks.JAY_WALL_SIGN.get()));
    public static final RegistryObject<Item> CELYNE_CANVAS_SIGN = registerWithTab("celyne_canvas_sign",
            () -> new SignItem(basicItem(), ModBlocks.CELYNE_SIGN.get(), ModBlocks.CELYNE_WALL_SIGN.get()));
    public static final RegistryObject<Item> PEPPER_CANVAS_SIGN = registerWithTab("pepper_canvas_sign",
            () -> new SignItem(basicItem(), ModBlocks.PEPPER_SIGN.get(), ModBlocks.PEPPER_WALL_SIGN.get()));
    public static final RegistryObject<Item> EPSILON_CANVAS_SIGN = registerWithTab("epsilon_canvas_sign",
            () -> new SignItem(basicItem(), ModBlocks.EPSILON_SIGN.get(), ModBlocks.EPSILON_WALL_SIGN.get()));
    public static final RegistryObject<Item> HAVARTI_CANVAS_SIGN = registerWithTab("havarti_canvas_sign",
            () -> new SignItem(basicItem(), ModBlocks.HAVARTI_SIGN.get(), ModBlocks.HAVARTI_WALL_SIGN.get()));
    public static final RegistryObject<Item> MINERVA_CANVAS_SIGN = registerWithTab("minerva_canvas_sign",
            () -> new SignItem(basicItem(), ModBlocks.MINERVA_SIGN.get(), ModBlocks.MINERVA_WALL_SIGN.get()));
    public static final RegistryObject<Item> RUNE_CANVAS_SIGN = registerWithTab("rune_canvas_sign",
            () -> new SignItem(basicItem(), ModBlocks.RUNE_SIGN.get(), ModBlocks.RUNE_WALL_SIGN.get()));
    public static final RegistryObject<Item> LAMBDA_CANVAS_SIGN = registerWithTab("lambda_canvas_sign",
            () -> new SignItem(basicItem(), ModBlocks.LAMBDA_SIGN.get(), ModBlocks.LAMBDA_WALL_SIGN.get()));
    public static final RegistryObject<Item> OMICRON_CANVAS_SIGN = registerWithTab("omicron_canvas_sign",
            () -> new SignItem(basicItem(), ModBlocks.OMICRON_SIGN.get(), ModBlocks.OMICRON_WALL_SIGN.get()));
    public static final RegistryObject<Item> PSI_CANVAS_SIGN = registerWithTab("psi_canvas_sign",
            () -> new SignItem(basicItem(), ModBlocks.PSI_SIGN.get(), ModBlocks.PSI_WALL_SIGN.get()));
    public static final RegistryObject<Item> TAU_CANVAS_SIGN = registerWithTab("tau_canvas_sign",
            () -> new SignItem(basicItem(), ModBlocks.TAU_SIGN.get(), ModBlocks.TAU_WALL_SIGN.get()));
    public static final RegistryObject<Item> YUN_CANVAS_SIGN = registerWithTab("yun_canvas_sign",
            () -> new SignItem(basicItem(), ModBlocks.YUN_SIGN.get(), ModBlocks.YUN_WALL_SIGN.get()));
    public static final RegistryObject<Item> JUPITER_CANVAS_SIGN = registerWithTab("jupiter_canvas_sign",
            () -> new SignItem(basicItem(), ModBlocks.JUPITER_SIGN.get(), ModBlocks.JUPITER_WALL_SIGN.get()));
    public static final RegistryObject<Item> BENNY_CANVAS_SIGN = registerWithTab("benny_canvas_sign",
            () -> new SignItem(basicItem(), ModBlocks.BENNY_SIGN.get(), ModBlocks.BENNY_WALL_SIGN.get()));
    public static final RegistryObject<Item> TINKER_CANVAS_SIGN = registerWithTab("tinker_canvas_sign",
            () -> new SignItem(basicItem(), ModBlocks.TINKER_SIGN.get(), ModBlocks.TINKER_WALL_SIGN.get()));
    public static final RegistryObject<Item> WICKY_CANVAS_SIGN = registerWithTab("wicky_canvas_sign",
            () -> new SignItem(basicItem(), ModBlocks.WICKY_SIGN.get(), ModBlocks.WICKY_WALL_SIGN.get()));
}
