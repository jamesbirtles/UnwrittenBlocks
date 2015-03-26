package unwrittenfun.minecraft.unwrittenblocks.common.tileEntities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.util.ForgeDirection;
import unwrittenfun.minecraft.unwrittenblocks.common.helpers.InventoryHelpers;
import unwrittenfun.minecraft.unwrittenblocks.common.multiblock.WallTeleporterNetwork;
import unwrittenfun.minecraft.unwrittenblocks.common.network.NetworkRegister;
import unwrittenfun.minecraft.unwrittenblocks.common.network.messages.TileEntityRequestMessage;
import unwrittenfun.minecraft.unwrittenblocks.common.network.receivers.ITileEntityIntegerMessageReceiver;

/**
 * Author: James Birtles
 */
public class TEWallTeleporterBase extends TEWallTeleporter implements IInventory, ITileEntityIntegerMessageReceiver {

  public TEWallTeleporterBase() {
    network = new WallTeleporterNetwork(this);
  }

  @Override
  public void updateEntity() {
    super.updateEntity();

    if (getWTNetwork().cooldown > 0) {
      getWTNetwork().cooldown--;
    }
  }

  @Override
  protected void onLoaded() {
    super.onLoaded();
    if (worldObj.isRemote) { // If on client, request info.
      NetworkRegister.wrapper.sendToServer(TileEntityRequestMessage.messageFrom(worldObj, xCoord, yCoord, zCoord, 1));
      NetworkRegister.wrapper.sendToServer(TileEntityRequestMessage.messageFrom(worldObj, xCoord, yCoord, zCoord, 2));
    }
  }

  @Override
  public boolean hasWTNetwork() {
    return true;
  }

  @Override
  public void connectToWallsAround() {
    for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
      TileEntity tileEntity = worldObj.getTileEntity(xCoord + direction.offsetX, yCoord + direction.offsetY, zCoord + direction.offsetZ);
      if (tileEntity instanceof TEWallTeleporter) {
        TEWallTeleporter teleporter = (TEWallTeleporter) tileEntity;
        if (!teleporter.isInvalid()) {
          teleporter.connectToWallsAround();
        }
      }
    }
  }

  @Override
  public void receiveIntegerMessage(byte id, int value) {
    switch (id) {
      case 0: // Button packets
        getWTNetwork().handleButton(value);
        break;
      case 1: // Fuel
        getWTNetwork().fuel = value;
        break;
    }
  }

  @Override
  public void receiveRequestMessage(byte id, EntityPlayerMP player) {
    switch (id) {
      case 1: // Destination Data
        getWTNetwork().requestDestinationData(player);
        break;
      case 2: // Fuel
        getWTNetwork().requestFuel(player);
        break;
      default:
        super.receiveRequestMessage(id, player);
    }
  }

  public ItemStack[] items = new ItemStack[2];

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
    return StatCollector.translateToLocal("unwrittenblocks.wallTeleporterBase.name");
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
    if (getWTNetwork().fuel == 0) {
      ItemStack fuelStack = getStackInSlot(1);
      if (fuelStack != null && fuelStack.isItemEqual(new ItemStack(Items.ender_pearl))) {
        fuelStack.stackSize--;
        if (fuelStack.stackSize < 1) setInventorySlotContents(1, null);
        getWTNetwork().fillFuel();
      }
    }
  }

  @Override
  public void writeToNBT(NBTTagCompound compound) {
    network.writeToNBT(compound);
    InventoryHelpers.writeInventoryToNBT(compound, this);
    super.writeToNBT(compound);
  }

  @Override
  public void readFromNBT(NBTTagCompound compound) {
    network.readFromNBT(compound);
    InventoryHelpers.readInventoryFromNBT(compound, this);
    super.readFromNBT(compound);
  }
}
