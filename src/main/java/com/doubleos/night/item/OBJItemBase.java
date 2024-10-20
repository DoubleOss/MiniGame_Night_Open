package com.doubleos.night.item;

import com.doubleos.night.BlockInterface.IHasModel;
import com.doubleos.night.BlockInterface.IObjModel;
import com.doubleos.night.init.ModItems;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class OBJItemBase extends Item implements IHasModel, IObjModel
{
    public boolean m_powerOn = false;

    public OBJItemBase(String name)
    {
        super();

        setUnlocalizedName(name);

        setRegistryName(name);
        setCreativeTab(com.doubleos.night.Night.jinwoo);

        ModItems.ITEMS.add(this);


    }

    @Override
    public void registerModels() {

    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));

    }


    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand hand)
    {

        return super.onItemRightClick(worldIn, player, hand);
    }

}
