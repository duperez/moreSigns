package duperez.moresigns;

import com.mojang.logging.LogUtils;
import duperez.moresigns.common.registry.ModBlockEntityTypes;
import duperez.moresigns.common.registry.ModBlocks;
import duperez.moresigns.common.registry.ModCreativeTabs;
import duperez.moresigns.common.registry.ModItems;
import duperez.moresigns.common.registry.ModMenuTypes;
import duperez.moresigns.common.registry.ModWoodTypes;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(MoreSigns.MODID)
public class MoreSigns {
    public static final String MODID = "moresigns";
    public static final Logger LOGGER = LogUtils.getLogger();

    public MoreSigns() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Wood types must exist before the client builds its sign models and sprite sheet.
        ModWoodTypes.init();

        ModBlocks.BLOCKS.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);
        ModBlockEntityTypes.BLOCK_ENTITY_TYPES.register(modEventBus);
        ModMenuTypes.MENU_TYPES.register(modEventBus);
        ModCreativeTabs.CREATIVE_TABS.register(modEventBus);
    }
}
