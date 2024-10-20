package com.doubleos.night;

import com.doubleos.night.block.tile.BlockTileCardView;
import com.doubleos.night.handler.HudConfig;
import com.doubleos.night.proxy.CommonProxy;
import com.doubleos.night.util.Reference;
import com.doubleos.night.command.GuiCommand;
import com.doubleos.night.creativetab.KimJinWooTab;
import com.doubleos.night.entity.EntityRope;
import com.doubleos.night.handler.SoundHandler;
import com.doubleos.night.packet.Packet;
import com.doubleos.night.variable.Variable;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.FMLEventChannel;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.apache.logging.log4j.Logger;

import java.awt.*;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION)
public class Night
{

    @Mod.Instance
    public static Night instance;

    private static Logger logger;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.COMMON_PROXY_CLASS)
    public static CommonProxy proxy;

    public static final FMLEventChannel channel = NetworkRegistry.INSTANCE.newEventDrivenChannel("KimJinWoo");

    public static final CreativeTabs jinwoo = new KimJinWooTab("KimJinWoo");

    public static final String RESOURCEID = "gamenight";

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        proxy.preInit(event);
        logger = event.getModLog();
        Packet.init();
        SoundHandler.registerSounds();

        int entityId = 0;

        EntityRegistry.registerModEntity(new ResourceLocation(Reference.MODID, "Rope"), EntityRope.class, "rope", entityId++, this, 64, 1, true, Color.WHITE.getRGB(), Color.BLACK.getRGB());
        Variable.Instance().init();
        HudConfig.init(event.getModConfigurationDirectory());
        GameRegistry.registerTileEntity(BlockTileCardView.class, new ResourceLocation(Reference.MODID, "cardtile"));

    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        // some example code
        proxy.init(event);



    }
    @EventHandler
    public void serverInit(FMLServerStartingEvent event)
    {
        proxy.serverInit(event);
        event.registerServerCommand(new GuiCommand());

    }
}
