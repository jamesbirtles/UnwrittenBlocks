package unwrittenfun.minecraft.unwrittenblocks.blocks

import net.minecraft.block.BlockContainer
import unwrittenfun.minecraft.unwrittenblocks.client.gui.creativetabs
import net.minecraft.client.renderer.texture.IconRegister
import unwrittenfun.minecraft.unwrittenblocks._
import net.minecraft.block.material.Material
import net.minecraft.world.World
import net.minecraft.tileentity.TileEntity
import unwrittenfun.minecraft.unwrittenblocks.blocks.tileentities.TileEntityBlockCutter
import net.minecraft.entity.player.EntityPlayer
import cpw.mods.fml.common.network.FMLNetworkHandler

/**
 * Mod: UnwrittenBlocks
 * Author: UnwrittenFun
 * License: Minecraft Mod Public License (Version 1.0.1)
 */
class BlockCutter(id: Int, key: String) extends BlockContainer(id, Material.iron) {
  setUnlocalizedName(key)
  setHardness(2f)
  setCreativeTab(creativetabs.ubTab)

  override def registerIcons(register: IconRegister) {
    blockIcon = register.registerIcon(TEXTURE_LOCATION + ":" + key)
  }

  override def createNewTileEntity(world: World): TileEntity = new TileEntityBlockCutter

  override def onBlockActivated(world: World, x: Int, y: Int, z: Int, player: EntityPlayer, side: Int, hitX: Float, hitY: Float, hitZ: Float): Boolean = {
    FMLNetworkHandler.openGui(player, UnwrittenBlocks, 1, world, x, y, z)
    true
  }
}
