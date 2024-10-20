package com.doubleos.night.block;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.client.model.ModelLoader;

public class StartupClientOnly {
    public static void preInitClientOnly() {
        // This step is necessary in order to make your block render properly when it is an item (i.e. in the inventory
        //   or in your hand or thrown on the ground).
        // It must be done on client only, and must be done after the block has been created in Common.preinit().

        ModelResourceLocation itemModelResourceLocation = new ModelResourceLocation("night:fliyingbox", "inventory");
        ModelResourceLocation itemModelResourceLocation2 = new ModelResourceLocation("night:fliyingbox_2", "inventory");

        final int DEFAULT_ITEM_SUBTYPE = 0;
        ModelLoader.setCustomModelResourceLocation(StartupCommon.itemBlockPartial, DEFAULT_ITEM_SUBTYPE, itemModelResourceLocation);
        ModelLoader.setCustomModelResourceLocation(StartupCommon.itemBlockPartial2, DEFAULT_ITEM_SUBTYPE, itemModelResourceLocation2);


    }

    public static void initClientOnly() {
    }

    public static void postInitClientOnly() {
    }
}