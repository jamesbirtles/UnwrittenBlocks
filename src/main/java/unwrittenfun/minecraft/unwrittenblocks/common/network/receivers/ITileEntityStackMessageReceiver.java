package unwrittenfun.minecraft.unwrittenblocks.common.network.receivers;

import net.minecraft.item.ItemStack;

/**
 * Project: UnwrittenBlocks
 * Author: UnwrittenFun
 * Created: 05/11/2014.
 */
public interface ITileEntityStackMessageReceiver {
  public void receiveStackMessage(byte id, ItemStack stack);
}
