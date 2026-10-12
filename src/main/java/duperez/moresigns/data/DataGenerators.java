package duperez.moresigns.data;

import duperez.moresigns.MoreSigns;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = MoreSigns.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
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
        generator.addProvider(event.includeServer(), new ModRecipes(output));
        generator.addProvider(event.includeServer(), new LootTableProvider(output, Set.of(),
                List.of(new LootTableProvider.SubProviderEntry(ModBlockLoot::new, LootContextParamSets.BLOCK))));

        generator.addProvider(event.includeClient(), new ModBlockStates(output, existingFileHelper));
        generator.addProvider(event.includeClient(), new ModItemModels(output, existingFileHelper));

        // Minecraft does not fall back from one regional locale to another, so each region gets the language's file.
        generator.addProvider(event.includeClient(), new ModLang(output, "en_us", Translations.Language.ENGLISH));
        for (String locale : List.of("pt_br", "pt_pt")) {
            generator.addProvider(event.includeClient(), new ModLang(output, locale, Translations.Language.PORTUGUESE));
        }
        for (String locale : List.of("es_es", "es_mx", "es_ar", "es_cl", "es_uy", "es_ve")) {
            generator.addProvider(event.includeClient(), new ModLang(output, locale, Translations.Language.SPANISH));
        }
    }
}
