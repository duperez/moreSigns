package duperez.moresigns.common.registry;

import duperez.moresigns.MoreSigns;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeTabs
{
	public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MoreSigns.MODID);

	public static final RegistryObject<CreativeModeTab> TAB_MORE_SIGNS = CREATIVE_TABS.register(MoreSigns.MODID,
			() -> CreativeModeTab.builder()
					.title(Component.translatable("itemGroup.moresignstab"))
					.icon(() -> new ItemStack(ModBlocks.ORANGE_PURPLE_SIGN.get()))
					.displayItems((parameters, output) -> ModItems.CREATIVE_TAB_ITEMS.forEach((item) -> output.accept(item.get())))
					.build());
}
