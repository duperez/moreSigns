package duperez.moresigns.common.item;

import duperez.moresigns.client.ClientItemExtensions;
import duperez.moresigns.common.sign.PartNames;
import duperez.moresigns.common.sign.SignNbt;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import java.util.function.Consumer;

/** A sign frame or a sign board, made at the sign workbench out of a block and tinted with a dye. */
public class PartItem extends Item {
    public enum Kind {
        FRAME, BOARD
    }

    private final Kind kind;

    public PartItem(Properties properties, Kind kind) {
        super(properties);
        this.kind = kind;
    }

    public Kind kind() {
        return kind;
    }

    @Override
    public Component getName(ItemStack stack) {
        return Component.translatable(getDescriptionId() + ".named", PartNames.describe(SignNbt.getPart(stack)));
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(ClientItemExtensions.customSignRenderer());
    }
}
