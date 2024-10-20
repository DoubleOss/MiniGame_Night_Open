package com.doubleos.night.init;


import com.doubleos.night.item.ItemBase;
import com.doubleos.night.item.OBJItemBase;
import com.doubleos.night.util.Reference;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;
@Mod.EventBusSubscriber(modid= Reference.MODID)
public class ModItems
{
    public static final List<Item> ITEMS = new ArrayList<Item>();



    public static final Item YES_ITEM = new OBJItemBase("yes");
    public static final Item NO_ITEM = new OBJItemBase("no");

    public static final Item QUESTIONCARD = new OBJItemBase("question_card");
    public static final Item votrpaper = new OBJItemBase("votrpaper");
    public static final Item votrpaper_wan = new OBJItemBase("votrpaper_wan");
    public static final Item votrpaper_wan2 = new OBJItemBase("votrpaper_wan2");

    public static final Item cuff = new OBJItemBase("cuff");

    public static final Item policebong = new OBJItemBase("policebong");





}
