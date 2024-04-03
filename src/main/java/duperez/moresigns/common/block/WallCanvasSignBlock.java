package duperez.moresigns.common.block;

import duperez.moresigns.common.block.state.CanvasSign;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;
import duperez.moresigns.common.registry.ModBlockEntityTypes;

import javax.annotation.Nullable;

public class WallCanvasSignBlock extends WallSignBlock implements CanvasSign
{
	private final String backgroundColor;

	public WallCanvasSignBlock(Properties properties, @Nullable String backgroundColor) {
		super(properties, WoodType.SPRUCE);
		this.backgroundColor = backgroundColor;
	}

	public WallCanvasSignBlock(Properties properties, @Nullable DyeColor backgroundColor) {
		super(properties, WoodType.SPRUCE);
		this.backgroundColor = backgroundColor.getName();
	}

	public WallCanvasSignBlock(Properties properties) {
		super(properties, WoodType.SPRUCE);
		this.backgroundColor = null;
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
