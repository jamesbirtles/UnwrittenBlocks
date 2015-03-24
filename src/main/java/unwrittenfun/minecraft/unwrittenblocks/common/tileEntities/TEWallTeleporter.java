package unwrittenfun.minecraft.unwrittenblocks.common.tileEntities;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import unwrittenfun.minecraft.unwrittenblocks.common.blocks.BlockRegister;
import unwrittenfun.minecraft.unwrittenblocks.common.items.ItemRegister;
import unwrittenfun.minecraft.unwrittenblocks.common.multiblock.WallTeleporterNetwork;
import unwrittenfun.minecraft.unwrittenblocks.common.network.NetworkRegister;
import unwrittenfun.minecraft.unwrittenblocks.common.network.messages.TileEntityRequestMessage;
import unwrittenfun.minecraft.unwrittenblocks.common.network.messages.TileEntityStackMessage;
import unwrittenfun.minecraft.unwrittenblocks.common.network.receivers.ITileEntityRequestMessageReceiver;
import unwrittenfun.minecraft.unwrittenblocks.common.network.receivers.ITileEntityStackMessageReceiver;

/**
 * Author: James Birtles
 */
public abstract class TEWallTeleporter extends TileEntity implements IWallTeleporterBlock, ITileEntityStackMessageReceiver, ITileEntityRequestMessageReceiver {
  public WallTeleporterNetwork network;
  public boolean ignoreWT = false;
  public boolean loaded = false;
  public ItemStack mask = new ItemStack(BlockRegister.wallTeleporterWall);

  @Override
  public void updateEntity() {
    if (hasWorldObj() && !loaded) {
      loaded = true;
      onLoaded();
    }
  }

  protected void onLoaded() {
    connectToWallsAround();
    if (worldObj.isRemote) {
      NetworkRegister.wrapper.sendToServer(TileEntityRequestMessage.messageFrom(worldObj, xCoord, yCoord, zCoord, 0));
    }
  }

  @Override
  public boolean hasWTNetwork() {
    return network != null;
  }

  @Override
  public WallTeleporterNetwork getWTNetwork() {
    return network;
  }

  @Override
  public void setWTNetwork(WallTeleporterNetwork wtNetwork) {
    network = wtNetwork;
  }

  @Override
  public boolean shouldIgnoreWT() {
    return ignoreWT;
  }

  @Override
  public void setIgnoreWT(boolean ignore) {
    ignoreWT = ignore;
  }

  public void setMask(ItemStack mask) {
    this.mask = mask;

    if (!worldObj.isRemote) {
      NetworkRegister.wrapper.sendToDimension(getMaskStackMessage(), worldObj.provider.dimensionId);
    }
  }

  private TileEntityStackMessage getMaskStackMessage() {
    return TileEntityStackMessage.messageFrom(worldObj, xCoord, yCoord, zCoord, 0, mask);
  }

  @Override
  public void receiveStackMessage(byte id, ItemStack stack) {
    switch (id) {
      case 0:
        setMask(stack);
        if (worldObj.isRemote) worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        break;
    }
  }

  @Override
  public void writeToNBT(NBTTagCompound compound) {
    NBTTagCompound maskCompound = new NBTTagCompound();
    mask.writeToNBT(maskCompound);
    compound.setTag("Mask", maskCompound);

    super.writeToNBT(compound);
  }

  @Override
  public void readFromNBT(NBTTagCompound compound) {
    if (compound.hasKey("Mask")) mask = ItemStack.loadItemStackFromNBT(compound.getCompoundTag("Mask"));
    super.readFromNBT(compound);
  }

  @Override
  public void receiveRequestMessage(byte id, EntityPlayerMP player) {
    switch (id) {
      case 0:
        NetworkRegister.wrapper.sendTo(getMaskStackMessage(), player);
        break;
    }
  }
}
