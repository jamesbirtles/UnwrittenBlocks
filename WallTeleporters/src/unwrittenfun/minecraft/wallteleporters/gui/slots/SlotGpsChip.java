package unwrittenfun.minecraft.wallteleporters.gui.slots;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import unwrittenfun.minecraft.wallteleporters.items.WTItems;

/**
 * Mod: Wall Teleporters
 * Author: UnwrittenFun
 * License: Minecraft Mod Public License (Version 1.0.1)
 */
public class SlotGpsChip extends Slot {
    public SlotGpsChip(IInventory inventory, int id, int x, int y) {
        super(inventory, id, x, y);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return stack.itemID == WTItems.gpsChip.itemID && stack.getItemDamage() == 1;
    }

    @Override
    public int getSlotStackLimit() {
        return 1;
    }
}
