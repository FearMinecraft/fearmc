package org.bukkit.craftbukkit;

import io.papermc.paper.util.OldEnumHolderable;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import org.bukkit.Sound;

import java.util.concurrent.atomic.AtomicInteger;

public class CraftSound extends OldEnumHolderable<Sound, SoundEvent> implements Sound {

    private static final AtomicInteger COUNTER = new AtomicInteger(0);

    public CraftSound(Holder<SoundEvent> soundEffect) {
        super(soundEffect, COUNTER.getAndIncrement());
    }

    // ⚡ inline-friendly + no overhead change
    public static Sound minecraftToBukkit(SoundEvent minecraft) {
        return CraftRegistry.minecraftToBukkit(minecraft, Registries.SOUND_EVENT);
    }

    public static Sound minecraftHolderToBukkit(Holder<SoundEvent> minecraft) {
        return CraftRegistry.minecraftHolderToBukkit(minecraft, Registries.SOUND_EVENT);
    }

    public static SoundEvent bukkitToMinecraft(Sound bukkit) {
        return CraftRegistry.bukkitToMinecraft(bukkit);
    }

    public static Holder<SoundEvent> bukkitToMinecraftHolder(Sound bukkit) {
        return CraftRegistry.bukkitToMinecraftHolder(bukkit);
    }
}
