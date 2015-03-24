package unwrittenfun.minecraft.unwrittenblocks.common.network;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import unwrittenfun.minecraft.unwrittenblocks.common.ModInfo;
import unwrittenfun.minecraft.unwrittenblocks.common.network.handlers.*;
import unwrittenfun.minecraft.unwrittenblocks.common.network.messages.*;

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
    wrapper.registerMessage(TeleporterDestinationHandler.class, TeleporterDestinationMessage.class, 5, Side.CLIENT);

    wrapper.registerMessage(TileEntityRequestHandler.class, TileEntityRequestMessage.class, 2, Side.SERVER);
    wrapper.registerMessage(TileEntityIOSideHandler.class, TileEntityIOSideMessage.class, 3, Side.SERVER);
    wrapper.registerMessage(TileEntityIntegerHandler.class, TileEntityIntegerMessage.class, 4, Side.SERVER);
    wrapper.registerMessage(TileEntityStackHandler.class, TileEntityStackMessage.class, 6, Side.SERVER);
  }
}
