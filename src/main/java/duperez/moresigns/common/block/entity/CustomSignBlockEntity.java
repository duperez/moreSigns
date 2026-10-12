package duperez.moresigns.common.block.entity;

import duperez.moresigns.common.registry.ModBlockEntityTypes;
import duperez.moresigns.common.registry.ModDataComponents;
import duperez.moresigns.common.registry.ModItems;
import duperez.moresigns.common.sign.SignParts;
import duperez.moresigns.common.sign.TextContrast;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

/**
 * A sign assembled from two parts: the board (the plate you write on) and the frame (its border and post).
 * Each part is drawn with the texture of the block it is made of, tinted by the dye it was painted with.
 */
public class CustomSignBlockEntity extends SignBlockEntity {
    /** Stands in for the board color of blocks the map does not color, which are glass-like and so light. */
    private static final int TRANSPARENT_BOARD_RGB = 0xD8E6EC;
    private static final String PARTS_TAG = "parts";
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
        markPartsChanged();
    }

    private void markPartsChanged() {
        setChanged();
        if (level != null && !level.isClientSide) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
        }
    }

    /** The item a player gets from picking this sign with the middle button: a sign made of the same frame and board. */
    public static ItemStack pickStack(LevelReader level, BlockPos pos) {
        ItemStack stack = new ItemStack(ModItems.CUSTOM_SIGN.get());
        if (level.getBlockEntity(pos) instanceof CustomSignBlockEntity sign) {
            stack.set(ModDataComponents.SIGN_PARTS.get(), sign.parts);
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
        int mapColor = BuiltInRegistries.BLOCK.get(parts.board().block()).defaultMapColor().col;
        if (mapColor == 0) {
            mapColor = TRANSPARENT_BOARD_RGB; // glass and the like have no map color, and the world shows through them
        }
        int boardRgb = TextContrast.tinted(mapColor, parts.board().tintRgb());
        DyeColor color = TextContrast.prefersWhiteText(boardRgb) ? DyeColor.WHITE : DyeColor.BLACK;
        updateText(text -> text.setColor(color), true);
        updateText(text -> text.setColor(color), false);
        textColorSet = true;
        setChanged();
    }

    @Override
    protected void applyImplicitComponents(DataComponentInput input) {
        super.applyImplicitComponents(input);
        // Not setParts: an item that already carries a text color from an earlier sign (pick block) must keep it.
        parts = input.getOrDefault(ModDataComponents.SIGN_PARTS.get(), SignParts.DEFAULT);
        markPartsChanged();
    }

    @Override
    protected void collectImplicitComponents(DataComponentMap.Builder builder) {
        super.collectImplicitComponents(builder);
        builder.set(ModDataComponents.SIGN_PARTS.get(), parts);
    }

    @Override
    public void removeComponentsFromTag(CompoundTag tag) {
        super.removeComponentsFromTag(tag);
        tag.remove(PARTS_TAG);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        SignParts.CODEC.encodeStart(registries.createSerializationContext(NbtOps.INSTANCE), parts)
                .result().ifPresent(encoded -> tag.put(PARTS_TAG, encoded));
        tag.putBoolean(TEXT_COLOR_SET_TAG, textColorSet);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        parts = tag.contains(PARTS_TAG)
                ? SignParts.CODEC.parse(registries.createSerializationContext(NbtOps.INSTANCE), tag.get(PARTS_TAG)).result().orElse(SignParts.DEFAULT)
                : SignParts.DEFAULT;
        textColorSet = tag.getBoolean(TEXT_COLOR_SET_TAG);
    }
}
