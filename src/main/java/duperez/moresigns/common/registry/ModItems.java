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
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MoreSigns.MODID);

    public static final DeferredItem<Item> CANVAS = ITEMS.registerItem("canvas", props -> new FuelItem(props, 400));

    public static final DeferredItem<PartItem> SIGN_FRAME = ITEMS.register("sign_frame",
            () -> new PartItem(new Item.Properties(), PartItem.Kind.FRAME));
    public static final DeferredItem<PartItem> SIGN_BOARD = ITEMS.register("sign_board",
            () -> new PartItem(new Item.Properties(), PartItem.Kind.BOARD));
    public static final DeferredItem<CustomSignItem> CUSTOM_SIGN = ITEMS.register("custom_sign",
            () -> new CustomSignItem(new Item.Properties().stacksTo(16), ModBlocks.CUSTOM_SIGN.get(), ModBlocks.CUSTOM_WALL_SIGN.get()));
    public static final DeferredItem<BlockItem> SIGN_WORKBENCH = ITEMS.registerSimpleBlockItem("sign_workbench", ModBlocks.SIGN_WORKBENCH);

    private static final Map<CanvasVariant, DeferredItem<SignItem>> SIGNS = new LinkedHashMap<>();

    static {
        for (CanvasVariant variant : CanvasVariants.ALL) {
            SIGNS.put(variant, ITEMS.register(variant.standingName(), () -> new SignItem(new Item.Properties(),
                    ModBlocks.standingSign(variant).get(), ModBlocks.wallSign(variant).get())));
        }
    }

    public static DeferredItem<SignItem> sign(CanvasVariant variant) {
        return SIGNS.get(variant);
    }

    public static Collection<DeferredItem<SignItem>> signs() {
        return SIGNS.values();
    }

    private ModItems() {
    }
}
