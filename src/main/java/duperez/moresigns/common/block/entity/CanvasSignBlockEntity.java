package duperez.moresigns.common.block.entity;

import duperez.moresigns.common.registry.ModBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Kept as its own block entity type (rather than reusing the vanilla sign one) so signs saved under the
 * {@code moresigns:canvas_sign} id keep loading.
 */
public class CanvasSignBlockEntity extends SignBlockEntity {
    public CanvasSignBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityTypes.CANVAS_SIGN.get(), pos, state);
    }
}
