package duperez.moresigns.data;

import duperez.moresigns.common.registry.ModBlocks;
import duperez.moresigns.common.registry.ModDataComponents;
import duperez.moresigns.common.registry.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.CopyComponentsFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ModBlockLoot extends BlockLootSubProvider {
    public ModBlockLoot(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        ModBlocks.standingSigns().forEach(sign -> dropSelf(sign.get()));
        dropSelf(ModBlocks.SIGN_WORKBENCH.get());

        // A custom sign drops itself with the frame and board it is made of.
        add(ModBlocks.CUSTOM_SIGN.get(), block -> LootTable.lootTable().withPool(applyExplosionCondition(block, LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .add(LootItem.lootTableItem(ModItems.CUSTOM_SIGN.get())
                        .apply(CopyComponentsFunction.copyComponents(CopyComponentsFunction.Source.BLOCK_ENTITY)
                                .include(ModDataComponents.SIGN_PARTS.get()))))));
    }

    /** Wall signs have no table of their own; they drop like their standing sign. */
    @Override
    protected Iterable<Block> getKnownBlocks() {
        List<Block> blocks = new ArrayList<>();
        ModBlocks.standingSigns().forEach(sign -> blocks.add(sign.get()));
        blocks.add(ModBlocks.CUSTOM_SIGN.get());
        blocks.add(ModBlocks.SIGN_WORKBENCH.get());
        return blocks;
    }
}
