package com.doubleos.night.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityRope extends Entity
{

    public float RenderSize = 1f;

    private static DataParameter<Float> SPEED = EntityDataManager.createKey(EntityBoat.class, DataSerializers.FLOAT);

    public double curRoll;

    public double preRoll;

    public boolean fristCheck = false;
    public int zero_Y_Count = 1;

    public int m_lifeTime = 0;


    public EntityRope(World worldIn)
    {
        super(worldIn);
        setSize(RenderSize, RenderSize);
    }

    @Override
    protected void entityInit()
    {

    }

    @Override
    public boolean canBeCollidedWith()
    {
        return true;
    }


    @Override
    public void onCollideWithPlayer(EntityPlayer entityIn)
    {

    }


    @Override
    public void onEntityUpdate()
    {
        super.onEntityUpdate();
    }


    @Override
    protected void readEntityFromNBT(NBTTagCompound compound) {

    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound compound) {

    }
}
