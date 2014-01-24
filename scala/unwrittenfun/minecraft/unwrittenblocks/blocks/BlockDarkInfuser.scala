package unwrittenfun.minecraft.unwrittenblocks.blocks

import net.minecraft.block.BlockContainer
import net.minecraft.block.material.Material
import net.minecraft.world.World
import net.minecraft.tileentity.TileEntity
import net.minecraft.client.renderer.texture.IconRegister
import unwrittenfun.minecraft.unwrittenblocks._
import unwrittenfun.minecraft.unwrittenblocks.blocks.tileentities.TileEntityDarkInfuser

/**
 * Mod: UnwrittenBlocks
 * Author: UnwrittenFun
 * License: Minecraft Mod Public License (Version 1.0.1)
 */
class BlockDarkInfuser(id: Int, key: String) extends BlockContainer(id, Material.iron) {
  def createNewTileEntity(world: World): TileEntity = new TileEntityDarkInfuser

  override def registerIcons(register: IconRegister) {
    blockIcon = register.registerIcon(TEXTURE_LOCATION + ":" + key)
  }
}
