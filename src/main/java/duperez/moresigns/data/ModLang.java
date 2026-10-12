package duperez.moresigns.data;

import duperez.moresigns.MoreSigns;
import duperez.moresigns.common.registry.ModBlocks;
import duperez.moresigns.common.variant.CanvasVariants;
import duperez.moresigns.data.Translations.Language;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

/** One language file. Several Minecraft locales share a language, because the game does not fall back from one to another. */
public class ModLang extends LanguageProvider {
    private final Language language;

    public ModLang(PackOutput output, String locale, Language language) {
        super(output, MoreSigns.MODID, locale);
        this.language = language;
    }

    @Override
    protected void addTranslations() {
        Translations.fixed().forEach(entry -> add(entry.key(), entry.in(language)));

        // Wall signs share the translation key of their standing sign.
        CanvasVariants.ALL.forEach(variant ->
                add(ModBlocks.standingSign(variant).get(), Translations.signName(variant)[language.ordinal()]));
    }
}
