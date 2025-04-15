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
}
