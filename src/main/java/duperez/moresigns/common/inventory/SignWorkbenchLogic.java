package duperez.moresigns.common.inventory;

import duperez.moresigns.common.item.PartItem;
import duperez.moresigns.common.registry.ModItems;
import duperez.moresigns.common.sign.Part;
import duperez.moresigns.common.sign.SignNbt;
import duperez.moresigns.common.sign.SignParts;
import duperez.moresigns.common.tag.ModTags;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Optional;

/**
 * What the sign workbench makes out of what is on it. Pure functions of the slots, so the server, which
 * hands out the result, and the client, which previews it, always agree.
 */
public final class SignWorkbenchLogic {
    /** Blocks a frame costs, and how many it makes. */
    public static final int FRAME_COST = 1;
    public static final int FRAME_YIELD = 1;
    /** Blocks a board costs, and how many it makes. */
    public static final int BOARD_COST = 1;
    public static final int BOARD_YIELD = 2;

    public enum Mode {
        MAKE, PAINT, ASSEMBLE, DISASSEMBLE;

        public static Mode byId(int id) {
            Mode[] modes = values();
            return modes[Math.floorMod(id, modes.length)];
        }
    }

    /**
     * What a craft would produce and use, and a line for the player saying why there is nothing, or what happens.
     * {@code extra} is a second thing produced, only when taking a sign apart (the board; {@code output} is the frame).
     * {@code frame} and {@code board} are what to draw in the preview, if anything.
     */
    public record Result(ItemStack output, ItemStack extra, int useFirst, int useSecond, Component message,
                         Optional<Part> frame, Optional<Part> board) {
        public boolean isEmpty() {
            return output.isEmpty();
        }
    }

    private SignWorkbenchLogic() {
    }

    public static boolean isMaterial(ItemStack stack) {
        return stack.getItem() instanceof BlockItem item && item.getBlock().defaultBlockState().is(ModTags.SIGN_MATERIALS);
    }

    public static Optional<DyeColor> dyeOf(ItemStack stack) {
        return stack.getItem() instanceof DyeItem dye ? Optional.of(dye.getDyeColor()) : Optional.empty();
    }

    public static Optional<PartItem.Kind> kindOf(ItemStack stack) {
        return stack.getItem() instanceof PartItem part ? Optional.of(part.kind()) : Optional.empty();
    }

    public static Part partOf(ItemStack stack) {
        return SignNbt.getPart(stack);
    }

    public static ItemStack makePart(PartItem.Kind kind, Part part, int count) {
        ItemStack stack = new ItemStack(kind == PartItem.Kind.FRAME ? ModItems.SIGN_FRAME.get() : ModItems.SIGN_BOARD.get(), count);
        SignNbt.setPart(stack, part);
        return stack;
    }

    public static Result compute(Mode mode, PartItem.Kind selected, boolean washing, ItemStack first, ItemStack second) {
        return switch (mode) {
            case MAKE -> make(selected, first);
            case PAINT -> paint(washing, first, second);
            case ASSEMBLE -> assemble(first, second);
            case DISASSEMBLE -> disassemble(first);
        };
    }

    private static Result make(PartItem.Kind kind, ItemStack material) {
        if (!isMaterial(material)) {
            return nothing("moresigns.workbench.hint.make.insert", Optional.empty(), Optional.empty());
        }
        Part part = Part.of(ForgeRegistries.BLOCKS.getKey(((BlockItem) material.getItem()).getBlock()));
        int cost = kind == PartItem.Kind.FRAME ? FRAME_COST : BOARD_COST;
        int yield = kind == PartItem.Kind.FRAME ? FRAME_YIELD : BOARD_YIELD;
        Optional<Part> shown = Optional.of(part);
        Optional<Part> frame = kind == PartItem.Kind.FRAME ? shown : Optional.empty();
        Optional<Part> board = kind == PartItem.Kind.BOARD ? shown : Optional.empty();
        if (material.getCount() < cost) {
            return nothing("moresigns.workbench.hint.make.more", frame, board);
        }
        Component what = Component.translatable(kind == PartItem.Kind.FRAME ? "moresigns.workbench.frame" : "moresigns.workbench.board");
        return new Result(makePart(kind, part, yield), ItemStack.EMPTY, cost, 0,
                Component.translatable("moresigns.workbench.hint.make.ready", yield, what, cost), frame, board);
    }

    private static Result paint(boolean washing, ItemStack part, ItemStack dye) {
        Optional<PartItem.Kind> kind = kindOf(part);
        if (kind.isEmpty()) {
            return nothing("moresigns.workbench.hint.paint.insert", Optional.empty(), Optional.empty());
        }
        Part current = partOf(part);
        Optional<Part> frame = kind.get() == PartItem.Kind.FRAME ? Optional.of(current) : Optional.empty();
        Optional<Part> board = kind.get() == PartItem.Kind.BOARD ? Optional.of(current) : Optional.empty();
        if (washing) {
            if (current.tint().isEmpty()) {
                return nothing("moresigns.workbench.hint.wash.clean", frame, board);
            }
            Part washed = current.withTint(Optional.empty());
            return new Result(makePart(kind.get(), washed, 1), ItemStack.EMPTY, 1, 0, Component.translatable("moresigns.workbench.hint.wash.ready"),
                    kind.get() == PartItem.Kind.FRAME ? Optional.of(washed) : Optional.empty(),
                    kind.get() == PartItem.Kind.BOARD ? Optional.of(washed) : Optional.empty());
        }
        Optional<DyeColor> color = dyeOf(dye);
        if (color.isEmpty()) {
            return nothing("moresigns.workbench.hint.paint.dye", frame, board);
        }
        if (color.equals(current.tint())) {
            return nothing("moresigns.workbench.hint.paint.same", frame, board);
        }
        Part painted = current.withTint(color);
        return new Result(makePart(kind.get(), painted, 1), ItemStack.EMPTY, 1, 0, Component.translatable("moresigns.workbench.hint.paint.ready"),
                kind.get() == PartItem.Kind.FRAME ? Optional.of(painted) : Optional.empty(),
                kind.get() == PartItem.Kind.BOARD ? Optional.of(painted) : Optional.empty());
    }

    private static Result assemble(ItemStack frameStack, ItemStack boardStack) {
        Optional<Part> frame = kindOf(frameStack).filter(kind -> kind == PartItem.Kind.FRAME).map(kind -> partOf(frameStack));
        Optional<Part> board = kindOf(boardStack).filter(kind -> kind == PartItem.Kind.BOARD).map(kind -> partOf(boardStack));
        if (frame.isEmpty() && board.isEmpty()) {
            return nothing("moresigns.workbench.hint.assemble.insert", frame, board);
        }
        if (frame.isEmpty()) {
            return nothing("moresigns.workbench.hint.assemble.frame", frame, board);
        }
        if (board.isEmpty()) {
            return nothing("moresigns.workbench.hint.assemble.board", frame, board);
        }
        ItemStack sign = new ItemStack(ModItems.CUSTOM_SIGN.get());
        SignNbt.setSignParts(sign, new SignParts(frame.get(), board.get()));
        return new Result(sign, ItemStack.EMPTY, 1, 1, Component.translatable("moresigns.workbench.hint.assemble.ready"), frame, board);
    }

    private static Result disassemble(ItemStack sign) {
        if (!sign.is(ModItems.CUSTOM_SIGN.get())) {
            return nothing("moresigns.workbench.hint.disassemble.insert", Optional.empty(), Optional.empty());
        }
        SignParts parts = SignNbt.getSignParts(sign);
        return new Result(makePart(PartItem.Kind.FRAME, parts.frame(), 1), makePart(PartItem.Kind.BOARD, parts.board(), 1), 1, 0,
                Component.translatable("moresigns.workbench.hint.disassemble.ready"), Optional.of(parts.frame()), Optional.of(parts.board()));
    }

    private static Result nothing(String messageKey, Optional<Part> frame, Optional<Part> board) {
        return new Result(ItemStack.EMPTY, ItemStack.EMPTY, 0, 0, Component.translatable(messageKey), frame, board);
    }
}
