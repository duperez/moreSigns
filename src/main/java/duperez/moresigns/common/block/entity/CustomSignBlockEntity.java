package duperez.moresigns.common.block.entity;

import duperez.moresigns.common.registry.ModBlockEntityTypes;
import duperez.moresigns.common.registry.ModItems;
import duperez.moresigns.common.sign.SignNbt;
import duperez.moresigns.common.sign.SignParts;
import duperez.moresigns.common.sign.TextContrast;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * A sign assembled from two parts: the board (the plate you write on) and the frame (its border and post).
 * Each part is drawn with the texture of the block it is made of, tinted by the dye it was painted with.
 */
public class CustomSignBlockEntity extends SignBlockEntity {
    /** Stands in for the board color of blocks the map does not color, which are glass-like and so light. */
    private static final int TRANSPARENT_BOARD_RGB = 0xD8E6EC;
    private static final String TEXT_COLOR_SET_TAG = "text_color_set";

    private SignParts parts = SignParts.DEFAULT;
    /** Whether the default text color has been chosen from the board. Done once, so the player's dye wins afterwards. */
    private boolean textColorSet;

    public CustomSignBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityTypes.CUSTOM_SIGN.get(), pos, state);
    }

    public SignParts parts() {
        return parts;
    }

    /** Changes what the sign is made of, and so which text color suits it, and tells the players who can see it. */
    public void setParts(SignParts parts) {
        this.parts = parts;
        this.textColorSet = false;
        setChanged();
        if (level != null && !level.isClientSide) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
        }
    }

    /** The item a player gets from picking this sign with the middle button: a sign made of the same frame and board. */
    public static ItemStack pickStack(BlockGetter level, BlockPos pos) {
        ItemStack stack = new ItemStack(ModItems.CUSTOM_SIGN.get());
        if (level.getBlockEntity(pos) instanceof CustomSignBlockEntity sign) {
            SignNbt.setSignParts(stack, sign.parts);
        }
        return stack;
    }

    /** Server tick: the vanilla sign upkeep, then picking a readable text color the first time. */
    public static void tick(Level level, BlockPos pos, BlockState state, CustomSignBlockEntity sign) {
        SignBlockEntity.tick(level, pos, state, sign);
        if (!level.isClientSide && !sign.textColorSet) {
            sign.chooseDefaultTextColor();
        }
    }

    private void chooseDefaultTextColor() {
        int mapColor = ForgeRegistries.BLOCKS.getValue(parts.board().block()).defaultMapColor().col;
        if (mapColor == 0) {
            mapColor = TRANSPARENT_BOARD_RGB;
        }
        int boardRgb = TextContrast.tinted(mapColor, parts.board().tintRgb());
        DyeColor color = TextContrast.prefersWhiteText(boardRgb) ? DyeColor.WHITE : DyeColor.BLACK;
        updateText(text -> text.setColor(color), true);
        updateText(text -> text.setColor(color), false);
        textColorSet = true;
        setChanged();
    }

    @Override
    public AABB getRenderBoundingBox() {
        return new AABB(worldPosition.getX(), worldPosition.getY(), worldPosition.getZ(),
                worldPosition.getX() + 1.0, worldPosition.getY() + 1.125, worldPosition.getZ() + 1.0);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put(SignNbt.BLOCK_ENTITY_PARTS, parts.toTag());
        tag.putBoolean(TEXT_COLOR_SET_TAG, textColorSet);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        parts = tag.contains(SignNbt.BLOCK_ENTITY_PARTS, Tag.TAG_COMPOUND) ? SignParts.fromTag(tag.getCompound(SignNbt.BLOCK_ENTITY_PARTS)) : SignParts.DEFAULT;
        textColorSet = tag.getBoolean(TEXT_COLOR_SET_TAG);
    }
}
