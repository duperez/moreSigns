package duperez.moresigns.common.block;

import duperez.moresigns.common.block.state.CanvasSign;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;
import duperez.moresigns.common.registry.ModBlockEntityTypes;

import javax.annotation.Nullable;

public class StandingCanvasSignBlock extends StandingSignBlock implements CanvasSign
{
	private final String backgroundColor;

	public StandingCanvasSignBlock(@Nullable String backgroundColor) {
		super(Properties.copy(Blocks.OAK_SIGN), WoodType.SPRUCE);
		this.backgroundColor = backgroundColor;
	}

	@Nullable
	public String getBackgroundColor() {
		return this.backgroundColor;
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return ModBlockEntityTypes.CANVAS_SIGN.get().create(pos, state);
	}

	@Override
	public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
		BlockEntity tileEntity = level.getBlockEntity(pos);
		Block block = state.getBlock();
		if (tileEntity instanceof SignBlockEntity && block instanceof CanvasSign) {
			if (((CanvasSign) block).isDarkBackground()) {
				((SignBlockEntity) tileEntity).setColor(DyeColor.WHITE);
			}
		}
	}
}
