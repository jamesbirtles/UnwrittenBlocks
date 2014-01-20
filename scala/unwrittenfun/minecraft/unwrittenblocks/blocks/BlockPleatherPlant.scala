package unwrittenfun.minecraft.unwrittenblocks.blocks

import net.minecraft.block.{Block, BlockCrops}
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.client.renderer.texture.IconRegister
import net.minecraft.util.Icon
import unwrittenfun.minecraft.unwrittenblocks._
import unwrittenfun.minecraft.unwrittenblocks.items.UBItems
import net.minecraft.item.Item

/**
 * Mod: UnwrittenBlocks
 * Author: UnwrittenFun
 * License: Minecraft Mod Public License (Version 1.0.1)
 */
class BlockPleatherPlant(id: Int, key: String) extends BlockCrops(id) {
  setUnlocalizedName(key)

  val icons = new Array[Icon](8)

  override def registerIcons(register: IconRegister) {
    for (i <- 0 until icons.length) icons(i) = register.registerIcon(TEXTURE_LOCATION + ":" + key + "_" + i)
  }

  override def getIcon(side: Int, meta: Int): Icon = icons(meta)

  protected override def canThisPlantGrowOnThisBlockID(growId: Int) = (growId == Block.dirt.blockID) || (growId == Block.grass.blockID)

  protected override def getSeedItem: Int = UBItems.pleatherBulb.itemID

  protected override def getCropItem: Int = UBItems.pleatherStrips.itemID
}
