package duperez.moresigns.common.registry;

import duperez.moresigns.MoreSigns;
import duperez.moresigns.common.variant.CanvasVariants;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MoreSigns.MODID);

    public static final Supplier<CreativeModeTab> MORE_SIGNS = CREATIVE_TABS.register(MoreSigns.MODID,
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.moresignstab"))
                    .icon(() -> new ItemStack(ModItems.sign(CanvasVariants.ORANGE_PURPLE).get()))
                    .displayItems((parameters, output) -> {
                        output.accept(ModItems.CANVAS.get());
                        ModItems.signs().forEach(sign -> output.accept(sign.get()));
                    })
                    .build());

    private ModCreativeTabs() {
    }
}
