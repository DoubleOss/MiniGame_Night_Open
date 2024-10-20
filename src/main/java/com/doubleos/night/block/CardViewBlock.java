package com.doubleos.night.block;

import com.doubleos.night.BlockInterface.IHasModel;
import com.doubleos.night.BlockInterface.IObjModel;
import com.doubleos.night.Night;
import com.doubleos.night.block.tile.BlockTileCardView;
import com.doubleos.night.init.ModBlocks;
import com.doubleos.night.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class CardViewBlock extends Block implements IHasModel, IObjModel, ITileEntityProvider
{

    public CardViewBlock(String name, Material blockMaterialIn, boolean unbreaking)
    {
        super(blockMaterialIn);
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(Night.jinwoo);
        setLightOpacity(0);
        setLightLevel(0f);


        if (unbreaking)
            setBlockUnbreakable();

        ModBlocks.BLOCKS.add(this);
        ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
    }

    @Override
    public void registerModels() {

    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.SOLID;
    }
    @Override
    public EnumBlockRenderType getRenderType(IBlockState iBlockState) {
        return EnumBlockRenderType.INVISIBLE;
    }
    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));

    }



    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new BlockTileCardView();
    }

    @Override
    public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn)
    {

    }


}
