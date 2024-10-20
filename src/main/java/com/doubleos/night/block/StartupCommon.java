package com.doubleos.night.block;

import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class StartupCommon {
    public static BlockPartial blockPartial;  // this holds the unique instance of your block
    public static ItemBlock itemBlockPartial;  // this holds the instance of the ItemBlock for your Block
    public static BlockPartial blockPartial2;  // this holds the unique instance of your block
    public static ItemBlock itemBlockPartial2;  // this holds the instance of the ItemBlock for your Block

    public static BlockPartial blockPartial3;  // this holds the unique instance of your block
    public static ItemBlock itemBlockPartial3;  // this holds the instance of the ItemBlock for your Block


    public static void preInitCommon()
    {

        blockPartial = (BlockPartial)(new BlockPartial().setUnlocalizedName("fliyingbox"));
        blockPartial.setRegistryName("fliyingbox");
        ForgeRegistries.BLOCKS.register(blockPartial);

        // We also need to create and register an ItemBlock for this block otherwise it won't appear in the inventory
        itemBlockPartial = new ItemBlock(blockPartial);
        itemBlockPartial.setRegistryName(blockPartial.getRegistryName());
        ForgeRegistries.ITEMS.register(itemBlockPartial);

        blockPartial2 = (BlockPartial)(new BlockPartial().setUnlocalizedName("fliyingbox_2"));
        blockPartial2.setRegistryName("fliyingbox_2");
        ForgeRegistries.BLOCKS.register(blockPartial2);

        // We also need to create and register an ItemBlock for this block otherwise it won't appear in the inventory
        itemBlockPartial2 = new ItemBlock(blockPartial2);
        itemBlockPartial2.setRegistryName(blockPartial2.getRegistryName());
        ForgeRegistries.ITEMS.register(itemBlockPartial2);



    }

    public static void initCommon()
    {
    }

    public static void postInitCommon()
    {
    }
}
