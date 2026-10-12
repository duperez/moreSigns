package duperez.moresigns.data;

import duperez.moresigns.MoreSigns;
import duperez.moresigns.common.registry.ModItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModItemModels extends ItemModelProvider {
    public ModItemModels(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, MoreSigns.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(ModItems.CANVAS.get());
        ModItems.signs().forEach(sign -> basicItem(sign.get()));
    }
}
