package unwrittenfun.minecraft.unwrittenblocks.common.containers.slots;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

/**
 * Author: James Birtles
 */
public class SlotUpgrade extends Slot {
  public SlotUpgrade(IInventory inventory, int index, int x, int y) {
    super(inventory, index, x, y);
  }

  @Override
  public int getSlotStackLimit() {
    return 4;
  }
}
