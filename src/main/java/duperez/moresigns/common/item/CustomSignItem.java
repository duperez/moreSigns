package duperez.moresigns.common.item;

import duperez.moresigns.client.ClientItemProperties;
import duperez.moresigns.common.sign.PartNames;
import duperez.moresigns.common.sign.SignNbt;
import duperez.moresigns.common.sign.Part;
import duperez.moresigns.common.sign.SignParts;
import duperez.moresigns.common.registry.ModCreativeTabs;
import net.minecraft.ChatFormatting;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SignItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.IItemRenderProperties;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Consumer;

/** Places a custom sign. The frame and board it is made of travel with the item, in its NBT. */
public class CustomSignItem extends SignItem {
    public CustomSignItem(Properties properties, Block standingSign, Block wallSign) {
        super(properties, standingSign, wallSign);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        SignParts parts = SignNbt.getSignParts(stack);
        tooltip.add(new TranslatableComponent("moresigns.tooltip.frame", PartNames.describe(parts.frame())).withStyle(ChatFormatting.GRAY));
        tooltip.add(new TranslatableComponent("moresigns.tooltip.board", PartNames.describe(parts.board())).withStyle(ChatFormatting.GRAY));
    }

    /** The mod's own tab lists these itself, one for each material; this makes them findable in the search tab too. */
    @Override
    public void fillItemCategory(CreativeModeTab tab, NonNullList<ItemStack> items) {
        if (tab == CreativeModeTab.TAB_SEARCH) {
            for (ResourceLocation material : ModCreativeTabs.materials()) {
                ItemStack sign = new ItemStack(this);
                SignNbt.setSignParts(sign, new SignParts(Part.of(material), Part.of(material)));
                items.add(sign);
            }
        }
    }

    @Override
    public void initializeClient(Consumer<IItemRenderProperties> consumer) {
        consumer.accept(ClientItemProperties.customSignRenderer());
    }
}
