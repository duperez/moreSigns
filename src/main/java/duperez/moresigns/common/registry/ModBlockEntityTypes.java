package duperez.moresigns.common.registry;

import duperez.moresigns.MoreSigns;
import duperez.moresigns.common.block.entity.CanvasSignBlockEntity;
import duperez.moresigns.common.block.entity.CustomSignBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlockEntityTypes {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, MoreSigns.MODID);

    public static final Supplier<BlockEntityType<CanvasSignBlockEntity>> CANVAS_SIGN = BLOCK_ENTITY_TYPES.register("canvas_sign",
            () -> BlockEntityType.Builder.of(CanvasSignBlockEntity::new, ModBlocks.allSigns().toArray(Block[]::new)).build(null));

    public static final Supplier<BlockEntityType<CustomSignBlockEntity>> CUSTOM_SIGN = BLOCK_ENTITY_TYPES.register("custom_sign",
            () -> BlockEntityType.Builder.of(CustomSignBlockEntity::new, ModBlocks.CUSTOM_SIGN.get(), ModBlocks.CUSTOM_WALL_SIGN.get()).build(null));

    private ModBlockEntityTypes() {
    }
}
