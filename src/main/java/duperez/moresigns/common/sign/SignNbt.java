package duperez.moresigns.common.sign;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;

/**
 * Where the parts of a sign are kept on an item. This version of Minecraft has no data components, so they are NBT.
 * A frame or a board keeps its {@link Part} under {@code Part}; a sign keeps its {@link SignParts} under {@code SignParts}.
 * <p>
 * The sign's parts are deliberately not under {@code BlockEntityTag}: the game ignores that tag on a sign placed by a player
 * who is not an operator, because a sign can carry commands in its text. The blocks apply {@code SignParts} themselves when placed.
 */
public final class SignNbt {
    public static final String PART = "Part";
    public static final String SIGN_PARTS = "SignParts";
    /** Where the block entity keeps its parts when saved. */
    public static final String BLOCK_ENTITY_PARTS = "parts";

    private SignNbt() {
    }

    public static Part getPart(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        return tag != null && tag.contains(PART, Tag.TAG_COMPOUND) ? Part.fromTag(tag.getCompound(PART)) : Part.DEFAULT;
    }

    public static void setPart(ItemStack stack, Part part) {
        stack.getOrCreateTag().put(PART, part.toTag());
    }

    /** The parts of a sign item, or the default sign's when it carries none. */
    public static SignParts getSignParts(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        return tag != null && tag.contains(SIGN_PARTS, Tag.TAG_COMPOUND) ? SignParts.fromTag(tag.getCompound(SIGN_PARTS)) : SignParts.DEFAULT;
    }

    public static void setSignParts(ItemStack stack, SignParts parts) {
        stack.getOrCreateTag().put(SIGN_PARTS, parts.toTag());
    }
}
