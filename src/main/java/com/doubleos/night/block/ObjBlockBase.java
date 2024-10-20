package com.doubleos.night.block;


import com.doubleos.night.BlockInterface.IHasModel;
import com.doubleos.night.BlockInterface.IObjModel;
import com.doubleos.night.init.ModBlocks;
import com.doubleos.night.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ObjBlockBase extends Block implements IHasModel, IObjModel
{


    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT_MIPPED;

    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    public ObjBlockBase(String name, Material blockMaterialIn, boolean unbreaking)
    {
        super(blockMaterialIn);
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(com.doubleos.night.Night.jinwoo);
        setLightOpacity(0);
        setLightLevel(1f);


        if (unbreaking)
            setBlockUnbreakable();

        ModBlocks.BLOCKS.add(this);
        ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));

    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return new AxisAlignedBB(0.D, 0.0D, 0.0d, 1D, 1D, 1D);
    }




    @Override
    public void registerModels() {

    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));

    }
}
