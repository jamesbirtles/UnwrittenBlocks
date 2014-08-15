package unwrittenfun.minecraft.unwrittenblocks.handlers

import cpw.mods.fml.common.network.{NetworkRegistry, IGuiHandler}
import unwrittenfun.minecraft.unwrittenblocks.UnwrittenBlocks
import net.minecraft.tileentity.TileEntity
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.world.World
import unwrittenfun.minecraft.unwrittenblocks.blocks.tileentities.{TileEntityDarkInfuser, TileEntityWallTeleporter}
import unwrittenfun.minecraft.unwrittenblocks.gui.containers.{ContainerDarkInfuser, ContainerWallTeleporter}
import unwrittenfun.minecraft.unwrittenblocks.client.gui.{GuiDarkInfuser, GuiWallTeleporter}

/**
 * Mod: UnwrittenBlocks
 * Author: UnwrittenFun
 * License: Minecraft Mod Public License (Version 1.0.1)
 */
class GuiHandler extends IGuiHandler {
  NetworkRegistry.instance.registerGuiHandler(UnwrittenBlocks, this)

  @Override def getServerGuiElement(id: Int, player: EntityPlayer, world: World, x: Int, y: Int, z: Int): Object = {
    val tileEntity: TileEntity = world.getBlockTileEntity(x, y, z)
    id match {
      case 0 =>
        tileEntity match {
          case teleporter: TileEntityWallTeleporter =>
            return new ContainerWallTeleporter(player.inventory, teleporter.multiblock)
          case _ =>
        }
      case 1 =>
        tileEntity match {
          case infuser: TileEntityDarkInfuser =>
            return new ContainerDarkInfuser(player.inventory, infuser)
          case _ =>
        }
      case _ =>
    }
    null
  }

  @Override def getClientGuiElement(id: Int, player: EntityPlayer, world: World, x: Int, y: Int, z: Int): Object = {
    val tileEntity: TileEntity = world.getBlockTileEntity(x, y, z)
    id match {
      case 0 =>
        tileEntity match {
          case teleporter: TileEntityWallTeleporter =>
            return new GuiWallTeleporter(player.inventory, teleporter.multiblock)
          case _ =>
        }
      case 1 =>
        tileEntity match {
          case infuser: TileEntityDarkInfuser =>
            return new GuiDarkInfuser(player.inventory, infuser)
          case _ =>
        }
      case _ =>
    }
    null
  }
}
