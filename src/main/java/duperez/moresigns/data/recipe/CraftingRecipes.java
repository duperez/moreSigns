package duperez.moresigns.data.recipe;

import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.recipes.*;
import net.minecraftforge.common.Tags;
import duperez.moresigns.common.registry.ModItems;
import duperez.moresigns.common.tag.ModTags;

import java.util.function.Consumer;

public class CraftingRecipes
{
	public static void register(Consumer<FinishedRecipe> consumer) {
		recipesCanvasSigns(consumer);
	}

	private static void recipesCanvasSigns(Consumer<FinishedRecipe> consumer) {
		//ShapedRecipeBuilder.shaped(ModItems.CANVAS_SIGN.get(), 3)
		//		.pattern("w#w")
		//		.pattern("w#w")
		//		.pattern(" / ")
		//		.define('w', ItemTags.PLANKS)
		//		.define('#', ModItems.CANVAS.get())
		//		.define('/', Items.STICK)
		//		.unlockedBy("has_canvas", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.CANVAS.get()))
		//		.save(consumer);
		ShapelessRecipeBuilder.shapeless(ModItems.WHITE_CANVAS_SIGN.get(), 1)
				.requires(ModTags.CANVAS_SIGNS)
				.requires(Tags.Items.DYES_WHITE)
				.unlockedBy("has_canvas_sign", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.CANVAS_SIGN.get()))
				.group("canvas_sign")
				.save(consumer);
		ShapelessRecipeBuilder.shapeless(ModItems.ORANGE_CANVAS_SIGN.get(), 1)
				.requires(ModTags.CANVAS_SIGNS)
				.requires(Tags.Items.DYES_ORANGE)
				.unlockedBy("has_canvas_sign", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.CANVAS_SIGN.get()))
				.group("canvas_sign")
				.save(consumer);
		ShapelessRecipeBuilder.shapeless(ModItems.MAGENTA_CANVAS_SIGN.get(), 1)
				.requires(ModTags.CANVAS_SIGNS)
				.requires(Tags.Items.DYES_MAGENTA)
				.unlockedBy("has_canvas_sign", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.CANVAS_SIGN.get()))
				.group("canvas_sign")
				.save(consumer);
		ShapelessRecipeBuilder.shapeless(ModItems.LIGHT_BLUE_CANVAS_SIGN.get(), 1)
				.requires(ModTags.CANVAS_SIGNS)
				.requires(Tags.Items.DYES_LIGHT_BLUE)
				.unlockedBy("has_canvas_sign", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.CANVAS_SIGN.get()))
				.group("canvas_sign")
				.save(consumer);
		ShapelessRecipeBuilder.shapeless(ModItems.YELLOW_CANVAS_SIGN.get(), 1)
				.requires(ModTags.CANVAS_SIGNS)
				.requires(Tags.Items.DYES_YELLOW)
				.unlockedBy("has_canvas_sign", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.CANVAS_SIGN.get()))
				.group("canvas_sign")
				.save(consumer);
		ShapelessRecipeBuilder.shapeless(ModItems.LIME_CANVAS_SIGN.get(), 1)
				.requires(ModTags.CANVAS_SIGNS)
				.requires(Tags.Items.DYES_LIME)
				.unlockedBy("has_canvas_sign", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.CANVAS_SIGN.get()))
				.group("canvas_sign")
				.save(consumer);
		ShapelessRecipeBuilder.shapeless(ModItems.PINK_CANVAS_SIGN.get(), 1)
				.requires(ModTags.CANVAS_SIGNS)
				.requires(Tags.Items.DYES_PINK)
				.unlockedBy("has_canvas_sign", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.CANVAS_SIGN.get()))
				.group("canvas_sign")
				.save(consumer);
		ShapelessRecipeBuilder.shapeless(ModItems.GRAY_CANVAS_SIGN.get(), 1)
				.requires(ModTags.CANVAS_SIGNS)
				.requires(Tags.Items.DYES_GRAY)
				.unlockedBy("has_canvas_sign", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.CANVAS_SIGN.get()))
				.group("canvas_sign")
				.save(consumer);
		ShapelessRecipeBuilder.shapeless(ModItems.LIGHT_GRAY_CANVAS_SIGN.get(), 1)
				.requires(ModTags.CANVAS_SIGNS)
				.requires(Tags.Items.DYES_LIGHT_GRAY)
				.unlockedBy("has_canvas_sign", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.CANVAS_SIGN.get()))
				.group("canvas_sign")
				.save(consumer);
		ShapelessRecipeBuilder.shapeless(ModItems.CYAN_CANVAS_SIGN.get(), 1)
				.requires(ModTags.CANVAS_SIGNS)
				.requires(Tags.Items.DYES_CYAN)
				.unlockedBy("has_canvas_sign", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.CANVAS_SIGN.get()))
				.group("canvas_sign")
				.save(consumer);
		ShapelessRecipeBuilder.shapeless(ModItems.PURPLE_CANVAS_SIGN.get(), 1)
				.requires(ModTags.CANVAS_SIGNS)
				.requires(Tags.Items.DYES_PURPLE)
				.unlockedBy("has_canvas_sign", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.CANVAS_SIGN.get()))
				.group("canvas_sign")
				.save(consumer);
		ShapelessRecipeBuilder.shapeless(ModItems.BLUE_CANVAS_SIGN.get(), 1)
				.requires(ModTags.CANVAS_SIGNS)
				.requires(Tags.Items.DYES_BLUE)
				.unlockedBy("has_canvas_sign", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.CANVAS_SIGN.get()))
				.group("canvas_sign")
				.save(consumer);
		ShapelessRecipeBuilder.shapeless(ModItems.BROWN_CANVAS_SIGN.get(), 1)
				.requires(ModTags.CANVAS_SIGNS)
				.requires(Tags.Items.DYES_BROWN)
				.unlockedBy("has_canvas_sign", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.CANVAS_SIGN.get()))
				.group("canvas_sign")
				.save(consumer);
		ShapelessRecipeBuilder.shapeless(ModItems.GREEN_CANVAS_SIGN.get(), 1)
				.requires(ModTags.CANVAS_SIGNS)
				.requires(Tags.Items.DYES_GREEN)
				.unlockedBy("has_canvas_sign", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.CANVAS_SIGN.get()))
				.group("canvas_sign")
				.save(consumer);
		ShapelessRecipeBuilder.shapeless(ModItems.RED_CANVAS_SIGN.get(), 1)
				.requires(ModTags.CANVAS_SIGNS)
				.requires(Tags.Items.DYES_RED)
				.unlockedBy("has_canvas_sign", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.CANVAS_SIGN.get()))
				.group("canvas_sign")
				.save(consumer);
		ShapelessRecipeBuilder.shapeless(ModItems.BLACK_CANVAS_SIGN.get(), 1)
				.requires(ModTags.CANVAS_SIGNS)
				.requires(Tags.Items.DYES_BLACK)
				.unlockedBy("has_canvas_sign", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.CANVAS_SIGN.get()))
				.group("canvas_sign")
				.save(consumer);
	}

	//private static void recipesMaterials(Consumer<FinishedRecipe> consumer) {
	//	//ShapedRecipeBuilder.shaped(ModItems.CANVAS.get())
	//	//		.pattern("##")
	//	//		.pattern("##")
	//	//		.define('#', ModItems.STRAW.get())
	//	//		.unlockedBy("has_straw", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.STRAW.get()))
	//	//		.save(consumer);
	//}
}
