package com.doubleos.night.creativetab;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class KimJinWooTab extends CreativeTabs
{
    public KimJinWooTab(String label) {
        super(label);
    }

    @Override
    public ItemStack getTabIconItem() {
        return Items.APPLE.getDefaultInstance();
    }
}
