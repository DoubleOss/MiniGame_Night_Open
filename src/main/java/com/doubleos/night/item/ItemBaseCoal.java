package com.doubleos.night.item;


import com.doubleos.night.BlockInterface.IHasModel;
import com.doubleos.night.Night;
import com.doubleos.night.init.ModItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class ItemBaseCoal extends Item implements IHasModel
{
    @Override
    public boolean hasEffect(ItemStack stack) {
        return false;
    }

    public ItemBaseCoal(String name, String koreaName)
    {

        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(com.doubleos.night.Night.jinwoo);

        ModItems.ITEMS.add(this);

    }

    @Override
    public int getItemBurnTime(ItemStack itemStack)
    {
        return 300;
    }

    @Override
    public void registerModels()
    {
        Night.proxy.registerItemRenderer(this, 0, "inventory");
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand hand)
    {
        if(!worldIn.isRemote)
        {
            player.sendMessage(new TextComponentString("Right Click"));

        }
        return super.onItemRightClick(worldIn, player, hand);

    }

}
