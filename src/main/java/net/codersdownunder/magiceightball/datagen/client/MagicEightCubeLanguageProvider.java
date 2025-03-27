package net.codersdownunder.magiceightball.datagen.client;

import net.codersdownunder.magiceightball.MagicEightCube;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class MagicEightCubeLanguageProvider extends LanguageProvider {
    public MagicEightCubeLanguageProvider(PackOutput output) {
        super(
                // Provided by the `GatherDataEvent.Client`.
                output,
                MagicEightCube.MODID,
                "en_us"
        );

    }

    @Override
    protected void addTranslations() {
        this.add("item.magiceightcube.name", "Magic Eight Cube");
        //this.add(MagicEightCube.CustomMagicEightCube.get(), "Magic Eight Cube - Custom");
        this.add("itemGroup.magiceightcube", "Magic Eight Cubes");

        this.add("text.eightball.message1", "Don’t count on it");
        this.add("text.eightball.message2", "My reply is no");
        this.add("text.eightball.message3", "My sources say no");
        this.add("text.eightball.message4", "Outlook not so good");
        this.add("text.eightball.message5", "Very doubtful");
        this.add("text.eightball.message6", "Reply hazy, try again");
        this.add("text.eightball.message7", "Ask again later");
        this.add("text.eightball.message8", "Better not tell you now");
        this.add("text.eightball.message9", "Cannot predict now");
        this.add("text.eightball.message10", "Concentrate and ask again");
        this.add("text.eightball.message11", "It is certain");
        this.add("text.eightball.message12", "It is decidedly so");
        this.add("text.eightball.message13", "Without a doubt");
        this.add("text.eightball.message14", "Yes definitely");
        this.add("text.eightball.message15", "You may rely on it");
        this.add("text.eightball.message16", "As I see it, yes");
        this.add("text.eightball.message17", "Most likely");
        this.add("text.eightball.message18", "Outlook good");
        this.add("text.eightball.message19", "Yes");
        this.add("text.eightball.message20", "Signs point to yes");
    }
}