package duperez.moresigns.data;

import duperez.moresigns.MoreSigns;
import duperez.moresigns.common.registry.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * Flat item models for the canvas and the canvas signs. The frame, board and custom sign items, and the workbench, have models
 * written by hand because they are drawn by code or by a block model.
 */
public class ModItemModels extends ItemModelProvider {
    public ModItemModels(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, MoreSigns.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        flat(ModItems.CANVAS.get());
        ModItems.signs().forEach(sign -> flat(sign.get()));
    }

    private void flat(Item item) {
        String name = ForgeRegistries.ITEMS.getKey(item).getPath();
        withExistingParent(name, "item/generated").texture("layer0", modLoc("item/" + name));
    }
}
