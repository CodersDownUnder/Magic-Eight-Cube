package net.codersdownunder.magiceightball.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.ListCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.animal.Cod;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public record CustomMagicEightCubeVariant(
        String title,
        List<String> badPhrases,
        List<String> unsurePhrases,
        List<String> goodPhrases,
        Component customName,
        boolean translatable
){
    public static final Codec<CustomMagicEightCubeVariant> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Codec.STRING.fieldOf("title").forGetter(bt -> bt.title),
                    Codec.list(Codec.STRING).fieldOf("badphrases").forGetter(bt -> bt.badPhrases),
                    Codec.list(Codec.STRING).fieldOf("unsurephrases").forGetter(bt -> bt.unsurePhrases),
                    Codec.list(Codec.STRING).fieldOf("goodphrases").forGetter(bt -> bt.goodPhrases),
                    ComponentSerialization.CODEC.fieldOf("display_name").forGetter(bt -> bt.customName),
                    Codec.BOOL.fieldOf("translatable").forGetter(bt -> bt.translatable)
            ).apply(instance, CustomMagicEightCubeVariant::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, CustomMagicEightCubeVariant> STREAM_CODEC = StreamCodec.of(
            CustomMagicEightCubeVariant::encode,
            CustomMagicEightCubeVariant::decode
    );


    private static void encode(RegistryFriendlyByteBuf buf, CustomMagicEightCubeVariant cube) {
        buf.writeUtf(cube.title);
        buf.writeCollection(cube.badPhrases, FriendlyByteBuf::writeUtf);
        buf.writeCollection(cube.unsurePhrases, FriendlyByteBuf::writeUtf);
        buf.writeCollection(cube.goodPhrases, FriendlyByteBuf::writeUtf);
        ComponentSerialization.STREAM_CODEC.encode(buf, cube.customName);
        buf.writeBoolean(cube.translatable);
    }

    private static CustomMagicEightCubeVariant decode(RegistryFriendlyByteBuf buf) {
        return new CustomMagicEightCubeVariant(
                buf.readUtf(),
                //Collections.singletonList(buf.readUtf()),
                buf.readCollection(ArrayList::new, FriendlyByteBuf::readUtf),
                buf.readCollection(ArrayList::new, FriendlyByteBuf::readUtf),
                buf.readCollection(ArrayList::new, FriendlyByteBuf::readUtf),
                ComponentSerialization.STREAM_CODEC.decode(buf),
                buf.readBoolean()
        );
    }

//    public CustomMagicEightCubeVariant(
//            String title,
//            List<String> phrases,
//            Component customName
//    ) {
//        this(
//                title,
//                phrases,
//                customName
//        );
//    }

}