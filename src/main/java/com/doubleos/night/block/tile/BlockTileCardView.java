package com.doubleos.night.block.tile;

import com.doubleos.night.packet.CPacketBlockTileCardSync;
import com.doubleos.night.packet.Packet;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraftforge.fml.server.FMLServerHandler;

public class BlockTileCardView extends TileEntity implements ITickable
{
    public int cardId = 0;

    public int m_growTick = 0;

    @Override
    public double getMaxRenderDistanceSquared() {
        return 1024d;
    }

    @Override
    public void update() {
        m_growTick++;
        if(m_growTick >= 200)
        {
            m_growTick = 0;
            if(!this.world.isRemote)
            {
                Packet.networkWrapper.sendToAll(new CPacketBlockTileCardSync(pos, cardId));
            }

        }
    }
}
