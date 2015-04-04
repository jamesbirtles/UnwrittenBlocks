package unwrittenfun.minecraft.unwrittenblocks.common.tileEntities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import org.apache.commons.lang3.ArrayUtils;
import unwrittenfun.minecraft.unwrittenblocks.common.UnwrittenBlocks;
import unwrittenfun.minecraft.unwrittenblocks.common.helpers.InventoryHelpers;
import unwrittenfun.minecraft.unwrittenblocks.common.network.NetworkRegister;
import unwrittenfun.minecraft.unwrittenblocks.common.network.messages.TileEntityIOSideMessage;
import unwrittenfun.minecraft.unwrittenblocks.common.network.receivers.ITileEntityIOSidesMessageReceiver;

import java.util.ArrayList;

/**
 * Project: UnwrittenBlocks
 * Author: UnwrittenFun
 * Created: 05/11/2014.
 */
public abstract class TEConfigurableIO extends TileEntity implements ISidedInventory, ITileEntityIOSidesMessageReceiver {
  public int[] ioSides = new int[]{0, 0, 0, 0, 0, 0};
  public ItemStack[] items = new ItemStack[0];

  @Override
  public void readFromNBT(NBTTagCompound compound) {
    super.readFromNBT(compound);
    ioSides = compound.getIntArray("IOConfig");

    InventoryHelpers.readInventoryFromNBT(compound, this);
  }

  @Override
  public void writeToNBT(NBTTagCompound compound) {
    super.writeToNBT(compound);
    compound.setIntArray("IOConfig", ioSides);

    InventoryHelpers.writeInventoryToNBT(compound, this);
  }


  // ISidedInventory

  @Override
  public int[] getAccessibleSlotsFromSide(int side) {
    ArrayList<Integer> slots = new ArrayList<Integer>();
    if (isInput(side)) {
      slots.addAll(getInputSlots());
    }
    if (isOutput(side)) {
      slots.addAll(getOutputSlots());
    }
    return ArrayUtils.toPrimitive(slots.toArray(new Integer[slots.size()]));
  }

  public boolean isInput(int side) {
    return (ioSides[side] & 1) == 1;
  }

  public abstract ArrayList<Integer> getInputSlots();

  public boolean isOutput(int side) {
    return (ioSides[side] & 2) == 2;
  }

  public abstract ArrayList<Integer> getOutputSlots();

  @Override
  public boolean canInsertItem(int slot, ItemStack stack, int side) {
    return getInputSlots().contains(slot) && isInput(side) && isItemValidForSlot(slot, stack);
  }

  @Override
  public boolean canExtractItem(int slot, ItemStack stack, int side) {
    return getOutputSlots().contains(slot) && isOutput(side);
  }


  // IInventory

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
    return true;
  }

  public void onInventoryChanged() {

  }

  @Override
  public void receiveIOSideMessage(int side, int value) {
    setSideIO(side, value);
  }

  public void setSideIO(int side, int value) {
    ioSides[side] = value;
    if (hasWorldObj() && worldObj.isRemote) {
      NetworkRegister.wrapper.sendToServer(TileEntityIOSideMessage.messageFrom(worldObj, xCoord, yCoord, zCoord, side, value));
    }
  }
}
