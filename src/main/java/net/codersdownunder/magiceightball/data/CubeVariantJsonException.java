package net.codersdownunder.magiceightball.data;

import net.minecraft.resources.ResourceLocation;

public class CubeVariantJsonException extends RuntimeException {
    public CubeVariantJsonException(ResourceLocation name, String packName, Throwable cause) {
        super("Error loading \"" + name + "\" from pack \"" + packName + "\": " + cause.getMessage(), cause);
    }
}