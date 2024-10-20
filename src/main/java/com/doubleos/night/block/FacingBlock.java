package com.doubleos.night.block;


import com.doubleos.night.BlockInterface.IHasModel;
import com.doubleos.night.Night;
import com.doubleos.night.init.ModBlocks;
import com.doubleos.night.init.ModItems;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

public class FacingBlock extends BlockHorizontal implements IHasModel
{

    public FacingBlock(String name, Material materialIn, boolean unbreaking)
    {
        super(materialIn);
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(CreativeTabs.REDSTONE);

        if (unbreaking)
            setBlockUnbreakable();

        ModBlocks.BLOCKS.add(this);
        ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
    }



    @Override
    public void registerModels()
    {
        Night.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "variants");
    }
}
