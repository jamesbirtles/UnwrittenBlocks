package unwrittenfun.minecraft.wallteleporters;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import net.minecraftforge.common.MinecraftForge;
import unwrittenfun.minecraft.wallteleporters.blocks.WTBlocks;
import unwrittenfun.minecraft.wallteleporters.handlers.ConfigHandler;
import unwrittenfun.minecraft.wallteleporters.handlers.EventHandler;
import unwrittenfun.minecraft.wallteleporters.handlers.GuiHandler;
import unwrittenfun.minecraft.wallteleporters.handlers.PacketHandler;
import unwrittenfun.minecraft.wallteleporters.info.ModInfo;
import unwrittenfun.minecraft.wallteleporters.items.WTItems;

/**
 * Mod: Wall Teleporters
 * Author: UnwrittenFun
 * License: Minecraft Mod Public License (Version 1.0.1)
 */
@Mod(modid = ModInfo.ID, name = ModInfo.NAME, version = ModInfo.VERSION)
@NetworkMod(channels = { ModInfo.CHANNEL }, clientSideRequired = true, serverSideRequired = false,
        packetHandler = PacketHandler.class)
public class WallTeleporters {
    @Mod.Instance(ModInfo.ID)
    public static WallTeleporters instance;

    @SidedProxy(clientSide = "unwrittenfun.minecraft.wallteleporters.client.ClientProxy",
            serverSide = "unwrittenfun.minecraft.wallteleporters.CommonProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new EventHandler());

        ConfigHandler.init(event.getSuggestedConfigurationFile());

        WTBlocks.init();
        WTItems.init();
    }

    @Mod.EventHandler
    public void load(FMLInitializationEvent event) {
        WTBlocks.addNames();
        WTItems.addNames();

        WTBlocks.registerRecipes();
        WTItems.registerRecipes();

        WTBlocks.registerTileEntity();

        new GuiHandler();
    }

    @Mod.EventHandler
    public void modsLoaded(FMLPostInitializationEvent event) {
    }
}
