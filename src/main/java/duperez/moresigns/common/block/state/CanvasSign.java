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
        return backgroundDye != null && ImmutableList.of(
                "gray", "purple", "brown", "green",
                "red", "black", "white", "indigo",
                "loosaz", "lime", "jinx", "spades",
                "rabbit", "gold", "mary", "cyber",
                "meg", "delta", "jay", "kalypso",
                "m1", "michael", "mona", "moon",
                "uko", "celyne", "pepper","epsilon",
                "havarti", "minerva", "rune", "wicky",
                "abadom", "apoliom", "monsieur", "inkaris",
                "loky", "light_gray", "aaliyah"

        ).contains(backgroundDye);
    }
}
