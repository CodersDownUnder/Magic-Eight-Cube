package net.codersdownunder.magiceightball.datagen;

import net.codersdownunder.magiceightball.MagicEightCube;
import net.codersdownunder.magiceightball.datagen.client.MagicEightCubeLanguageProvider;
import net.codersdownunder.magiceightball.datagen.client.MagicEightCubeModelProvider;
import net.codersdownunder.magiceightball.datagen.server.CustomCubeVariantProvider;
import net.codersdownunder.magiceightball.datagen.server.MagicEightCubeRecipeProvider;
import net.minecraft.DetectedVersion;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.metadata.PackMetadataGenerator;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.OverlayMetadataSection;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.util.InclusiveRange;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, modid = "magiceightcube")
public class MagicEightCubeDatagenHandler {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent.Client event) {

        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();


        generator.addProvider(true, new PackMetadataGenerator(packOutput)
                .add(OverlayMetadataSection.TYPE, new OverlayMetadataSection(List.of(
                        new OverlayMetadataSection.OverlayEntry(new InclusiveRange<>(0, Integer.MAX_VALUE), "pack_overlays_test"))))
                .add(PackMetadataSection.TYPE, new PackMetadataSection(
                        Component.translatable("flowerseeds.packmeta.description"),
                        DetectedVersion.BUILT_IN.getPackVersion(PackType.CLIENT_RESOURCES),
                        Optional.of(new InclusiveRange<>(0, Integer.MAX_VALUE)))));

        generator.addProvider(true, new MagicEightCubeLanguageProvider(packOutput));
        generator.addProvider(true, new MagicEightCubeModelProvider(packOutput));
        generator.addProvider(true, new MagicEightCubeRecipeProvider.Runner(packOutput, lookupProvider));
        generator.addProvider(true, new CustomCubeVariantProvider(generator));

    }
}
