package duperez.moresigns.common.inventory;

import duperez.moresigns.common.inventory.SignWorkbenchLogic.Mode;
import duperez.moresigns.common.item.PartItem;
import duperez.moresigns.common.registry.ModBlocks;
import duperez.moresigns.common.registry.ModItems;
import duperez.moresigns.common.registry.ModMenuTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

/**
 * The sign workbench. It has four modes, picked with the tabs: make a frame or a board out of a block,
 * paint one with a dye (the dye is kept), assemble a frame and a board into a sign, and take a sign apart again.
 * <p>
 * Slot 0 and 1 are the inputs, slot 2 the result. Their meaning, and which items they take, depends on the mode.
 */
public class SignWorkbenchMenu extends AbstractContainerMenu {
    public static final int FIRST_SLOT = 0;
    public static final int SECOND_SLOT = 1;
    public static final int RESULT_SLOT = 2;
    /** The two results of taking a sign apart: its frame, and its board. */
    public static final int FRAME_RESULT_SLOT = 3;
    public static final int BOARD_RESULT_SLOT = 4;
    public static final int INVENTORY_START = 5;
    private static final int INVENTORY_END = INVENTORY_START + 36;

    /** Ids of the buttons the screen can press, besides the ones that switch mode (the mode's ordinal, 0 to 3). */
    public static final int BUTTON_MAKE_FRAME = 10;
    public static final int BUTTON_MAKE_BOARD = 11;
    public static final int BUTTON_TOGGLE_WASH = 20;

    private final ContainerLevelAccess access;
    private final DataSlot mode = DataSlot.standalone();
    private final DataSlot selectedKind = DataSlot.standalone();
    private final DataSlot washing = DataSlot.standalone();
    private final Container inputs = new SimpleContainer(2) {
        @Override
        public void setChanged() {
            super.setChanged();
            slotsChanged(this);
        }
    };
    private final Container result = new SimpleContainer(3);

    public SignWorkbenchMenu(int containerId, Inventory inventory) {
        this(containerId, inventory, ContainerLevelAccess.NULL);
    }

    public SignWorkbenchMenu(int containerId, Inventory inventory, ContainerLevelAccess access) {
        super(ModMenuTypes.SIGN_WORKBENCH.get(), containerId);
        this.access = access;

        addSlot(new InputSlot(0, 20, 17));
        addSlot(new InputSlot(1, 20, 51));
        addSlot(new ResultSlot(0, 139, 34, Output.MAIN));
        addSlot(new ResultSlot(1, 139, 22, Output.FRAME));
        addSlot(new ResultSlot(2, 139, 46, Output.BOARD));

        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 9; column++) {
                addSlot(new Slot(inventory, column + row * 9 + 9, 8 + column * 18, 93 + row * 18));
            }
        }
        for (int column = 0; column < 9; column++) {
            addSlot(new Slot(inventory, column, 8 + column * 18, 151));
        }

        addDataSlot(mode);
        addDataSlot(selectedKind);
        addDataSlot(washing);
    }

    public Mode mode() {
        return Mode.byId(mode.get());
    }

    public PartItem.Kind selectedKind() {
        return selectedKind.get() == 0 ? PartItem.Kind.FRAME : PartItem.Kind.BOARD;
    }

    public boolean washing() {
        return washing.get() != 0;
    }

    public SignWorkbenchLogic.Result currentResult() {
        return SignWorkbenchLogic.compute(mode(), selectedKind(), washing(), inputs.getItem(0), inputs.getItem(1));
    }

    @Override
    public boolean clickMenuButton(Player player, int id) {
        if (id >= 0 && id < Mode.values().length) {
            if (mode.get() != id) {
                returnInputs(player);
                mode.set(id);
                washing.set(0);
                refreshResult();
            }
            return true;
        }
        if (id == BUTTON_MAKE_FRAME || id == BUTTON_MAKE_BOARD) {
            selectedKind.set(id == BUTTON_MAKE_FRAME ? 0 : 1);
            refreshResult();
            return true;
        }
        if (id == BUTTON_TOGGLE_WASH) {
            washing.set(washing() ? 0 : 1);
            refreshResult();
            return true;
        }
        return false;
    }

    @Override
    public void slotsChanged(Container container) {
        if (container == inputs) {
            refreshResult();
        }
    }

    private void refreshResult() {
        SignWorkbenchLogic.Result crafted = currentResult();
        boolean takingApart = mode() == Mode.DISASSEMBLE;
        result.setItem(0, takingApart ? ItemStack.EMPTY : crafted.output().copy());
        result.setItem(1, takingApart ? crafted.output().copy() : ItemStack.EMPTY);
        result.setItem(2, takingApart ? crafted.extra().copy() : ItemStack.EMPTY);
        broadcastChanges();
    }

    /** Puts whatever is in the input slots back in the player's hands, so nothing is left in a slot the new mode would not accept. */
    private void returnInputs(Player player) {
        for (int i = 0; i < inputs.getContainerSize(); i++) {
            ItemStack stack = inputs.removeItemNoUpdate(i);
            if (!stack.isEmpty()) {
                player.getInventory().placeItemBackInInventory(stack);
            }
        }
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        access.execute((level, pos) -> clearContainer(player, inputs));
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(access, player, ModBlocks.SIGN_WORKBENCH.get());
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        Slot slot = slots.get(index);
        if (!slot.hasItem()) {
            return ItemStack.EMPTY;
        }
        ItemStack stack = slot.getItem();
        ItemStack original = stack.copy();

        if (index >= RESULT_SLOT && index < INVENTORY_START) {
            return quickCraft(player, slot);
        }
        if (index < RESULT_SLOT) {
            if (!moveItemStackTo(stack, INVENTORY_START, INVENTORY_END, true)) {
                return ItemStack.EMPTY;
            }
        } else if (!moveToInputs(stack)) {
            // Not for the workbench: shuffle between the inventory and the hotbar.
            if (index < INVENTORY_START + 27) {
                if (!moveItemStackTo(stack, INVENTORY_START + 27, INVENTORY_END, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!moveItemStackTo(stack, INVENTORY_START, INVENTORY_START + 27, false)) {
                return ItemStack.EMPTY;
            }
        }
        if (stack.isEmpty()) {
            slot.set(ItemStack.EMPTY);
        } else {
            slot.setChanged();
        }
        if (stack.getCount() == original.getCount()) {
            return ItemStack.EMPTY;
        }
        slot.onTake(player, stack);
        return original;
    }

    private boolean moveToInputs(ItemStack stack) {
        for (int i = FIRST_SLOT; i <= SECOND_SLOT; i++) {
            if (slots.get(i).mayPlace(stack) && moveItemStackTo(stack, i, i + 1, false)) {
                return true;
            }
        }
        return false;
    }

    /** Shift-click on the result: keeps crafting until the ingredients run out or the inventory is full. */
    private ItemStack quickCraft(Player player, Slot resultSlot) {
        ItemStack first = ItemStack.EMPTY;
        for (int crafts = 0; crafts < 64 && resultSlot.hasItem(); crafts++) {
            ItemStack output = resultSlot.getItem().copy();
            if (!fitsInInventory(output)) {
                break;
            }
            if (first.isEmpty()) {
                first = output.copy();
            }
            moveItemStackTo(output, INVENTORY_START, INVENTORY_END, true);
            resultSlot.onTake(player, resultSlot.getItem());
        }
        return first;
    }

    private boolean fitsInInventory(ItemStack stack) {
        int room = 0;
        for (int i = INVENTORY_START; i < INVENTORY_END; i++) {
            ItemStack existing = slots.get(i).getItem();
            if (existing.isEmpty()) {
                room += Math.min(stack.getMaxStackSize(), slots.get(i).getMaxStackSize());
            } else if (ItemStack.isSameItemSameTags(existing, stack)) {
                room += Math.max(0, Math.min(existing.getMaxStackSize(), slots.get(i).getMaxStackSize()) - existing.getCount());
            }
        }
        return room >= stack.getCount();
    }

    @Override
    public boolean canTakeItemForPickAll(ItemStack stack, Slot slot) {
        return slot.container != result && super.canTakeItemForPickAll(stack, slot);
    }

    /** An input slot: only shown, and only accepting items, when the current mode uses it for them. */
    private class InputSlot extends Slot {
        private final int index;

        InputSlot(int index, int x, int y) {
            super(inputs, index, x, y);
            this.index = index;
        }

        @Override
        public boolean isActive() {
            return (mode() != Mode.MAKE && mode() != Mode.DISASSEMBLE) || index == 0;
        }

        @Override
        public boolean mayPlace(ItemStack stack) {
            return switch (mode()) {
                case DISASSEMBLE -> index == 0 && stack.is(ModItems.CUSTOM_SIGN.get());
                case MAKE -> index == 0 && SignWorkbenchLogic.isMaterial(stack);
                case PAINT -> index == 0 ? SignWorkbenchLogic.kindOf(stack).isPresent() : SignWorkbenchLogic.dyeOf(stack).isPresent();
                case ASSEMBLE -> SignWorkbenchLogic.kindOf(stack).filter(kind -> kind == (index == 0 ? PartItem.Kind.FRAME : PartItem.Kind.BOARD)).isPresent();
            };
        }
    }

    /** Which of the workbench's results a result slot shows. */
    private enum Output {
        MAIN, FRAME, BOARD
    }

    /**
     * A result slot. Taking from it uses up the ingredients, except the dye. When a sign is taken apart there are two results,
     * and taking either one gives the other to the player too, so nothing is lost by taking just one.
     */
    private class ResultSlot extends Slot {
        private final Output output;

        ResultSlot(int containerIndex, int x, int y, Output output) {
            super(result, containerIndex, x, y);
            this.output = output;
        }

        @Override
        public boolean isActive() {
            return (output == Output.MAIN) == (mode() != Mode.DISASSEMBLE);
        }

        @Override
        public boolean mayPlace(ItemStack stack) {
            return false;
        }

        @Override
        public void onTake(Player player, ItemStack stack) {
            ItemStack other = ItemStack.EMPTY;
            if (mode() == Mode.DISASSEMBLE) {
                other = result.getItem(output == Output.FRAME ? 2 : 1).copy();
            }
            SignWorkbenchLogic.Result crafted = currentResult();
            if (!crafted.isEmpty()) {
                inputs.removeItem(0, crafted.useFirst());
                if (crafted.useSecond() > 0) {
                    inputs.removeItem(1, crafted.useSecond());
                }
                access.execute((level, pos) -> level.playSound(null, pos, SoundEvents.UI_STONECUTTER_TAKE_RESULT, SoundSource.BLOCKS, 1.0F, 1.0F));
            }
            if (!other.isEmpty()) {
                player.getInventory().placeItemBackInInventory(other);
            }
            // removeItem has already refreshed the results through slotsChanged.
            super.onTake(player, stack);
        }
    }
}
