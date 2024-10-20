package com.doubleos.night.init;

import com.doubleos.night.block.CardViewBlock;
import com.doubleos.night.block.ObjBlockBase;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

public class ModBlocks
{
    public static final List<Block> BLOCKS = new ArrayList<Block>();

    public static final Block TEST_CAR = new ObjBlockBase("rope", Material.BARRIER, true);
    public static final Block GENRATOR = new ObjBlockBase("generator", Material.BARRIER, true);

//
//    public static final Block FLYINGBOX = new FacingBlock("fliyingbox", Material.BARRIER, true);
//
//    public static final Block FLYINGBOX2 = new FacingBlock("fliyingbox_2", Material.BARRIER, true);


    //public static final Block BATT22 = new ObjBlockBase("battery", Material.BARRIER, true);

    public static final Block CARD_TILE_BLOCK = new CardViewBlock("cardblock", Material.BARRIER, false);




    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void registerRenders(ModelRegistryEvent event)
    {

    }



    //public static final Block TEST_DREGON = new DregonBase("dregon", Material.BARRIER, true);


}
