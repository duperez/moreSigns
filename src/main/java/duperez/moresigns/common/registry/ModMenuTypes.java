package duperez.moresigns.common.registry;

import duperez.moresigns.MoreSigns;
import duperez.moresigns.common.inventory.SignWorkbenchMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(Registries.MENU, MoreSigns.MODID);

    public static final Supplier<MenuType<SignWorkbenchMenu>> SIGN_WORKBENCH = MENU_TYPES.register("sign_workbench",
            () -> new MenuType<>(SignWorkbenchMenu::new, FeatureFlags.DEFAULT_FLAGS));

    private ModMenuTypes() {
    }
}
