package unwrittenfun.minecraft.unwrittenblocks.common.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import unwrittenfun.minecraft.unwrittenblocks.common.containers.slots.SlotUpgrade;
import unwrittenfun.minecraft.unwrittenblocks.common.network.NetworkRegister;
import unwrittenfun.minecraft.unwrittenblocks.common.network.messages.TileEntityIntegerMessage;
import unwrittenfun.minecraft.unwrittenblocks.common.tileEntities.TEDarkInfuser;

/**
 * Project: UnwrittenBlocks
 * Author: UnwrittenFun
 * Created: 06/11/2014.
 */
public class ContainerDarkInfuser extends Container {
  public InventoryPlayer playerInventory;
  public TEDarkInfuser darkInfuser;
  private int oldInfusingProgress = 0;

  public ContainerDarkInfuser(InventoryPlayer playerInventory, TEDarkInfuser darkInfuser) {
    this.playerInventory = playerInventory;
    this.darkInfuser = darkInfuser;

    for (int x = 0; x < 9; x++) {
      addSlotToContainer(new Slot(playerInventory, x, 8 + 18 * x, 142));
    }

    for (int y = 0; y < 3; y++) {
      for (int x = 0; x < 9; x++) {
        addSlotToContainer(new Slot(playerInventory, x + y * 9 + 9, 8 + 18 * x, 84 + 18 * y));
      }
    }

    addSlotToContainer(new Slot(darkInfuser, 0, 44, 36));
    addSlotToContainer(new Slot(darkInfuser, 1, 116, 36));
    addSlotToContainer(new SlotUpgrade(darkInfuser, 2, 152, 8));
  }

  @Override
  public void addCraftingToCrafters(ICrafting crafting) {
    super.addCraftingToCrafters(crafting);

    for (int side = 0; side < darkInfuser.ioSides.length; side++) {
      crafting.sendProgressBarUpdate(this, side, darkInfuser.ioSides[side]);
    }

    crafting.sendProgressBarUpdate(this, darkInfuser.ioSides.length, getInfusingProgress());
  }

  @Override
  public void detectAndSendChanges() {
    super.detectAndSendChanges();

    if (getInfusingProgress() != oldInfusingProgress) {
      oldInfusingProgress = getInfusingProgress();
      NetworkRegister.wrapper.sendTo(TileEntityIntegerMessage.messageFrom(darkInfuser.getWorldObj(), darkInfuser.xCoord, darkInfuser.yCoord, darkInfuser.zCoord, 0, oldInfusingProgress), (EntityPlayerMP) playerInventory.player);
    }
  }

  @Override
  public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int p_82846_2_) {
    //TODO: Shift click implementation
    return null;
  }

  @Override
  public void updateProgressBar(int id, int value) {
    if (id < darkInfuser.ioSides.length) {
      darkInfuser.ioSides[id] = value;
    } else if (id == darkInfuser.ioSides.length) {
      darkInfuser.infuserProgress = value;
    }
  }

  @Override
  public boolean canInteractWith(EntityPlayer player) {
    return darkInfuser.isUseableByPlayer(player);
  }

  private int getInfusingProgress() {
    return darkInfuser.infuserTicks > darkInfuser.infuserMaxTicks ? 0 : (int) (50f * (((float) darkInfuser.infuserTicks / darkInfuser.infuserMaxTicks)));
  }
}
