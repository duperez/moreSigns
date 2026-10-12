package duperez.moresigns.common.block;

import duperez.moresigns.common.block.entity.CanvasSignBlockEntity;
import duperez.moresigns.common.registry.ModBlockEntityTypes;
import duperez.moresigns.common.variant.CanvasVariant;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class WallCanvasSignBlock extends WallSignBlock implements CanvasSign {
    private final CanvasVariant variant;

    /** @param standingSign The standing sign of the same variant, whose loot table this block shares. */
    public WallCanvasSignBlock(CanvasVariant variant, WoodType woodType, Supplier<? extends Block> standingSign) {
        super(Properties.copy(Blocks.OAK_WALL_SIGN).lootFrom(standingSign), woodType);
        this.variant = variant;
    }

    @Override
    public CanvasVariant variant() {
        return variant;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CanvasSignBlockEntity(pos, state);
    }

    /** Vanilla only ticks {@code BlockEntityType.SIGN}, so without this the edit lock of a canvas sign is never released. */
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return createTickerHelper(type, ModBlockEntityTypes.CANVAS_SIGN.get(), SignBlockEntity::tick);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(level, pos, state, placer, stack);
        applyDefaultTextColor(level, pos);
    }
}
