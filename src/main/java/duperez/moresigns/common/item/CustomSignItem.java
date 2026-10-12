package duperez.moresigns.common.item;

import duperez.moresigns.common.registry.ModDataComponents;
import duperez.moresigns.common.sign.PartNames;
import duperez.moresigns.common.sign.SignParts;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SignItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;

import java.util.List;

/** Places a custom sign. The frame and board it is made of travel with the item as a data component. */
public class CustomSignItem extends SignItem {
    public CustomSignItem(Properties properties, Block standingSign, Block wallSign) {
        super(properties.component(ModDataComponents.SIGN_PARTS.get(), SignParts.DEFAULT), standingSign, wallSign);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        SignParts parts = stack.getOrDefault(ModDataComponents.SIGN_PARTS.get(), SignParts.DEFAULT);
        tooltip.add(Component.translatable("moresigns.tooltip.frame", PartNames.describe(parts.frame())).withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("moresigns.tooltip.board", PartNames.describe(parts.board())).withStyle(ChatFormatting.GRAY));
    }
}
