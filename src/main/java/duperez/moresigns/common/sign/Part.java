package duperez.moresigns.common.sign;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;

import java.util.Optional;

/**
 * One piece of a custom sign, a frame or a board: the block it is made of, and the dye it was painted with.
 * The block gives the piece its texture; the dye tints it.
 */
public record Part(ResourceLocation block, Optional<DyeColor> tint) {
    public static final Part DEFAULT = new Part(ResourceLocation.withDefaultNamespace("oak_planks"), Optional.empty());

    public static final Codec<Part> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("block").forGetter(Part::block),
            DyeColor.CODEC.optionalFieldOf("tint").forGetter(Part::tint)
    ).apply(instance, Part::new));

    public static final StreamCodec<ByteBuf, Part> STREAM_CODEC = StreamCodec.composite(
            ResourceLocation.STREAM_CODEC, Part::block,
            ByteBufCodecs.optional(DyeColor.STREAM_CODEC), Part::tint,
            Part::new);

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
        int dye = tint.get().getTextureDiffuseColor() & 0xFFFFFF;
        return mix(dye >> 16 & 0xFF) << 16 | mix(dye >> 8 & 0xFF) << 8 | mix(dye & 0xFF);
    }

    private static int mix(int channel) {
        return Math.round(255 + (channel - 255) * TINT_STRENGTH);
    }
}
