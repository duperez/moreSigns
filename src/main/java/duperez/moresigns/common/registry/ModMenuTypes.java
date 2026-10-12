package duperez.moresigns.common.registry;

import duperez.moresigns.MoreSigns;
import duperez.moresigns.common.inventory.SignWorkbenchMenu;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(ForgeRegistries.MENU_TYPES, MoreSigns.MODID);

    public static final RegistryObject<MenuType<SignWorkbenchMenu>> SIGN_WORKBENCH = MENU_TYPES.register("sign_workbench",
            () -> new MenuType<>(SignWorkbenchMenu::new, FeatureFlags.DEFAULT_FLAGS));

    private ModMenuTypes() {
    }
}
