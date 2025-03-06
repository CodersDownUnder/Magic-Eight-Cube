package net.codersdownunder.magiceightball.datagen.client;

import net.codersdownunder.magiceightball.MagicEightCube;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;

public class MagicEightCubeModelProvider extends ModelProvider {

    public MagicEightCubeModelProvider(PackOutput output) {
        // Replace "examplemod" with your own mod id.
        super(output, "magiceightcube");
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {

        justClientItemPlease(itemModels, MagicEightCube.CustomMagicEightCube);

        }

    private void justClientItemPlease(ItemModelGenerators itemModels, DeferredItem<? extends Item> item)
    {
        itemModels.itemModelOutput.accept(item.get(),
                ItemModelUtils.plainModel(ModelLocationUtils.getModelLocation(item.get())));
    }
    }