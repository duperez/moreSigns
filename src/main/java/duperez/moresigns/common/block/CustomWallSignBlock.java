package duperez.moresigns.common.block;

import duperez.moresigns.common.block.entity.CustomSignBlockEntity;
import duperez.moresigns.common.registry.ModBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;

import javax.annotation.Nullable;

/** The wall-mounted version of {@link CustomSignBlock}; it drops, and is picked as, the same item. */
public class CustomWallSignBlock extends WallSignBlock {
    public CustomWallSignBlock(Block standingSign) {
        super(WoodType.OAK, Properties.ofFullCopy(Blocks.OAK_WALL_SIGN).dropsLike(standingSign));
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CustomSignBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return createTickerHelper(type, ModBlockEntityTypes.CUSTOM_SIGN.get(), CustomSignBlockEntity::tick);
    }

    @Override
    public ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state) {
        return CustomSignBlockEntity.pickStack(level, pos);
    }
}
