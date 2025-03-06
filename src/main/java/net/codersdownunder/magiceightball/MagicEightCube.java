package net.codersdownunder.magiceightball;

import net.codersdownunder.magiceightball.data.CubeVariantManager;
import net.codersdownunder.magiceightball.items.CustomMagicEightCubeItem;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.AddServerReloadListenersEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Supplier;


@Mod(MagicEightCube.MODID)
public class MagicEightCube
{
    public static final String MODID = "magiceightcube";
    public static final Logger LOGGER = LogManager.getLogger(MODID);


    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);
    public static final DeferredRegister.DataComponents REGISTRAR = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, MODID);
    public static final CubeVariantManager CUBE_VARIANTS = new CubeVariantManager();

    public static final Supplier<DataComponentType<ResourceLocation>> CUBE_VARIANT = REGISTRAR.registerComponentType(
                "cube_variant",
                builder -> builder
                        .persistent(ResourceLocation.CODEC)
                        .networkSynchronized(ResourceLocation.STREAM_CODEC)
        );

    public static final DeferredItem<CustomMagicEightCubeItem> CustomMagicEightCube = ITEMS.registerItem(
            "custommagiceightcube",
            registryName -> new CustomMagicEightCubeItem(
                    new Item.Properties()
                            .setId(ResourceKey.create(Registries.ITEM,
                                    ResourceLocation.fromNamespaceAndPath(MODID, "custommagiceightcube"))).stacksTo(1)));

    // Creates a creative tab with the id "magiceightcube:example_tab" for the example item, that is placed after the combat tab
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> ModTab = CREATIVE_MODE_TABS.register("magiceightcube-tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.magiceightcube")) //The language key for the title of your CreativeModeTab
            .icon(() -> CustomMagicEightCube.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.acceptAll(CustomMagicEightCube.get().getSubItems());
            }).build());


    public MagicEightCube(IEventBus modEventBus, ModContainer modContainer)
    {

        BLOCKS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so items get registered
        ITEMS.register(modEventBus);

        CREATIVE_MODE_TABS.register(modEventBus);
        REGISTRAR.register(modEventBus);
        
        //MECConfig.loadConfig(MECConfig.CLIENT, FMLPaths.CONFIGDIR.get().resolve("magiceightcube-client.toml").toString());
        //NeoForge.EVENT_BUS.register(this);
        //modEventBus.addListener(this::onBuildContentsOfCreativeTabs);
        NeoForge.EVENT_BUS.addListener(this::onAddReloadListener);
    }

    private void onAddReloadListener(AddServerReloadListenersEvent event) {
        event.addListener(ResourceLocation.fromNamespaceAndPath(MODID, "cube_variants"), CUBE_VARIANTS);
    }

//    public void onBuildContentsOfCreativeTabs(BuildCreativeModeTabContentsEvent event) {
//        if (event.getTabKey() == ModTab.getKey()) {
//            //LOGGER.info("AAAAAAAAAAAAAAAAAAAAAAA****************** {}", CustomMagicEightCube.get().getSubItems());
//
//        }
//    }



}

