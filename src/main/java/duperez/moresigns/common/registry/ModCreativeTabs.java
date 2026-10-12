package duperez.moresigns.common.registry;

import duperez.moresigns.common.sign.Part;
import duperez.moresigns.common.sign.SignNbt;
import duperez.moresigns.common.sign.SignParts;
import duperez.moresigns.common.variant.CanvasVariants;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public class ModCreativeTabs {
    /**
     * The mod's creative tab. The workbench, the canvas and the canvas signs join it through their item properties; frames, boards and
     * custom signs are added here, one for each material, as a starting point: the rest are made at the workbench.
     */
    public static final CreativeModeTab TAB = new CreativeModeTab("moresignstab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.sign(CanvasVariants.ORANGE_PURPLE).get());
        }

        @Override
        public void fillItemList(NonNullList<ItemStack> items) {
            super.fillItemList(items);
            List<ResourceLocation> materials = materials();
            for (ResourceLocation material : materials) {
                items.add(partStack(ModItems.SIGN_FRAME.get(), material));
            }
            for (ResourceLocation material : materials) {
                items.add(partStack(ModItems.SIGN_BOARD.get(), material));
            }
            for (ResourceLocation material : materials) {
                ItemStack sign = new ItemStack(ModItems.CUSTOM_SIGN.get());
                SignNbt.setSignParts(sign, new SignParts(Part.of(material), Part.of(material)));
                items.add(sign);
            }
        }
    };

    /** Every plank, and a few of the other materials. Anything the workbench accepts can be made there. */
    public static List<ResourceLocation> materials() {
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

    public static ItemStack partStack(Item item, ResourceLocation material) {
        ItemStack stack = new ItemStack(item);
        SignNbt.setPart(stack, Part.of(material));
        return stack;
    }

    private ModCreativeTabs() {
    }
}
