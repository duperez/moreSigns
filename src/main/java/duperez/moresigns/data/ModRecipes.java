package duperez.moresigns.data;

import duperez.moresigns.MoreSigns;
import duperez.moresigns.common.registry.ModItems;
import duperez.moresigns.common.tag.ModTags;
import duperez.moresigns.common.variant.CanvasVariant;
import duperez.moresigns.common.variant.CanvasVariants;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

import java.util.concurrent.CompletableFuture;

public class ModRecipes extends RecipeProvider {
    private static final String SIGN_GROUP = "canvas_sign";

    public ModRecipes(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput output) {
        // Frames, boards and custom signs are made at this table, not in a crafting grid, so none of them can clash with a vanilla recipe.
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, ModItems.SIGN_WORKBENCH.get())
                .requires(Blocks.CRAFTING_TABLE)
                .requires(ItemTags.SIGNS)
                .unlockedBy("has_sign", has(ItemTags.SIGNS))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModItems.CANVAS.get(), 3)
                .pattern("###")
                .pattern("###")
                .define('#', Items.STRING)
                .unlockedBy("has_string", has(Items.STRING))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModItems.sign(CanvasVariants.PLAIN).get(), 3)
                .pattern("w#w")
                .pattern("w#w")
                .pattern(" / ")
                .define('w', ItemTags.PLANKS)
                .define('#', ModItems.CANVAS.get())
                .define('/', Items.STICK)
                .unlockedBy("has_canvas", has(ModItems.CANVAS.get()))
                .save(output);

        for (CanvasVariant variant : CanvasVariants.DYED) {
            ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, ModItems.sign(variant).get())
                    .requires(ModTags.CANVAS_SIGNS)
                    .requires(dyeTag(variant.dye()))
                    .group(SIGN_GROUP)
                    .unlockedBy("has_canvas_sign", has(ModTags.CANVAS_SIGNS))
                    .save(output);
        }

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.BOOK)
                .requires(Items.PAPER, 3)
                .requires(ModItems.CANVAS.get())
                .unlockedBy("has_canvas", has(ModItems.CANVAS.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(MoreSigns.MODID, "book_from_canvas"));

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, Items.PAINTING)
                .pattern("sss")
                .pattern("scs")
                .pattern("sss")
                .define('s', Items.STICK)
                .define('c', ModItems.CANVAS.get())
                .unlockedBy("has_canvas", has(ModItems.CANVAS.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(MoreSigns.MODID, "painting_from_canvas"));

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, Items.SCAFFOLDING, 6)
                .pattern("b#b")
                .pattern("b b")
                .pattern("b b")
                .define('b', Items.BAMBOO)
                .define('#', ModItems.CANVAS.get())
                .unlockedBy("has_canvas", has(ModItems.CANVAS.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(MoreSigns.MODID, "scaffolding_from_canvas"));
    }

    private static TagKey<Item> dyeTag(String dye) {
        return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "dyes/" + dye));
    }
}
