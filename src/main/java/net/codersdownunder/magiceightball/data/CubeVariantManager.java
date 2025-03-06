package net.codersdownunder.magiceightball.data;

import net.codersdownunder.magiceightball.MagicEightCube;
import net.codersdownunder.magiceightball.items.CustomMagicEightCubeItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

public final class CubeVariantManager extends DataResourceManager<CustomMagicEightCubeVariant> {
    public CubeVariantManager() {
        super(
                CustomMagicEightCubeVariant.CODEC,
                CubeVariantJsonException::new,
                "cube variant",
                "cube_variants",
                "CubeVariantManager",
                MagicEightCube.LOGGER
        );
    }

    @Nullable
    public CustomMagicEightCubeVariant typeFromCube(ItemStack stack) {
        if (!(stack.getItem() instanceof CustomMagicEightCubeItem)) return null;
        return CustomMagicEightCubeItem.getCubeVariant(stack);
    }


    private static Component errorMessage(String str) {
        return Component.literal("[Magic Eight Cube] ").withStyle(ChatFormatting.YELLOW)
                .append(Component.literal(str).withStyle(ChatFormatting.WHITE));
    }
}