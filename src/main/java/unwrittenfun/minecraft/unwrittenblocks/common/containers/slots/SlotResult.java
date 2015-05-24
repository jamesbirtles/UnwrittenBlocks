package unwrittenfun.minecraft.unwrittenblocks.common.containers.slots;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import org.lwjgl.input.Keyboard;
import unwrittenfun.minecraft.unwrittenblocks.common.tileEntities.ICraftingResult;

/**
 * Author: James Birtles
 */
public class SlotResult extends Slot {
  public SlotResult(IInventory inventory, int p_i1824_2_, int p_i1824_3_, int p_i1824_4_) {
    super(inventory, p_i1824_2_, p_i1824_3_, p_i1824_4_);
  }

  @Override
  public boolean isItemValid(ItemStack p_75214_1_) {
    return false;
  }

  @Override
  public void onPickupFromSlot(EntityPlayer player, ItemStack stack) {
    super.onSlotChanged();
    if (inventory instanceof ICraftingResult) {
      ((ICraftingResult) inventory).onResultTaken();
    }
  }

  @Override
  public boolean canTakeStack(EntityPlayer player) {
    return true;
  }
}
