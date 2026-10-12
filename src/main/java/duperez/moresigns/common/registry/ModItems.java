package duperez.moresigns.common.registry;

import duperez.moresigns.MoreSigns;
import duperez.moresigns.common.item.FuelItem;
import duperez.moresigns.common.variant.CanvasVariant;
import duperez.moresigns.common.variant.CanvasVariants;
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
