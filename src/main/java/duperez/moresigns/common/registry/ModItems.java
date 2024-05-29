package duperez.moresigns.common.registry;

import com.google.common.collect.Sets;
import duperez.moresigns.common.item.FuelItem;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
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

    public static final RegistryObject<Item> CANVAS = registerWithTab("canvas", () -> new FuelItem(basicItem(), 400));

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

}
