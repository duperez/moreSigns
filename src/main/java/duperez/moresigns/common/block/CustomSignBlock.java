package duperez.moresigns.common.block;

import duperez.moresigns.common.block.entity.CustomSignBlockEntity;
import duperez.moresigns.common.registry.ModBlockEntityTypes;
import duperez.moresigns.common.sign.SignNbt;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
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
        super(Properties.copy(Blocks.OAK_SIGN), WoodType.OAK);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CustomSignBlockEntity(pos, state);
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

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return createTickerHelper(type, ModBlockEntityTypes.CUSTOM_SIGN.get(), CustomSignBlockEntity::tick);
    }
}
