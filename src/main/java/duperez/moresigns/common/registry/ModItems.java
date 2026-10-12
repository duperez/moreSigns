package duperez.moresigns.common.registry;

import duperez.moresigns.MoreSigns;
import duperez.moresigns.common.item.CustomSignItem;
import duperez.moresigns.common.item.FuelItem;
import duperez.moresigns.common.item.PartItem;
import duperez.moresigns.common.variant.CanvasVariant;
import duperez.moresigns.common.variant.CanvasVariants;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SignItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MoreSigns.MODID);

    public static final RegistryObject<Item> CANVAS = ITEMS.register("canvas", () -> new FuelItem(new Item.Properties().tab(ModCreativeTabs.TAB), 400));

    // Frames, boards and custom signs have no tab of their own: the tab adds them itself, one for each material, with their parts set.
    public static final RegistryObject<PartItem> SIGN_FRAME = ITEMS.register("sign_frame",
            () -> new PartItem(new Item.Properties(), PartItem.Kind.FRAME));
    public static final RegistryObject<PartItem> SIGN_BOARD = ITEMS.register("sign_board",
            () -> new PartItem(new Item.Properties(), PartItem.Kind.BOARD));
    public static final RegistryObject<CustomSignItem> CUSTOM_SIGN = ITEMS.register("custom_sign",
            () -> new CustomSignItem(new Item.Properties().stacksTo(16), ModBlocks.CUSTOM_SIGN.get(), ModBlocks.CUSTOM_WALL_SIGN.get()));
    public static final RegistryObject<BlockItem> SIGN_WORKBENCH = ITEMS.register("sign_workbench",
            () -> new BlockItem(ModBlocks.SIGN_WORKBENCH.get(), new Item.Properties().tab(ModCreativeTabs.TAB)));

    private static final Map<CanvasVariant, RegistryObject<SignItem>> SIGNS = new LinkedHashMap<>();

    static {
        for (CanvasVariant variant : CanvasVariants.ALL) {
            SIGNS.put(variant, ITEMS.register(variant.standingName(), () -> new SignItem(new Item.Properties().tab(ModCreativeTabs.TAB),
                    ModBlocks.standingSign(variant).get(), ModBlocks.wallSign(variant).get())));
        }
    }

    public static RegistryObject<SignItem> sign(CanvasVariant variant) {
        return SIGNS.get(variant);
    }

    public static Collection<RegistryObject<SignItem>> signs() {
        return SIGNS.values();
    }

    private ModItems() {
    }
}
