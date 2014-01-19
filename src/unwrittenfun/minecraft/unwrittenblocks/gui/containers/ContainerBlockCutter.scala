package unwrittenfun.minecraft.unwrittenblocks.gui.containers

import net.minecraft.entity.player.{EntityPlayer, InventoryPlayer}
import unwrittenfun.minecraft.unwrittenblocks.blocks.tileentities.TileEntityBlockCutter
import net.minecraft.inventory.{Slot, Container}

/**
 * Mod: UnwrittenBlocks
 * Author: UnwrittenFun
 * License: Minecraft Mod Public License (Version 1.0.1)
 */
class ContainerBlockCutter(invPlayer: InventoryPlayer, cutter: TileEntityBlockCutter) extends Container {
  var x: Int = 0
  while (x < 9) {
    addSlotToContainer(new Slot(invPlayer, x, 8 + 18 * x, 142))
    x += 1
  }

  var y: Int = 0
  while (y < 3) {
    var x: Int = 0
    while (x < 9) {
      addSlotToContainer(new Slot(invPlayer, x + y * 9 + 9, 8 + 18 * x, 84 + 18 * y))
      x += 1
    }
    y += 1
  }

  addSlotToContainer(new Slot(cutter, 0, 10, 10))
  addSlotToContainer(new Slot(cutter, 1, 10, 60))

  def canInteractWith(player: EntityPlayer): Boolean = cutter.isUseableByPlayer(player)
}
