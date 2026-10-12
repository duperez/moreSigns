package duperez.moresigns.common.sign;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;

import java.util.Optional;

/**
 * One piece of a custom sign, a frame or a board: the block it is made of, and the dye it was painted with.
 * The block gives the piece its texture; the dye tints it.
 */
public record Part(ResourceLocation block, Optional<DyeColor> tint) {
    public static final Part DEFAULT = new Part(new ResourceLocation("minecraft", "oak_planks"), Optional.empty());

    private static final String BLOCK = "block";
    private static final String TINT = "tint";

    /** How strongly a dye colors a texture. Full strength turns light woods muddy, so the dye is mixed with white. */
    private static final float TINT_STRENGTH = 0.8F;

    public static Part of(ResourceLocation block) {
        return new Part(block, Optional.empty());
    }

    public Part withTint(Optional<DyeColor> tint) {
        return new Part(block, tint);
    }

    /** The 0xRRGGBB factor a texture is multiplied by to draw this part; white when it is not painted. */
    public int tintRgb() {
        if (tint.isEmpty()) {
            return 0xFFFFFF;
        }
        float[] dye = tint.get().getTextureDiffuseColors();
        return mix(dye[0]) << 16 | mix(dye[1]) << 8 | mix(dye[2]);
    }

    private static int mix(float channel) {
        return Math.round(255 + (channel * 255 - 255) * TINT_STRENGTH);
    }

    public CompoundTag toTag() {
        CompoundTag tag = new CompoundTag();
        tag.putString(BLOCK, block.toString());
        tint.ifPresent(dye -> tag.putString(TINT, dye.getName()));
        return tag;
    }

    /** Reads a part back; anything missing or unreadable falls back to the default, so a damaged tag never breaks a sign. */
    public static Part fromTag(Tag tag) {
        if (!(tag instanceof CompoundTag compound)) {
            return DEFAULT;
        }
        ResourceLocation block = compound.contains(BLOCK, Tag.TAG_STRING) ? ResourceLocation.tryParse(compound.getString(BLOCK)) : null;
        Optional<DyeColor> tint = compound.contains(TINT, Tag.TAG_STRING)
                ? Optional.ofNullable(DyeColor.byName(compound.getString(TINT), null)) : Optional.empty();
        return new Part(block != null ? block : DEFAULT.block(), tint);
    }
}
