package unwrittenfun.minecraft.unwrittenblocks.common.tileEntities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import unwrittenfun.minecraft.unwrittenblocks.common.multiblock.WallTeleporterNetwork;

/**
 * Author: James Birtles
 */
public class TEWallTeleporterBase extends TEWallTeleporter implements IInventory {
  public TEWallTeleporterBase() {
    network = new WallTeleporterNetwork(this);
  }

  @Override
  public boolean hasWTNetwork() {
    return true;
  }

  @Override
  public void connectToWallsAround() {
    for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
      TileEntity tileEntity = worldObj.getTileEntity(xCoord + direction.offsetX, yCoord + direction.offsetY, zCoord + direction.offsetZ);
      if (tileEntity instanceof IWallTeleporterBlock) {
        IWallTeleporterBlock teleporter = (IWallTeleporterBlock) tileEntity;
        if (!teleporter.shouldIgnoreWT()) {
          teleporter.connectToWallsAround();
        }
      }
    }
  }

  public ItemStack[] items = new ItemStack[3];

  @Override
  public int getSizeInventory() {
    return items.length;
  }

  @Override
  public ItemStack getStackInSlot(int slot) {
    return slot < getSizeInventory() ? items[slot] : null;
  }

  @Override
  public ItemStack decrStackSize(int slot, int amount) {
    ItemStack stack = getStackInSlot(slot);
    if (stack != null) {
      if (stack.stackSize <= amount) {
        setInventorySlotContents(slot, null);
      } else {
        stack = stack.splitStack(amount);
        onInventoryChanged();
      }
    }
    return stack;
  }

  @Override
  public ItemStack getStackInSlotOnClosing(int slot) {
    ItemStack stack = getStackInSlot(slot);
    setInventorySlotContents(slot, null);
    return stack;
  }

  @Override
  public void setInventorySlotContents(int slot, ItemStack stack) {
    items[slot] = stack;
    onInventoryChanged();
  }

  @Override
  public String getInventoryName() {
    return "unwrittenblocks.wallTeleporterBase.name";
  }

  @Override
  public boolean hasCustomInventoryName() {
    return false;
  }

  @Override
  public int getInventoryStackLimit() {
    return 64;
  }

  @Override
  public boolean isUseableByPlayer(EntityPlayer player) {
    return true;
  }

  @Override
  public void openInventory() {
  }

  @Override
  public void closeInventory() {
  }

  @Override
  public boolean isItemValidForSlot(int slot, ItemStack stack) {
    return true; // TODO: Be smart.
  }

  public void onInventoryChanged() {
    // TODO: Implement
  }
}
