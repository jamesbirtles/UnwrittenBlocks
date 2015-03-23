package unwrittenfun.minecraft.unwrittenblocks.common.network.receivers;

/**
 * Project: UnwrittenBlocks
 * Author: UnwrittenFun
 * Created: 05/11/2014.
 */
public interface ITileEntityIntegerMessageReceiver {
  public void receiveIntegerMessage(byte id, int value);
}
