package duperez.moresigns.data;

import duperez.moresigns.MoreSigns;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = MoreSigns.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        if (event.includeServer()) {
            BlockTagsProvider blockTags = new ModBlockTags(generator, existingFileHelper);
            generator.addProvider(blockTags);
            generator.addProvider(new ModItemTags(generator, blockTags, existingFileHelper));
            generator.addProvider(new ModRecipes(generator));
            generator.addProvider(new ModLootTables(generator));
        }

        if (event.includeClient()) {
            generator.addProvider(new ModBlockStates(generator, existingFileHelper));
            generator.addProvider(new ModItemModels(generator, existingFileHelper));

            // Minecraft does not fall back from one regional locale to another, so each region gets the language's file.
            generator.addProvider(new ModLang(generator, "en_us", Translations.Language.ENGLISH));
            for (String locale : List.of("pt_br", "pt_pt")) {
                generator.addProvider(new ModLang(generator, locale, Translations.Language.PORTUGUESE));
            }
            for (String locale : List.of("es_es", "es_mx", "es_ar", "es_cl", "es_uy", "es_ve")) {
                generator.addProvider(new ModLang(generator, locale, Translations.Language.SPANISH));
            }
        }
    }
}
