package com.doubleos.night.handler;

import com.doubleos.night.util.Reference;
import net.minecraft.client.audio.Sound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.ArrayList;
import java.util.HashMap;

public class SoundHandler
{

    private static SoundEvent CARD;
    private static SoundEvent CORRECT;
    private static SoundEvent CURTAIN;
    private static SoundEvent DONE;
    private static SoundEvent FAIL;
    private static SoundEvent FLIP;
    private static SoundEvent GAME_OVER;
    private static SoundEvent TOKEN;

    public static ArrayList<String> m_soundListString = new ArrayList<>();

    public static HashMap<String, SoundEvent> m_hashStringGetSoundEvent = new HashMap<>();


    public static void registerSounds()
    {
        CARD = registerSound("CARD");
        CORRECT = registerSound("CORRECT");
        CURTAIN = registerSound("CURTAIN");
        DONE = registerSound("DONE");
        FAIL = registerSound("FAIL");
        FLIP = registerSound("FLIP");
        GAME_OVER = registerSound("GAME_OVER");
        TOKEN = registerSound("TOKEN");
    }

    static SoundEvent registerSound(String name)
    {
        ResourceLocation location = new ResourceLocation(Reference.MODID, name);
        SoundEvent soundEvent = new SoundEvent(location);

        soundEvent.setRegistryName(name);

        m_soundListString.add(name);
        m_hashStringGetSoundEvent.put(name, soundEvent);

        ForgeRegistries.SOUND_EVENTS.register(soundEvent);
        return soundEvent;
    }

}
