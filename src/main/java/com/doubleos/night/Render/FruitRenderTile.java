package com.doubleos.night.Render;


import com.doubleos.night.Night;
import com.doubleos.night.block.tile.BlockTileCardView;
import com.doubleos.night.util.Render;
import com.doubleos.night.variable.MainGame;
import com.doubleos.night.variable.Variable;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class FruitRenderTile extends TileEntitySpecialRenderer<BlockTileCardView>
{

    @Override
    public void renderTileEntityFast(BlockTileCardView te, double x, double y, double z, float partialTicks, int destroyStage, float partial, BufferBuilder buffer) {


    }

    @Override
    public void render(BlockTileCardView te, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
    {
        int scale = te.cardId;
        if(Variable.getMainGame().gamePhase == MainGame.eGamePhase.REASON_DIRECT_Y_N || Variable.getMainGame().gamePhase == MainGame.eGamePhase.REASON_VOTING_TIME
                || Variable.getMainGame().gamePhase == MainGame.eGamePhase.REASON_VOTING_VIEW || Variable.getMainGame().gamePhase == MainGame.eGamePhase.POLICE_DETECTIVE)
        {


            GlStateManager.pushMatrix();
            {
                GlStateManager.translate(x,y, z);

                GlStateManager.rotate(180, 0, 0, 1);

                GlStateManager.translate(0f,-4.5, 2);
                GlStateManager.rotate(90, 0, 1, 0);

                GlStateManager.scale(0.08f, 0.08f, 0.08f);
                bindTexture(new ResourceLocation(Night.RESOURCEID, "textures\\hud\\card\\"+scale+".png"));
                Render.drawTexture(0, 0, 754/20f, 1080/20f, 0, 0, 1f, 1f, 1, 1);

            }
            GlStateManager.popMatrix();

        }
        else
        {


        }

    }


}
