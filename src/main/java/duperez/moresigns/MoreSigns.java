package duperez.moresigns;

import duperez.moresigns.common.registry.ModBlockEntityTypes;
import duperez.moresigns.common.registry.ModBlocks;
import duperez.moresigns.common.registry.ModCreativeTabs;
import duperez.moresigns.common.registry.ModItems;
import duperez.moresigns.common.registry.ModWoodTypes;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;
import com.mojang.logging.LogUtils;

@Mod(MoreSigns.MODID)
public class MoreSigns {
    public static final String MODID = "moresigns";
    public static final Logger LOGGER = LogUtils.getLogger();

    public MoreSigns(IEventBus modEventBus) {
        // Wood types must exist before the client builds its sign models and sprite sheet.
        ModWoodTypes.init();

        ModBlocks.BLOCKS.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);
        ModBlockEntityTypes.BLOCK_ENTITY_TYPES.register(modEventBus);
        ModCreativeTabs.CREATIVE_TABS.register(modEventBus);
    }
}
