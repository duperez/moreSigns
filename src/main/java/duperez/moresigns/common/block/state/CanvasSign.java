package duperez.moresigns.common.block.state;

import net.minecraft.world.item.DyeColor;
import duperez.moresigns.common.Configuration;

import javax.annotation.Nullable;

public interface CanvasSign
{
	/**
	 * Returns this sign's background dye color. If null, the sign is uncolored (beige).
	 */
	@Nullable
	String getBackgroundColor();

	/**
	 * Checks a config to determine if the background color is considered "dark".
	 */
	default boolean isDarkBackground() {
		String backgroundDye = this.getBackgroundColor();
		return backgroundDye != null && Configuration.CANVAS_SIGN_DARK_BACKGROUND_LIST.get().contains(backgroundDye);
	}
}
