package duperez.moresigns.common.registry;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SignItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import duperez.moresigns.MoreSigns;

@SuppressWarnings("unused")
public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MoreSigns.MODID);

    // Helper methods
    public static Item.Properties basicItem() {
        return new Item.Properties().tab(MoreSigns.CREATIVE_TAB);
    }

    public static Item.Properties foodItem(FoodProperties food) {
        return new Item.Properties().food(food).tab(MoreSigns.CREATIVE_TAB);
    }

    public static Item.Properties bowlFoodItem(FoodProperties food) {
        return new Item.Properties().food(food).craftRemainder(Items.BOWL).stacksTo(16).tab(MoreSigns.CREATIVE_TAB);
    }

    public static Item.Properties drinkItem() {
        return new Item.Properties().craftRemainder(Items.GLASS_BOTTLE).stacksTo(16).tab(MoreSigns.CREATIVE_TAB);
    }

    public static final RegistryObject<Item> CANVAS_SIGN = ITEMS.register("canvas_sign",
            () -> new SignItem(basicItem(), ModBlocks.CANVAS_SIGN.get(), ModBlocks.CANVAS_WALL_SIGN.get()));
    public static final RegistryObject<Item> WHITE_CANVAS_SIGN = ITEMS.register("white_canvas_sign",
            () -> new SignItem(basicItem(), ModBlocks.WHITE_CANVAS_SIGN.get(), ModBlocks.WHITE_CANVAS_WALL_SIGN.get()));
    public static final RegistryObject<Item> ORANGE_CANVAS_SIGN = ITEMS.register("orange_canvas_sign",
            () -> new SignItem(basicItem(), ModBlocks.ORANGE_CANVAS_SIGN.get(), ModBlocks.ORANGE_CANVAS_WALL_SIGN.get()));
    public static final RegistryObject<Item> MAGENTA_CANVAS_SIGN = ITEMS.register("magenta_canvas_sign",
            () -> new SignItem(basicItem(), ModBlocks.MAGENTA_CANVAS_SIGN.get(), ModBlocks.MAGENTA_CANVAS_WALL_SIGN.get()));
    public static final RegistryObject<Item> LIGHT_BLUE_CANVAS_SIGN = ITEMS.register("light_blue_canvas_sign",
            () -> new SignItem(basicItem(), ModBlocks.LIGHT_BLUE_CANVAS_SIGN.get(), ModBlocks.LIGHT_BLUE_CANVAS_WALL_SIGN.get()));
    public static final RegistryObject<Item> YELLOW_CANVAS_SIGN = ITEMS.register("yellow_canvas_sign",
            () -> new SignItem(basicItem(), ModBlocks.YELLOW_CANVAS_SIGN.get(), ModBlocks.YELLOW_CANVAS_WALL_SIGN.get()));
    public static final RegistryObject<Item> LIME_CANVAS_SIGN = ITEMS.register("lime_canvas_sign",
            () -> new SignItem(basicItem(), ModBlocks.LIME_CANVAS_SIGN.get(), ModBlocks.LIME_CANVAS_WALL_SIGN.get()));
    public static final RegistryObject<Item> PINK_CANVAS_SIGN = ITEMS.register("pink_canvas_sign",
            () -> new SignItem(basicItem(), ModBlocks.PINK_CANVAS_SIGN.get(), ModBlocks.PINK_CANVAS_WALL_SIGN.get()));
    public static final RegistryObject<Item> GRAY_CANVAS_SIGN = ITEMS.register("gray_canvas_sign",
            () -> new SignItem(basicItem(), ModBlocks.GRAY_CANVAS_SIGN.get(), ModBlocks.GRAY_CANVAS_WALL_SIGN.get()));
    public static final RegistryObject<Item> LIGHT_GRAY_CANVAS_SIGN = ITEMS.register("light_gray_canvas_sign",
            () -> new SignItem(basicItem(), ModBlocks.LIGHT_GRAY_CANVAS_SIGN.get(), ModBlocks.LIGHT_GRAY_CANVAS_WALL_SIGN.get()));
    public static final RegistryObject<Item> CYAN_CANVAS_SIGN = ITEMS.register("cyan_canvas_sign",
            () -> new SignItem(basicItem(), ModBlocks.CYAN_CANVAS_SIGN.get(), ModBlocks.CYAN_CANVAS_WALL_SIGN.get()));
    public static final RegistryObject<Item> PURPLE_CANVAS_SIGN = ITEMS.register("purple_canvas_sign",
            () -> new SignItem(basicItem(), ModBlocks.PURPLE_CANVAS_SIGN.get(), ModBlocks.PURPLE_CANVAS_WALL_SIGN.get()));
    public static final RegistryObject<Item> BLUE_CANVAS_SIGN = ITEMS.register("blue_canvas_sign",
            () -> new SignItem(basicItem(), ModBlocks.BLUE_CANVAS_SIGN.get(), ModBlocks.BLUE_CANVAS_WALL_SIGN.get()));
    public static final RegistryObject<Item> BROWN_CANVAS_SIGN = ITEMS.register("brown_canvas_sign",
            () -> new SignItem(basicItem(), ModBlocks.BROWN_CANVAS_SIGN.get(), ModBlocks.BROWN_CANVAS_WALL_SIGN.get()));
    public static final RegistryObject<Item> GREEN_CANVAS_SIGN = ITEMS.register("green_canvas_sign",
            () -> new SignItem(basicItem(), ModBlocks.GREEN_CANVAS_SIGN.get(), ModBlocks.GREEN_CANVAS_WALL_SIGN.get()));
    public static final RegistryObject<Item> RED_CANVAS_SIGN = ITEMS.register("red_canvas_sign",
            () -> new SignItem(basicItem(), ModBlocks.RED_CANVAS_SIGN.get(), ModBlocks.RED_CANVAS_WALL_SIGN.get()));
    public static final RegistryObject<Item> BLACK_CANVAS_SIGN = ITEMS.register("black_canvas_sign",
            () -> new SignItem(basicItem(), ModBlocks.BLACK_CANVAS_SIGN.get(), ModBlocks.BLACK_CANVAS_WALL_SIGN.get()));

    public static final RegistryObject<Item> ORANGE_PURPLE_CANVAS_SIGN = ITEMS.register("orange_purple_canvas_sign",
            () -> new SignItem(basicItem(), ModBlocks.ORANGE_PURPLE_SIGN.get(), ModBlocks.ORANGE_PURPLE_CANVAS_WALL_SIGN.get()));

}
