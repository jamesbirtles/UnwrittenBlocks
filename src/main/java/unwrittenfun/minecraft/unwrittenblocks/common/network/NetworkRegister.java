package unwrittenfun.minecraft.unwrittenblocks.common.network;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import unwrittenfun.minecraft.unwrittenblocks.common.ModInfo;
import unwrittenfun.minecraft.unwrittenblocks.common.network.handlers.TileEntityIOSideHandler;
import unwrittenfun.minecraft.unwrittenblocks.common.network.handlers.TileEntityIntegerHandler;
import unwrittenfun.minecraft.unwrittenblocks.common.network.handlers.TileEntityRequestHandler;
import unwrittenfun.minecraft.unwrittenblocks.common.network.handlers.TileEntityStackHandler;
import unwrittenfun.minecraft.unwrittenblocks.common.network.messages.TileEntityIOSideMessage;
import unwrittenfun.minecraft.unwrittenblocks.common.network.messages.TileEntityIntegerMessage;
import unwrittenfun.minecraft.unwrittenblocks.common.network.messages.TileEntityRequestMessage;
import unwrittenfun.minecraft.unwrittenblocks.common.network.messages.TileEntityStackMessage;

/**
 * Project: UnwrittenBlocks
 * Author: UnwrittenFun
 * Created: 05/11/2014.
 */
public class NetworkRegister {
  public static SimpleNetworkWrapper wrapper;

  public static void initMessages() {
    wrapper = NetworkRegistry.INSTANCE.newSimpleChannel(ModInfo.MOD_ID);
    wrapper.registerMessage(TileEntityIntegerHandler.class, TileEntityIntegerMessage.class, 0, Side.CLIENT);
    wrapper.registerMessage(TileEntityStackHandler.class, TileEntityStackMessage.class, 1, Side.CLIENT);
    wrapper.registerMessage(TileEntityRequestHandler.class, TileEntityRequestMessage.class, 2, Side.SERVER);
    wrapper.registerMessage(TileEntityIOSideHandler.class, TileEntityIOSideMessage.class, 2, Side.SERVER);
  }
}
