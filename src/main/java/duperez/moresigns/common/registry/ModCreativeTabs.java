package duperez.moresigns.common.registry;

import duperez.moresigns.MoreSigns;
import duperez.moresigns.common.sign.Part;
import duperez.moresigns.common.sign.SignNbt;
import duperez.moresigns.common.sign.SignParts;
import duperez.moresigns.common.variant.CanvasVariants;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MoreSigns.MODID);

    public static final RegistryObject<CreativeModeTab> MORE_SIGNS = CREATIVE_TABS.register(MoreSigns.MODID,
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
                            SignNbt.setSignParts(sign, new SignParts(Part.of(material), Part.of(material)));
                            output.accept(sign);
                        }
                    })
                    .build());

    /** The materials shown in the tab: every plank, and a few of the others. Anything the workbench accepts can be made there. */
    private static List<ResourceLocation> materials() {
        List<ResourceLocation> materials = new ArrayList<>();
        ForgeRegistries.BLOCKS.tags().getTag(BlockTags.PLANKS).forEach(block -> materials.add(ForgeRegistries.BLOCKS.getKey(block)));
        if (materials.isEmpty()) {
            materials.add(Part.DEFAULT.block());
        }
        for (String name : List.of("iron_block", "gold_block", "copper_block", "diamond_block", "emerald_block", "netherite_block",
                "glass", "stone", "stone_bricks", "bricks", "quartz_block", "obsidian")) {
            materials.add(new ResourceLocation("minecraft", name));
        }
        return materials;
    }

    private static ItemStack partStack(Item item, ResourceLocation material) {
        ItemStack stack = new ItemStack(item);
        SignNbt.setPart(stack, Part.of(material));
        return stack;
    }

    private ModCreativeTabs() {
    }
}
