package net.codersdownunder.magiceightball.datagen.server;

import net.codersdownunder.magiceightball.MagicEightCube;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.crafting.DataComponentIngredient;

import java.util.concurrent.CompletableFuture;

public class MagicEightCubeRecipeProvider extends RecipeProvider {

    // Construct the provider to run
    protected MagicEightCubeRecipeProvider(HolderLookup.Provider provider, RecipeOutput output) {
        super(provider, output);
    }

    @Override
    protected void buildRecipes() {




        shaped(MagicEightCube.CustomMagicEightCube.get().stackOfType(ResourceLocation.fromNamespaceAndPath(MagicEightCube.MODID, "magiceightcube")))
                .pattern("IGI")
                .pattern("ICI")
                .pattern("III")
                .define('I', Tags.Items.DYES_BLACK)
                .define('C', Tags.Items.COBBLESTONES)
                .define('G', Tags.Items.GLASS_BLOCKS)
                .unlockedBy("has_ink", has(Tags.Items.DYES_BLACK))
                .save(output, ResourceLocation.fromNamespaceAndPath(MagicEightCube.MODID,"magiceightcube").toString());

        ItemStack result = new ItemStack(MagicEightCube.CustomMagicEightCube.get());
        result.set(MagicEightCube.CUBE_VARIANT, ResourceLocation.fromNamespaceAndPath(MagicEightCube.MODID, "example"));

                shaped(MagicEightCube.CustomMagicEightCube.get().stackOfType(ResourceLocation.fromNamespaceAndPath(MagicEightCube.MODID, "example")))
                .pattern("IGI")
                .pattern("ICI")
                .pattern("III")
                .define('I', Tags.Items.DYES_BLACK)
                .define('C', Tags.Items.STORAGE_BLOCKS_IRON)
                .define('G', Tags.Items.GLASS_BLOCKS)
                .unlockedBy("has_ironblock", has(Tags.Items.STORAGE_BLOCKS_IRON))
                        .save(output, ResourceLocation.fromNamespaceAndPath(MagicEightCube.MODID,"examplecube").toString());



    }

    protected ShapedRecipeBuilder shaped(ItemStack result) {
        return ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, result);
    }



    // The runner to add to the data generator
    public static class Runner extends RecipeProvider.Runner {
        // Get the parameters from the `GatherDataEvent`s.
        public Runner(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
            super(output, lookupProvider);
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider provider, RecipeOutput output) {
            return new MagicEightCubeRecipeProvider(provider, output);
        }

        @Override
        public String getName() {
            return "Recipes";
        }
    }
}