package net.codersdownunder.magiceightball.datagen.server;

import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.codersdownunder.magiceightball.MagicEightCube;
import net.codersdownunder.magiceightball.data.CustomEightCubeBuilder;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class CustomCubeVariantProvider implements DataProvider {
    static final Logger LOGGER = LogManager.getLogger();
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();
    private final DataGenerator generator;

    public CustomCubeVariantProvider(DataGenerator generator) {
        this.generator = generator;
    }

    @Override
    public String getName() {
        return "Magic Eight Cube Types";
    }

    protected List<CustomEightCubeBuilder> getBagTypes() {
        List<CustomEightCubeBuilder> ret = new ArrayList<>();


        List<String> goodPhrasesMain = new ArrayList<>();
        goodPhrasesMain.add("text.eightball.message11");
        goodPhrasesMain.add("text.eightball.message12");
        goodPhrasesMain.add("text.eightball.message13");
        goodPhrasesMain.add("text.eightball.message14");
        goodPhrasesMain.add("text.eightball.message15");
        goodPhrasesMain.add("text.eightball.message16");
        goodPhrasesMain.add("text.eightball.message17");
        goodPhrasesMain.add("text.eightball.message18");
        goodPhrasesMain.add("text.eightball.message19");
        goodPhrasesMain.add("text.eightball.message20");

        List<String> unsurePhrasesMain = new ArrayList<>();
        unsurePhrasesMain.add("text.eightball.message6");
        unsurePhrasesMain.add("text.eightball.message7");
        unsurePhrasesMain.add("text.eightball.message8");
        unsurePhrasesMain.add("text.eightball.message9");
        unsurePhrasesMain.add("text.eightball.message10");

        List<String> badPhrasesMain = new ArrayList<>();
        badPhrasesMain.add("text.eightball.message1");
        badPhrasesMain.add("text.eightball.message2");
        badPhrasesMain.add("text.eightball.message3");
        badPhrasesMain.add("text.eightball.message4");
        badPhrasesMain.add("text.eightball.message5");

        ret.add(new CustomEightCubeBuilder(ResourceLocation.fromNamespaceAndPath(MagicEightCube.MODID, "magiceightcube"), "magiceightcube", badPhrasesMain, unsurePhrasesMain, goodPhrasesMain, true).displayName(Component.translatable("item.magiceightcube.name")));

        List<String> badPhrases = new ArrayList<String>();
        badPhrases.add("no");
        badPhrases.add("Pay $1.99 for 5 more answers.");

        List<String> unsurePhrases = new ArrayList<String>();
        unsurePhrases.add("Go ask a real person.");
        unsurePhrases.add("Please shake again.");

        List<String> goodPhrases = new ArrayList<String>();
        goodPhrases.add("How Do You Keep An Idiot Amused? Please Shake For Answer.");
        goodPhrases.add("Think, what would a Jedi do?");
        goodPhrases.add("Don't do it, your friends are idiots.");

        ret.add(new CustomEightCubeBuilder(
                ResourceLocation.fromNamespaceAndPath(MagicEightCube.MODID, "example"),
                "example", badPhrases, unsurePhrases, goodPhrases, false).displayName(Component.literal("Example Cube")));




//        List<String> testList2 = new ArrayList<String>();
//        testList2.add("test3");
//        testList2.add("test24");
//        testList2.add("test35");
//
//        ret.add(new CustomEightCubeBuilder(
//                ResourceLocation.fromNamespaceAndPath(MagicEightCube.MODID, "test2"),
//                "test2", testList2).displayName(Component.literal("Test Cube 2")));

        return ret;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        Path outputFolder = this.generator.getPackOutput().getOutputFolder();
        Set<ResourceLocation> entries = Sets.newHashSet();
        List<CompletableFuture<?>> list = new ArrayList<>();

        //noinspection OverlyLongLambda
        getBagTypes().forEach(builder -> {
            if (entries.contains(builder.cubeVariantId)) {
                throw new IllegalStateException("Duplicate cube variants: " + builder.cubeVariantId);
            }

            entries.add(builder.cubeVariantId);
            Path path = outputFolder.resolve(String.format("data/%s/cube_variants/%s.json", builder.cubeVariantId.getNamespace(), builder.cubeVariantId.getPath()));
            list.add(DataProvider.saveStable(cache, builder.serialize(), path));
        });

        return CompletableFuture.allOf(list.toArray(new CompletableFuture[0]));
    }
}