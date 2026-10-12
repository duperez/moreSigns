package duperez.moresigns.common.block;

import duperez.moresigns.common.block.entity.CanvasSignBlockEntity;
import duperez.moresigns.common.variant.CanvasVariant;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;

import javax.annotation.Nullable;

public class StandingCanvasSignBlock extends StandingSignBlock implements CanvasSign {
    private final CanvasVariant variant;

    public StandingCanvasSignBlock(CanvasVariant variant, WoodType woodType) {
        super(Properties.copy(Blocks.OAK_SIGN), woodType);
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

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(level, pos, state, placer, stack);
        applyDefaultTextColor(level, pos);
    }
}
