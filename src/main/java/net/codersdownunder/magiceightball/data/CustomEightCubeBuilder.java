package net.codersdownunder.magiceightball.data;

import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import net.codersdownunder.magiceightball.MagicEightCube;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public class CustomEightCubeBuilder {
    public final ResourceLocation cubeVariantId;
    private final String title;
    private final List<String> badPhrases;
    private final List<String> unsurePhrases;
    private final List<String> goodPhrases;
    private Component displayName;
    private final boolean translatable;

    public CustomEightCubeBuilder(ResourceLocation cubeVariantId, String title, List<String> badPhrases, List<String> unsurePhrases, List<String> goodPhrases, boolean translatable) {
        this.cubeVariantId = cubeVariantId;
        this.title = title;
        this.badPhrases = badPhrases;
        this.unsurePhrases = unsurePhrases;
        this.goodPhrases = goodPhrases;
        this.displayName = Component.translatable(String.format("bag.%s.%s", this.cubeVariantId.getNamespace(), this.cubeVariantId.getPath()));
        this.translatable = translatable;

    }

    public CustomEightCubeBuilder displayName(Component text) {
        this.displayName = text;
        return this;
    }


    public JsonObject serialize() {


        CustomMagicEightCubeVariant cubeVariant = new CustomMagicEightCubeVariant(
                this.title,
                this.badPhrases,
                this.unsurePhrases,
                this.goodPhrases,
                this.displayName,
                this.translatable
        );

        var jsonElementDataResult = CustomMagicEightCubeVariant.CODEC.encodeStart(JsonOps.INSTANCE, cubeVariant);
        if (jsonElementDataResult.isError()) {
            MagicEightCube.LOGGER.error("Something went wrong serializing cube variant \"{}\"", this.cubeVariantId);
        }
        return jsonElementDataResult.getOrThrow().getAsJsonObject();
    }
}