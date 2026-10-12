package duperez.moresigns.common.registry;

import duperez.moresigns.MoreSigns;
import duperez.moresigns.common.variant.CanvasVariant;
import duperez.moresigns.common.variant.CanvasVariants;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.WoodType;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * One {@link WoodType} per canvas variant. The wood type's name decides which sprite vanilla's sign renderer uses
 * ({@code textures/entity/signs/<name>.png}), which is what lets the mod rely on the vanilla renderer for the canvas signs.
 */
public final class ModWoodTypes {
    private static final Map<CanvasVariant, WoodType> WOOD_TYPES = new LinkedHashMap<>();

    static {
        for (CanvasVariant variant : CanvasVariants.ALL) {
            String name = new ResourceLocation(MoreSigns.MODID, variant.woodTypeName()).toString();
            WOOD_TYPES.put(variant, WoodType.register(WoodType.create(name)));
        }
    }

    public static WoodType get(CanvasVariant variant) {
        return WOOD_TYPES.get(variant);
    }

    /** Forces the static initializer, and so the registration, to run. */
    public static void init() {
    }

    private ModWoodTypes() {
    }
}
