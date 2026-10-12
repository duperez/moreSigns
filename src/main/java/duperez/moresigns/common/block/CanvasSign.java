package duperez.moresigns.common.block;

import duperez.moresigns.common.variant.CanvasVariant;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.SignBlockEntity;

/** Behaviour shared by the standing and the wall canvas sign blocks. */
public interface CanvasSign {
    CanvasVariant variant();

    /** Signs on a dark background start out with white text, since the default black text would not be readable. */
    default void applyDefaultTextColor(Level level, BlockPos pos) {
        if (variant().darkBackground() && level.getBlockEntity(pos) instanceof SignBlockEntity sign) {
            sign.updateText(text -> text.setColor(DyeColor.WHITE), true);
        }
    }
}
