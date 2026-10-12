package duperez.moresigns.data;

import duperez.moresigns.MoreSigns;
import duperez.moresigns.common.registry.ModBlocks;
import duperez.moresigns.common.tag.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class ModBlockTags extends BlockTagsProvider {
    public ModBlockTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, MoreSigns.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        ModBlocks.standingSigns().forEach(sign -> tag(BlockTags.STANDING_SIGNS).add(sign.get()));
        ModBlocks.wallSigns().forEach(sign -> tag(BlockTags.WALL_SIGNS).add(sign.get()));
        tag(BlockTags.STANDING_SIGNS).add(ModBlocks.CUSTOM_SIGN.get());
        tag(BlockTags.WALL_SIGNS).add(ModBlocks.CUSTOM_WALL_SIGN.get());
        ModBlocks.allSigns().forEach(sign -> tag(BlockTags.MINEABLE_WITH_AXE).add(sign));
        tag(BlockTags.MINEABLE_WITH_AXE).add(ModBlocks.CUSTOM_SIGN.get(), ModBlocks.CUSTOM_WALL_SIGN.get(), ModBlocks.SIGN_WORKBENCH.get());

        // What the sign workbench takes as material: full blocks with one clear texture. Using the shared tags means blocks
        // from other mods (their metals, their glass) are accepted too. Add more here, or from a data pack.
        tag(ModTags.SIGN_MATERIALS)
                .addTag(BlockTags.PLANKS)
                .addTag(BlockTags.WOOL)
                .addTag(BlockTags.TERRACOTTA)
                .addTag(BlockTags.STONE_BRICKS)
                .addTag(Tags.Blocks.STORAGE_BLOCKS)
                .addTag(Tags.Blocks.GLASS_BLOCKS)
                .addTag(Tags.Blocks.CONCRETES)
                .addTag(Tags.Blocks.STONES)
                .addTag(Tags.Blocks.COBBLESTONES)
                .addTag(Tags.Blocks.OBSIDIANS)
                .add(Blocks.BRICKS, Blocks.NETHER_BRICKS, Blocks.QUARTZ_BLOCK, Blocks.SMOOTH_STONE, Blocks.BONE_BLOCK,
                        Blocks.PRISMARINE, Blocks.CUT_COPPER);
    }
}
