package duperez.moresigns.data;

import duperez.moresigns.MoreSigns;
import duperez.moresigns.common.registry.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
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
        ModBlocks.allSigns().forEach(sign -> tag(BlockTags.MINEABLE_WITH_AXE).add(sign));
    }
}
