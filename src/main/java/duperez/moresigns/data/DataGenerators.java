package duperez.moresigns.data;

import duperez.moresigns.MoreSigns;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = MoreSigns.MODID)
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        BlockTagsProvider blockTags = new ModBlockTags(output, lookupProvider, existingFileHelper);
        generator.addProvider(event.includeServer(), blockTags);
        generator.addProvider(event.includeServer(), new ModItemTags(output, lookupProvider, blockTags.contentsGetter(), existingFileHelper));
        generator.addProvider(event.includeServer(), new ModRecipes(output, lookupProvider));
        generator.addProvider(event.includeServer(), new LootTableProvider(output, Set.of(),
                List.of(new LootTableProvider.SubProviderEntry(ModBlockLoot::new, LootContextParamSets.BLOCK)), lookupProvider));

        generator.addProvider(event.includeClient(), new ModBlockStates(output, existingFileHelper));
        generator.addProvider(event.includeClient(), new ModItemModels(output, existingFileHelper));
        // Minecraft does not fall back from one regional locale to another, so each region gets the language's file.
        for (String locale : List.of("en_us")) {
            generator.addProvider(event.includeClient(), new ModLang(output, locale, Translations.Language.ENGLISH));
        }
        for (String locale : List.of("pt_br", "pt_pt")) {
            generator.addProvider(event.includeClient(), new ModLang(output, locale, Translations.Language.PORTUGUESE));
        }
        for (String locale : List.of("es_es", "es_mx", "es_ar", "es_cl", "es_uy", "es_ve")) {
            generator.addProvider(event.includeClient(), new ModLang(output, locale, Translations.Language.SPANISH));
        }
    }
}
