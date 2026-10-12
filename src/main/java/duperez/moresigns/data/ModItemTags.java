package duperez.moresigns.data;

import duperez.moresigns.MoreSigns;
import duperez.moresigns.common.registry.ModItems;
import duperez.moresigns.common.tag.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class ModItemTags extends ItemTagsProvider {
    public ModItemTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
                       CompletableFuture<TagsProvider.TagLookup<Block>> blockTags, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, MoreSigns.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        ModItems.signs().forEach(sign -> {
            tag(ModTags.CANVAS_SIGNS).add(sign.get());
            tag(ItemTags.SIGNS).add(sign.get());
        });
        // The custom sign is deliberately not in the signs tag: the workbench recipe takes any sign from it, and a custom sign
        // used there would be lost together with its frame and board.
    }
}
