package duperez.moresigns.common.sign;

import net.minecraft.network.chat.Component;
import net.minecraftforge.registries.ForgeRegistries;

/** Player-facing names for the pieces of a custom sign. */
public final class PartNames {
    private PartNames() {
    }

    /** "Spruce Planks", or "Red Spruce Planks" when painted. */
    public static Component describe(Part part) {
        Component block = ForgeRegistries.BLOCKS.getValue(part.block()).getName();
        return part.tint()
                .<Component>map(dye -> Component.translatable("moresigns.part.painted", Component.translatable("color.minecraft." + dye.getName()), block))
                .orElse(block);
    }
}
