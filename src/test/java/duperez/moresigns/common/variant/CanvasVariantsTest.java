package duperez.moresigns.common.variant;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CanvasVariantsTest {

    /** Registry names shipped in the 1.20.1 releases. Changing any of them breaks signs in existing worlds. */
    private static final List<String> LEGACY_STANDING_SIGNS = List.of(
            "canvas_sign", "white_canvas_sign", "orange_canvas_sign", "magenta_canvas_sign",
            "light_blue_canvas_sign", "yellow_canvas_sign", "lime_canvas_sign", "pink_canvas_sign",
            "gray_canvas_sign", "light_gray_canvas_sign", "cyan_canvas_sign", "purple_canvas_sign",
            "blue_canvas_sign", "brown_canvas_sign", "green_canvas_sign", "red_canvas_sign",
            "black_canvas_sign", "orange_purple_canvas_sign", "green_gradient_canvas_sign");

    private static final List<String> LEGACY_WALL_SIGNS = List.of(
            "canvas_wall_sign", "white_canvas_wall_sign", "orange_canvas_wall_sign", "magenta_canvas_wall_sign",
            "light_blue_canvas_wall_sign", "yellow_canvas_wall_sign", "lime_canvas_wall_sign", "pink_canvas_wall_sign",
            "gray_canvas_wall_sign", "light_gray_canvas_wall_sign", "cyan_canvas_wall_sign", "purple_canvas_wall_sign",
            "blue_canvas_wall_sign", "brown_canvas_wall_sign", "green_canvas_wall_sign", "red_canvas_wall_sign",
            "black_canvas_wall_sign", "orange_purple_canvas_wall_sign", "green_gradient_canvas_wall_sign");

    @Test
    void standingSignNamesMatchLegacyRegistryNames() {
        assertEquals(LEGACY_STANDING_SIGNS, names(CanvasVariant::standingName));
    }

    @Test
    void wallSignNamesMatchLegacyRegistryNames() {
        assertEquals(LEGACY_WALL_SIGNS, names(CanvasVariant::wallName));
    }

    @Test
    void woodTypeNamesAreUnique() {
        List<String> woodTypes = names(CanvasVariant::woodTypeName);
        assertEquals(woodTypes.size(), Set.copyOf(woodTypes).size());
    }

    @Test
    void plainVariantUsesTheUnsuffixedSprite() {
        assertEquals("canvas", CanvasVariants.PLAIN.woodTypeName());
        assertEquals("canvas_light_blue", CanvasVariant.dyed("light_blue", false).woodTypeName());
    }

    @Test
    void dyedVariantsAreNamedAfterTheirDye() {
        CanvasVariant lightBlue = CanvasVariant.dyed("light_blue", false);
        assertEquals("Light Blue Canvas Sign", lightBlue.displayName());
        assertEquals("light_blue_canvas_sign", lightBlue.standingName());
        assertEquals("light_blue", lightBlue.dye());
    }

    @Test
    void onlyDyedVariantsHaveADyeRecipe() {
        assertTrue(CanvasVariants.DYED.stream().allMatch(variant -> variant.dye() != null));
        assertEquals(List.of(), CanvasVariants.ALL.stream()
                .filter(variant -> variant.dye() != null && !CanvasVariants.DYED.contains(variant))
                .toList());
    }

    private static List<String> names(java.util.function.Function<CanvasVariant, String> name) {
        return CanvasVariants.ALL.stream().map(name).collect(Collectors.toList());
    }
}
