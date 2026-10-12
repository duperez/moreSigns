package duperez.moresigns.common.block;

import duperez.moresigns.common.block.entity.CustomSignBlockEntity;
import duperez.moresigns.common.registry.ModBlockEntityTypes;
import duperez.moresigns.common.sign.SignNbt;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;

import javax.annotation.Nullable;
import java.util.function.Supplier;

/** The wall-mounted version of {@link CustomSignBlock}; it drops, and is picked as, the same item. */
public class CustomWallSignBlock extends WallSignBlock {
    public CustomWallSignBlock(Supplier<? extends Block> standingSign) {
        super(Properties.copy(Blocks.OAK_WALL_SIGN).lootFrom(standingSign), WoodType.OAK);
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

    /** The frame and board come from the item, not from a block entity tag, which the game ignores on signs placed by players. */
    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(level, pos, state, placer, stack);
        if (!level.isClientSide && level.getBlockEntity(pos) instanceof CustomSignBlockEntity sign) {
            sign.setParts(SignNbt.getSignParts(stack));
        }
    }

    @Override
    public ItemStack getCloneItemStack(BlockGetter level, BlockPos pos, BlockState state) {
        return CustomSignBlockEntity.pickStack(level, pos);
    }
}
