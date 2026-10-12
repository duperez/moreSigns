package duperez.moresigns.client;

import duperez.moresigns.MoreSigns;
import duperez.moresigns.client.gui.CustomSignEditScreen;
import duperez.moresigns.common.block.entity.CustomSignBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.SignEditScreen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MoreSigns.MODID, value = Dist.CLIENT)
public class ClientEvents {

    /**
     * The game opens a vanilla {@link SignEditScreen} for every sign. For a custom sign, swap it for one that draws
     * the sign as it looks in the world. Exact class check so screens of other mods are left alone.
     */
    @SubscribeEvent
    public static void onScreenOpening(ScreenEvent.Opening event) {
        if (event.getNewScreen() != null && event.getNewScreen().getClass() == SignEditScreen.class) {
            SignEditScreen screen = (SignEditScreen) event.getNewScreen();
            if (screen.sign instanceof CustomSignBlockEntity sign) {
                event.setNewScreen(new CustomSignEditScreen(sign, screen.isFrontText, Minecraft.getInstance().isTextFilteringEnabled()));
            }
        }
    }
}
