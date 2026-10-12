package duperez.moresigns.common.sign;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;

/** What a finished custom sign is made of. */
public record SignParts(Part frame, Part board) {
    public static final SignParts DEFAULT = new SignParts(Part.DEFAULT, Part.DEFAULT);

    private static final String FRAME = "frame";
    private static final String BOARD = "board";

    public CompoundTag toTag() {
        CompoundTag tag = new CompoundTag();
        tag.put(FRAME, frame.toTag());
        tag.put(BOARD, board.toTag());
        return tag;
    }

    public static SignParts fromTag(Tag tag) {
        if (!(tag instanceof CompoundTag compound)) {
            return DEFAULT;
        }
        return new SignParts(
                compound.contains(FRAME, Tag.TAG_COMPOUND) ? Part.fromTag(compound.getCompound(FRAME)) : Part.DEFAULT,
                compound.contains(BOARD, Tag.TAG_COMPOUND) ? Part.fromTag(compound.getCompound(BOARD)) : Part.DEFAULT);
    }
}
