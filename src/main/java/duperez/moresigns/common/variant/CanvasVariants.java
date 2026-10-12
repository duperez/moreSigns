package duperez.moresigns.common.variant;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Every canvas sign the mod registers, in creative tab order.
 * <p>
 * To add a sign: put {@code textures/entity/signs/canvas_<key>.png} and
 * {@code textures/item/<key>_canvas_sign.png} in the resources, add a line here, then run the
 * {@code runData} Gradle task.
 * <p>
 * Registry names of existing entries must never change, since that breaks signs in existing worlds.
 */
public final class CanvasVariants {

    public static final CanvasVariant PLAIN = CanvasVariant.plain();

    /** In vanilla dye order. */
    public static final List<CanvasVariant> DYED = List.of(
            CanvasVariant.dyed("white", false),
            CanvasVariant.dyed("orange", false),
            CanvasVariant.dyed("magenta", false),
            CanvasVariant.dyed("light_blue", false),
            CanvasVariant.dyed("yellow", false),
            CanvasVariant.dyed("lime", false),
            CanvasVariant.dyed("pink", false),
            CanvasVariant.dyed("gray", true),
            CanvasVariant.dyed("light_gray", false),
            CanvasVariant.dyed("cyan", false),
            CanvasVariant.dyed("purple", true),
            CanvasVariant.dyed("blue", true),
            CanvasVariant.dyed("brown", true),
            CanvasVariant.dyed("green", true),
            CanvasVariant.dyed("red", true),
            CanvasVariant.dyed("black", true));

    public static final CanvasVariant ORANGE_PURPLE =
            CanvasVariant.painted("orange_purple", "Purple-Orange Canvas Sign", false);
    public static final CanvasVariant GREEN_GRADIENT =
            CanvasVariant.painted("green_gradient", "Green-shade Canvas Sign", false);

    public static final List<CanvasVariant> ALL = Stream.of(
                    Stream.of(PLAIN),
                    DYED.stream(),
                    Stream.of(ORANGE_PURPLE, GREEN_GRADIENT))
            .flatMap(Function.identity())
            .toList();

    private CanvasVariants() {
    }
}
