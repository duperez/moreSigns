package duperez.moresigns.data;

import duperez.moresigns.common.registry.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;

import java.util.Set;

public class ModBlockLoot extends BlockLootSubProvider {
    public ModBlockLoot(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        ModBlocks.standingSigns().forEach(sign -> dropSelf(sign.get()));
    }

    /** Wall signs have no table of their own; they drop like their standing sign. */
    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.standingSigns().stream().<Block>map(sign -> sign.get()).toList();
    }
}
