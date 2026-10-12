package duperez.moresigns.common.variant;

import java.util.Arrays;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * One kind of canvas sign. Everything the mod generates for a sign (standing and wall blocks, item,
 * wood type, sprite, blockstate, loot, recipe, tags, lang) is derived from this record, so adding a
 * sign means adding a texture and one entry in {@link CanvasVariants}.
 * <p>
 * Deliberately free of Minecraft/Forge types so it can be unit tested and reused if the mod ever
 * targets another loader.
 *
 * @param key            Prefix for registry names, or {@code null} for the plain (undyed) sign.
 * @param displayName    English name of the standing sign item, e.g. "White Canvas Sign".
 * @param dye            Dye color name that crafts this sign from any other canvas sign, or {@code null}
 *                       when the sign has no dye recipe (plain and hand-painted signs).
 * @param darkBackground Whether new signs start with white text, because black text would be unreadable.
 */
public record CanvasVariant(String key, String displayName, String dye, boolean darkBackground) {

    private static final String SIGN_SUFFIX = "canvas_sign";
    private static final String WALL_SIGN_SUFFIX = "canvas_wall_sign";

    /** The undyed sign. Also the one every other sign is crafted from. */
    public static CanvasVariant plain() {
        return new CanvasVariant(null, "Canvas Sign", null, false);
    }

    /** A sign dyed with a vanilla dye color, named after it. */
    public static CanvasVariant dyed(String dye, boolean darkBackground) {
        String name = Arrays.stream(dye.split("_"))
                .map(word -> word.substring(0, 1).toUpperCase(Locale.ROOT) + word.substring(1))
                .collect(Collectors.joining(" "));
        return new CanvasVariant(dye, name + " Canvas Sign", dye, darkBackground);
    }

    /** A sign with a custom painted texture and no dye recipe. */
    public static CanvasVariant painted(String key, String displayName, boolean darkBackground) {
        return new CanvasVariant(key, displayName, null, darkBackground);
    }

    /** Registry path of the standing sign block and of the item, e.g. {@code white_canvas_sign}. */
    public String standingName() {
        return prefixed(SIGN_SUFFIX);
    }

    /** Registry path of the wall sign block, e.g. {@code white_canvas_wall_sign}. */
    public String wallName() {
        return prefixed(WALL_SIGN_SUFFIX);
    }

    /**
     * Name of the wood type, and so of the sprite under {@code textures/entity/signs/}, e.g.
     * {@code canvas_white}. The plain sign is just {@code canvas}.
     */
    public String woodTypeName() {
        return key == null ? "canvas" : "canvas_" + key;
    }

    private String prefixed(String suffix) {
        return key == null ? suffix : key + "_" + suffix;
    }
}
