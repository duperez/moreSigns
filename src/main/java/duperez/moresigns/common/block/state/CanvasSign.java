package duperez.moresigns.common.block.state;

import com.google.common.collect.ImmutableList;

import javax.annotation.Nullable;

public interface CanvasSign {
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
        return backgroundDye != null && ImmutableList.of("gray", "purple", "blue", "brown", "green", "red", "black", "white").contains(backgroundDye);
    }
}
