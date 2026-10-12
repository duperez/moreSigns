package duperez.moresigns.data;

import duperez.moresigns.MoreSigns;
import duperez.moresigns.common.registry.ModBlocks;
import duperez.moresigns.common.registry.ModItems;
import duperez.moresigns.common.variant.CanvasVariants;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class ModLang extends LanguageProvider {
    public ModLang(PackOutput output) {
        super(output, MoreSigns.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add("itemGroup.moresignstab", "More Signs");
        addItem(ModItems.CANVAS, "Canvas");
        // Wall signs share the translation key of their standing sign.
        CanvasVariants.ALL.forEach(variant -> add(ModBlocks.standingSign(variant).get(), variant.displayName()));
    }
}
