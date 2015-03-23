package unwrittenfun.minecraft.unwrittenblocks.common.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import unwrittenfun.minecraft.unwrittenblocks.common.tileEntities.TEWallTeleporterBase;

/**
 * Author: James Birtles
 */
public class ContainerWallTeleporter extends Container {
  public TEWallTeleporterBase teleporter;

  public ContainerWallTeleporter(InventoryPlayer playerInventory, TEWallTeleporterBase teleporterBase) {
    teleporter = teleporterBase;

    for (int x = 0; x < 9; x++) {
      addSlotToContainer(new Slot(playerInventory, x, 8 + 18 * x, 142));
    }

    for (int y = 0; y < 3; y++) {
      for (int x = 0; x < 9; x++) {
        addSlotToContainer(new Slot(playerInventory, x + y * 9 + 9, 8 + 18 * x, 84 + 18 * y));
      }
    }

    addSlotToContainer(new Slot(teleporterBase, 0, 152, 8));
    addSlotToContainer(new Slot(teleporterBase, 1, 8, 62));
  }

  @Override
  public boolean canInteractWith(EntityPlayer player) {
    return true;
  }

  @Override
  public void addCraftingToCrafters(ICrafting crafter) {
    crafter.sendProgressBarUpdate(this, 0, teleporter.getWTNetwork().fuel);
    super.addCraftingToCrafters(crafter);
  }

  @Override
  public void updateProgressBar(int type, int value) {
    switch (type) {
      case 0: // Fuel
        teleporter.getWTNetwork().fuel = value;
        break;
    }
  }
}
