package duperez.moresigns.common.registry;

import duperez.moresigns.MoreSigns;
import duperez.moresigns.common.sign.Part;
import duperez.moresigns.common.sign.SignParts;
import duperez.moresigns.common.variant.CanvasVariants;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MoreSigns.MODID);

    public static final Supplier<CreativeModeTab> MORE_SIGNS = CREATIVE_TABS.register(MoreSigns.MODID,
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.moresignstab"))
                    .icon(() -> new ItemStack(ModItems.sign(CanvasVariants.ORANGE_PURPLE).get()))
                    .displayItems((parameters, output) -> {
                        output.accept(ModItems.SIGN_WORKBENCH.get());
                        output.accept(ModItems.CANVAS.get());
                        ModItems.signs().forEach(sign -> output.accept(sign.get()));

                        // One frame, one board and one sign for each material, as a starting point; the rest are made at the workbench.
                        for (ResourceLocation material : materials()) {
                            output.accept(partStack(ModItems.SIGN_FRAME.get(), material));
                        }
                        for (ResourceLocation material : materials()) {
                            output.accept(partStack(ModItems.SIGN_BOARD.get(), material));
                        }
                        for (ResourceLocation material : materials()) {
                            ItemStack sign = new ItemStack(ModItems.CUSTOM_SIGN.get());
                            sign.set(ModDataComponents.SIGN_PARTS.get(), new SignParts(Part.of(material), Part.of(material)));
                            output.accept(sign);
                        }
                    })
                    .build());

    /** The materials shown in the tab: every plank, and a few of the others. Anything the workbench accepts can be made there. */
    private static List<ResourceLocation> materials() {
        List<ResourceLocation> materials = new ArrayList<>(BuiltInRegistries.BLOCK.getTag(BlockTags.PLANKS)
                .map(tag -> tag.stream().flatMap(holder -> holder.unwrapKey().stream()).map(key -> key.location()).toList())
                .orElse(List.of(Part.DEFAULT.block())));
        for (String name : List.of("iron_block", "gold_block", "copper_block", "diamond_block", "emerald_block", "netherite_block",
                "glass", "stone", "stone_bricks", "bricks", "quartz_block", "obsidian")) {
            materials.add(ResourceLocation.withDefaultNamespace(name));
        }
        return materials;
    }

    private static ItemStack partStack(net.minecraft.world.item.Item item, ResourceLocation material) {
        ItemStack stack = new ItemStack(item);
        stack.set(ModDataComponents.PART.get(), Part.of(material));
        return stack;
    }

    private ModCreativeTabs() {
    }
}
