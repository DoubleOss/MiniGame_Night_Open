package com.doubleos.night.item;

import com.doubleos.night.BlockInterface.IHasModel;
import com.doubleos.night.init.ModItems;
import com.doubleos.night.packet.Packet;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemBase extends Item implements IHasModel
{
    @Override
    public boolean hasEffect(ItemStack stack) {
        return false;
    }

    public ItemBase(String name, String koreaName)
    {

        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(com.doubleos.night.Night.jinwoo);

        ModItems.ITEMS.add(this);

    }

    @Override
    public void registerModels()
    {
        com.doubleos.night.Night.proxy.registerItemRenderer(this, 0, "inventory");
    }

}
