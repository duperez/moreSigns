package duperez.moresigns.common.sign;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;

/** What a finished custom sign is made of. */
public record SignParts(Part frame, Part board) {
    public static final SignParts DEFAULT = new SignParts(Part.DEFAULT, Part.DEFAULT);

    public static final Codec<SignParts> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Part.CODEC.fieldOf("frame").forGetter(SignParts::frame),
            Part.CODEC.fieldOf("board").forGetter(SignParts::board)
    ).apply(instance, SignParts::new));

    public Tag toTag() {
        return CODEC.encodeStart(NbtOps.INSTANCE, this).result().orElseGet(CompoundTag::new);
    }

    public static SignParts fromTag(Tag tag) {
        return CODEC.parse(NbtOps.INSTANCE, tag).result().orElse(DEFAULT);
    }
}
