package unwrittenfun.minecraft.wallteleporters.gui.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import unwrittenfun.minecraft.wallteleporters.blocks.multiblocks.MultiblockWallTeleporter;
import unwrittenfun.minecraft.wallteleporters.gui.slots.SlotGpsChip;
import unwrittenfun.minecraft.wallteleporters.items.WTItems;

/**
 * Mod: Wall Teleporters
 * Author: UnwrittenFun
 * License: Minecraft Mod Public License (Version 1.0.1)
 */
public class ContainerWallTeleporter extends Container {
    public MultiblockWallTeleporter multiblock;

    public ContainerWallTeleporter(InventoryPlayer invPlayer, MultiblockWallTeleporter multiblock) {
        this.multiblock = multiblock;
        multiblock.container = this;

        for (int x = 0; x < 9; x++) {
            addSlotToContainer(new Slot(invPlayer, x, 8 + 18 * x, 133));
        }

        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 9; x++) {
                addSlotToContainer(new Slot(invPlayer, x + y * 9 + 9, 8 + 18 * x, 75 + 18 * y));
            }
        }

        addSlotToContainer(new SlotGpsChip(multiblock, 0, 152, 8));
        addSlotToContainer(new SlotGpsChip(multiblock, 1, 152, 53));
    }

    @Override
    public boolean canInteractWith(EntityPlayer entityplayer) {
        return true;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int i) {
        Slot slot = getSlot(i);

        if (slot != null && slot.getHasStack()) {
            ItemStack stack = slot.getStack();
            ItemStack result = stack.copy();

            if (i >= 36) {
                if (!mergeItemStack(stack, 0, 36, false)) {
                    return null;
                }
            } else if (stack.itemID != WTItems.gpsChip.itemID || !mergeItemStack(stack, 36, 37, false)) {
                return null;
            }

            if (stack.stackSize == 0) {
                slot.putStack(null);
            } else {
                slot.onSlotChanged();
            }

            slot.onPickupFromSlot(player, stack);

            return result;
        }

        return null;
    }

    @Override
    public void onContainerClosed(EntityPlayer player) {
        super.onContainerClosed(player);

        if (crafters.size() == 0) {
            multiblock.container = null;
        }
    }

    public void sendSlotContentsToCrafters(int i, ItemStack stack) {
        for (int j = 0; j < crafters.size(); j++) {
            ((ICrafting) crafters.get(j)).sendSlotContents(this, i, stack);
        }
    }
}
