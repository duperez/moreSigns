package duperez.moresigns.common.block;

import duperez.moresigns.common.block.entity.CustomSignBlockEntity;
import duperez.moresigns.common.registry.ModBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;

import javax.annotation.Nullable;

/** Standing sign whose looks come from its block entity instead of from the block itself. */
public class CustomSignBlock extends StandingSignBlock {
    public CustomSignBlock() {
        super(WoodType.OAK, Properties.ofFullCopy(Blocks.OAK_SIGN));
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CustomSignBlockEntity(pos, state);
    }

    @Override
    public ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state) {
        return CustomSignBlockEntity.pickStack(level, pos);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return createTickerHelper(type, ModBlockEntityTypes.CUSTOM_SIGN.get(), CustomSignBlockEntity::tick);
    }
}
