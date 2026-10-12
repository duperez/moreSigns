package duperez.moresigns.common.sign;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;

/** What a finished custom sign is made of. */
public record SignParts(Part frame, Part board) {
    public static final SignParts DEFAULT = new SignParts(Part.DEFAULT, Part.DEFAULT);

    public static final Codec<SignParts> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Part.CODEC.fieldOf("frame").forGetter(SignParts::frame),
            Part.CODEC.fieldOf("board").forGetter(SignParts::board)
    ).apply(instance, SignParts::new));

    public static final StreamCodec<ByteBuf, SignParts> STREAM_CODEC = StreamCodec.composite(
            Part.STREAM_CODEC, SignParts::frame,
            Part.STREAM_CODEC, SignParts::board,
            SignParts::new);
}
