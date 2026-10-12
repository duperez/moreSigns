package duperez.moresigns.common.sign;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
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

    public static final Codec<Part> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("block").forGetter(Part::block),
            DyeColor.CODEC.optionalFieldOf("tint").forGetter(Part::tint)
    ).apply(instance, Part::new));

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

    public Tag toTag() {
        return CODEC.encodeStart(NbtOps.INSTANCE, this).result().orElseGet(CompoundTag::new);
    }

    public static Part fromTag(Tag tag) {
        return CODEC.parse(NbtOps.INSTANCE, tag).result().orElse(DEFAULT);
    }
}
