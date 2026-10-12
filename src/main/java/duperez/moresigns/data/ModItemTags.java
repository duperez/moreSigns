package duperez.moresigns.data;

import duperez.moresigns.MoreSigns;
import duperez.moresigns.common.registry.ModItems;
import duperez.moresigns.common.tag.ModTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemTags extends ItemTagsProvider {
    public ModItemTags(DataGenerator generator, BlockTagsProvider blockTags, ExistingFileHelper existingFileHelper) {
        super(generator, blockTags, MoreSigns.MODID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        ModItems.signs().forEach(sign -> {
            tag(ModTags.CANVAS_SIGNS).add(sign.get());
            tag(ItemTags.SIGNS).add(sign.get());
        });
        // The custom sign is deliberately not in the signs tag: the workbench recipe takes any sign from it, and a custom sign
        // used there would be lost together with its frame and board.
    }
}
