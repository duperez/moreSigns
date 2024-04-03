package duperez.moresigns.common.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import duperez.moresigns.common.registry.ModBlocks;

@Mixin(BlockRenderDispatcher.class)
public abstract class HideBlockBreakProgressMixin
{
	@Inject(method = "renderBreakingTexture", at = @At("HEAD"), cancellable = true)
	private void hideBlockDamage(BlockState blockStateIn, BlockPos posIn, BlockAndTintGetter lightReaderIn, PoseStack matrixStackIn, VertexConsumer vertexBuilderIn, CallbackInfo ci) {
		if (blockStateIn.getBlock() == ModBlocks.CANVAS_RUG.get()) {
			ci.cancel();
		}
	}
}
