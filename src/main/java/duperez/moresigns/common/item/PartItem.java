package duperez.moresigns.common.item;

import duperez.moresigns.client.ClientItemProperties;
import duperez.moresigns.common.sign.PartNames;
import duperez.moresigns.common.sign.SignNbt;
import duperez.moresigns.common.registry.ModCreativeTabs;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.IItemRenderProperties;

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
        return new TranslatableComponent(getDescriptionId() + ".named", PartNames.describe(SignNbt.getPart(stack)));
    }

    /** The mod's own tab lists these itself, one for each material; this makes them findable in the search tab too. */
    @Override
    public void fillItemCategory(CreativeModeTab tab, NonNullList<ItemStack> items) {
        if (tab == CreativeModeTab.TAB_SEARCH) {
            for (ResourceLocation material : ModCreativeTabs.materials()) {
                items.add(ModCreativeTabs.partStack(this, material));
            }
        }
    }

    @Override
    public void initializeClient(Consumer<IItemRenderProperties> consumer) {
        consumer.accept(ClientItemProperties.customSignRenderer());
    }
}
