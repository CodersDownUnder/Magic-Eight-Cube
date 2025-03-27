package net.codersdownunder.magiceightball.items;


import net.codersdownunder.magiceightball.MagicEightCube;
import net.codersdownunder.magiceightball.data.CustomMagicEightCubeVariant;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.CommonColors;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CustomMagicEightCubeItem extends Item {

    public CustomMagicEightCubeItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack heldItem = player.getItemInHand(hand);
        var variant = getCubeVariant(heldItem);

        if (!level.isClientSide) {
            assert variant != null;
            //MagicEightCube.LOGGER.info(variant.phrases());
            var badSize = variant.badPhrases().size();
            var unsureSize = variant.unsurePhrases().size();
            var goodSize = variant.goodPhrases().size();
            int rand1 = level.getRandom().nextInt(0, badSize + unsureSize + goodSize);
            //int rand1 = 6;

            int color = CommonColors.GREEN;

            if (rand1 < badSize) {
                color = CommonColors.RED;
            } else if (rand1 >= badSize && rand1 <= badSize + unsureSize - 1) {
                color = CommonColors.YELLOW;
            }

            List<String> combined = new ArrayList<String>();
            combined.addAll(variant.badPhrases());
            combined.addAll(variant.unsurePhrases());
            combined.addAll(variant.goodPhrases());

            sendSystemMessage(player, combined.get(rand1), color, variant.translatable());
        }
        return super.use(level, player, hand);
    }

    private void sendSystemMessage(Player player, String message, int color, boolean translatable) {
        if (translatable) {
            ((ServerPlayer) player).sendSystemMessage(Component.translatable(message)
                    .withStyle(Style.EMPTY.withBold(true).withColor(color).withShadowColor(CommonColors.BLACK).withUnderlined(true)), true);
            return;
        }
        ((ServerPlayer) player).sendSystemMessage(Component.literal(message)
                .withStyle(Style.EMPTY.withBold(true).withColor(color).withShadowColor(CommonColors.BLACK).withUnderlined(true)), true);
    }

    public ItemStack stackOfType(CustomMagicEightCubeVariant type) {
        return stackOfType(type, 1);
    }

    public ItemStack stackOfType(CustomMagicEightCubeVariant type, int count) {
        ItemStack result = new ItemStack(this, count);
        return setCubeProperties(result, type);
    }


    public ItemStack stackOfType(ResourceLocation typeName) {
        ItemStack result = new ItemStack(this);
        return setCubeVariant(result, typeName);
    }

    @Nullable
    public static CustomMagicEightCubeVariant getCubeVariant(ItemStack stack) {
        ResourceLocation id = stack.get(MagicEightCube.CUBE_VARIANT);
        return id != null ? MagicEightCube.CUBE_VARIANTS.get(id) : null;
    }


    public static ItemStack setCubeVariant(ItemStack stack, ResourceLocation bagTypeId) {
        stack.set(MagicEightCube.CUBE_VARIANT, bagTypeId);
        return stack;
    }

    public static ItemStack setCubeProperties(ItemStack stack, CustomMagicEightCubeVariant type) {
        setCubeVariant(stack, MagicEightCube.CUBE_VARIANTS.getKey(type));
        return stack;
    }

    @Nonnull
    @Override
    public Component getName(@Nonnull ItemStack stack) {
        CustomMagicEightCubeVariant type = getCubeVariant(stack);
        if (type != null) {
            return type.customName();
        }
        return super.getName(stack);
    }


    public List<ItemStack> getSubItems() {
        NonNullList<ItemStack> items = NonNullList.create();

        // Add for each type (sorted by ID)
        List<CustomMagicEightCubeVariant> list = new ArrayList<>(MagicEightCube.CUBE_VARIANTS.stream().toList());
        //MagicEightCube.LOGGER.info(list);
        list.sort(Comparator.comparing(cubeVariant -> MagicEightCube.CUBE_VARIANTS.getKey(cubeVariant).toString()));
        for (CustomMagicEightCubeVariant variant : list) {
                items.add(stackOfType(variant));
        }

        return items;
    }

}
