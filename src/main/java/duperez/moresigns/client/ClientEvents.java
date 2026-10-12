package duperez.moresigns.client;

import duperez.moresigns.MoreSigns;
import duperez.moresigns.client.gui.CustomSignEditScreen;
import duperez.moresigns.common.block.entity.CustomSignBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.SignEditScreen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ScreenOpenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MoreSigns.MODID, value = Dist.CLIENT)
public class ClientEvents {

    /**
     * The game opens a vanilla {@link SignEditScreen} for every sign. For a custom sign, swap it for one that draws
     * the sign as it looks in the world. Exact class check so screens of other mods are left alone.
     */
    @SubscribeEvent
    public static void onScreenOpen(ScreenOpenEvent event) {
        if (event.getScreen() != null && event.getScreen().getClass() == SignEditScreen.class) {
            SignEditScreen screen = (SignEditScreen) event.getScreen();
            if (screen.sign instanceof CustomSignBlockEntity sign) {
                event.setScreen(new CustomSignEditScreen(sign, Minecraft.getInstance().isTextFilteringEnabled()));
            }
        }
    }
}
