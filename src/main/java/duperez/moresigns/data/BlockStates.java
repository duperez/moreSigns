package duperez.moresigns.data;

import com.google.common.collect.Sets;
import duperez.moresigns.common.registry.ModBlocks;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import duperez.moresigns.MoreSigns;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

public class BlockStates extends BlockStateProvider
{
	private static final int DEFAULT_ANGLE_OFFSET = 180;

	public BlockStates(DataGenerator gen, ExistingFileHelper exFileHelper) {
		super(gen, MoreSigns.MODID, exFileHelper);
	}

	private String blockName(Block block) {
		return block.getRegistryName().getPath();
	}

	public ResourceLocation resourceBlock(String path) {
		return new ResourceLocation(MoreSigns.MODID, "block/" + path);
	}

	public ModelFile existingModel(Block block) {
		return new ModelFile.ExistingModelFile(resourceBlock(blockName(block)), models().existingFileHelper);
	}

	public ModelFile existingModel(String path) {
		return new ModelFile.ExistingModelFile(resourceBlock(path), models().existingFileHelper);
	}

	@Override
	protected void registerStatesAndModels() {
		Set<Block> canvasSigns = Sets.newHashSet(
				ModBlocks.CANVAS_SIGN.get(),
				ModBlocks.WHITE_CANVAS_SIGN.get(),
				ModBlocks.ORANGE_CANVAS_SIGN.get(),
				ModBlocks.MAGENTA_CANVAS_SIGN.get(),
				ModBlocks.LIGHT_BLUE_CANVAS_SIGN.get(),
				ModBlocks.YELLOW_CANVAS_SIGN.get(),
				ModBlocks.LIME_CANVAS_SIGN.get(),
				ModBlocks.PINK_CANVAS_SIGN.get(),
				ModBlocks.GRAY_CANVAS_SIGN.get(),
				ModBlocks.LIGHT_GRAY_CANVAS_SIGN.get(),
				ModBlocks.CYAN_CANVAS_SIGN.get(),
				ModBlocks.PURPLE_CANVAS_SIGN.get(),
				ModBlocks.BLUE_CANVAS_SIGN.get(),
				ModBlocks.BROWN_CANVAS_SIGN.get(),
				ModBlocks.GREEN_CANVAS_SIGN.get(),
				ModBlocks.RED_CANVAS_SIGN.get(),
				ModBlocks.BLACK_CANVAS_SIGN.get(),
				ModBlocks.GREEN_GRADIENT_SIGN.get(),
				ModBlocks.CANVAS_WALL_SIGN.get(),
				ModBlocks.WHITE_CANVAS_WALL_SIGN.get(),
				ModBlocks.ORANGE_CANVAS_WALL_SIGN.get(),
				ModBlocks.MAGENTA_CANVAS_WALL_SIGN.get(),
				ModBlocks.LIGHT_BLUE_CANVAS_WALL_SIGN.get(),
				ModBlocks.YELLOW_CANVAS_WALL_SIGN.get(),
				ModBlocks.LIME_CANVAS_WALL_SIGN.get(),
				ModBlocks.PINK_CANVAS_WALL_SIGN.get(),
				ModBlocks.GRAY_CANVAS_WALL_SIGN.get(),
				ModBlocks.LIGHT_GRAY_CANVAS_WALL_SIGN.get(),
				ModBlocks.CYAN_CANVAS_WALL_SIGN.get(),
				ModBlocks.PURPLE_CANVAS_WALL_SIGN.get(),
				ModBlocks.BLUE_CANVAS_WALL_SIGN.get(),
				ModBlocks.BROWN_CANVAS_WALL_SIGN.get(),
				ModBlocks.GREEN_CANVAS_WALL_SIGN.get(),
				ModBlocks.RED_CANVAS_WALL_SIGN.get(),
				ModBlocks.ORANGE_PURPLE_CANVAS_WALL_SIGN.get(),
				ModBlocks.BLACK_CANVAS_WALL_SIGN.get(),
				ModBlocks.GREEN_GRADIENT_WALL_SIGN.get());

		for (Block sign : canvasSigns) {
			this.simpleBlock(sign, existingModel(ModBlocks.CANVAS_SIGN.get()));
		}
	}

	public ConfiguredModel[] cubeRandomRotation(Block block, String suffix) {
		String formattedName = blockName(block) + (suffix.isEmpty() ? "" : "_" + suffix);
		return ConfiguredModel.allYRotations(models().cubeAll(formattedName, resourceBlock(formattedName)), 0, false);
	}

	public void customDirectionalBlock(Block block, Function<BlockState, ModelFile> modelFunc, Property<?>... ignored) {
		getVariantBuilder(block)
				.forAllStatesExcept(state -> {
					Direction dir = state.getValue(BlockStateProperties.FACING);
					return ConfiguredModel.builder()
							.modelFile(modelFunc.apply(state))
							.rotationX(dir == Direction.DOWN ? 180 : dir.getAxis().isHorizontal() ? 90 : 0)
							.rotationY(dir.getAxis().isVertical() ? 0 : ((int) dir.toYRot() + DEFAULT_ANGLE_OFFSET) % 360)
							.build();
				}, ignored);
	}

	public void customHorizontalBlock(Block block, Function<BlockState, ModelFile> modelFunc, Property<?>... ignored) {
		getVariantBuilder(block)
				.forAllStatesExcept(state -> ConfiguredModel.builder()
						.modelFile(modelFunc.apply(state))
						.rotationY(((int) state.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot() + DEFAULT_ANGLE_OFFSET) % 360)
						.build(), ignored);
	}

	public void stageBlock(Block block, IntegerProperty ageProperty, Property<?>... ignored) {
		getVariantBuilder(block)
				.forAllStatesExcept(state -> {
					int ageSuffix = state.getValue(ageProperty);
					String stageName = blockName(block) + "_stage" + ageSuffix;
					return ConfiguredModel.builder()
							.modelFile(models().cross(stageName, resourceBlock(stageName))).build();
				}, ignored);
	}

	// I am not proud of this method... But hey, it's runData. Only I shall have to deal with it.
	public void customStageBlock(Block block, @Nullable ResourceLocation parent, String textureKey, IntegerProperty ageProperty, List<Integer> suffixes, Property<?>... ignored) {
		getVariantBuilder(block)
				.forAllStatesExcept(state -> {
					int ageSuffix = state.getValue(ageProperty);
					String stageName = blockName(block) + "_stage";
					stageName += suffixes.isEmpty() ? ageSuffix : suffixes.get(Math.min(suffixes.size(), ageSuffix));
					if (parent == null) {
						return ConfiguredModel.builder()
								.modelFile(models().cross(stageName, resourceBlock(stageName))).build();
					}
					return ConfiguredModel.builder()
							.modelFile(models().singleTexture(stageName, parent, textureKey, resourceBlock(stageName))).build();
				}, ignored);
	}

	public void wildCropBlock(Block block) {
		this.wildCropBlock(block, false);
	}

	public void wildCropBlock(Block block, boolean isBushCrop) {
		if (isBushCrop) {
			this.simpleBlock(block, models().singleTexture(blockName(block), resourceBlock("bush_crop"), "crop", resourceBlock(blockName(block))));
		} else {
			this.simpleBlock(block, models().cross(blockName(block), resourceBlock(blockName(block))));
		}
	}

	public void crateBlock(Block block, String cropName) {
		this.simpleBlock(block,
				models().cubeBottomTop(blockName(block), resourceBlock(cropName + "_crate_side"), resourceBlock("crate_bottom"), resourceBlock(cropName + "_crate_top")));
	}

	public void doublePlantBlock(Block block) {
		getVariantBuilder(block)
				.partialState().with(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER)
				.modelForState().modelFile(models().cross(blockName(block) + "_bottom", resourceBlock(blockName(block) + "_bottom"))).addModel()
				.partialState().with(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER)
				.modelForState().modelFile(models().cross(blockName(block) + "_top", resourceBlock(blockName(block) + "_top"))).addModel();
	}
}
