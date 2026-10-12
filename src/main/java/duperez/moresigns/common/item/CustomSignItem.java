package duperez.moresigns.common.item;

import duperez.moresigns.client.ClientItemExtensions;
import duperez.moresigns.common.sign.PartNames;
import duperez.moresigns.common.sign.SignNbt;
import duperez.moresigns.common.sign.SignParts;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SignItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

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
        tooltip.add(Component.translatable("moresigns.tooltip.frame", PartNames.describe(parts.frame())).withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("moresigns.tooltip.board", PartNames.describe(parts.board())).withStyle(ChatFormatting.GRAY));
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(ClientItemExtensions.customSignRenderer());
    }
}
