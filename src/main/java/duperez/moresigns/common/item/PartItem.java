package duperez.moresigns.common.item;

import duperez.moresigns.common.registry.ModDataComponents;
import duperez.moresigns.common.sign.Part;
import duperez.moresigns.common.sign.PartNames;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

/** A sign frame or a sign board, made at the sign workbench out of a block and tinted with a dye. */
public class PartItem extends Item {
    public enum Kind {
        FRAME, BOARD
    }

    private final Kind kind;

    public PartItem(Properties properties, Kind kind) {
        super(properties.component(ModDataComponents.PART.get(), Part.DEFAULT));
        this.kind = kind;
    }

    public Kind kind() {
        return kind;
    }

    @Override
    public Component getName(ItemStack stack) {
        Part part = stack.getOrDefault(ModDataComponents.PART.get(), Part.DEFAULT);
        return Component.translatable(getDescriptionId() + ".named", PartNames.describe(part));
    }
}
