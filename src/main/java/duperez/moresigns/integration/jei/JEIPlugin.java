package duperez.moresigns.integration.jei;

//import mezz.jei.api.IModPlugin;
//import mezz.jei.api.JeiPlugin;
//import mezz.jei.api.registration.*;
//import net.minecraft.MethodsReturnNonnullByDefault;
//import net.minecraft.client.Minecraft;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.world.item.crafting.Recipe;
//import net.minecraft.world.item.crafting.RecipeType;
//import duperez.moresigns.MoreSigns;
//
//import javax.annotation.ParametersAreNonnullByDefault;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@JeiPlugin
//@ParametersAreNonnullByDefault
//@MethodsReturnNonnullByDefault
//@SuppressWarnings("unused")
//public class JEIPlugin implements IModPlugin {
//    private static final ResourceLocation ID = new ResourceLocation(MoreSigns.MODID, "jei_plugin");
//    private static final Minecraft MC = Minecraft.getInstance();
//
//    private static List<Recipe<?>> findRecipesByType(RecipeType<?> type) {
//        return MC.level
//                .getRecipeManager()
//                .getRecipes()
//                .stream()
//                .filter(r -> r.getType() == type)
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public void registerCategories(IRecipeCategoryRegistration registry) {
//    }
//
//    @Override
//    public void registerRecipes(IRecipeRegistration registration) {
//
//    }
//
//    @Override
//    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
//    }
//
//    @Override
//    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
//    }
//
//    @Override
//    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
//    }
//
//    @Override
//    public ResourceLocation getPluginUid() {
//        return ID;
//    }
//}
//