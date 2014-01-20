package unwrittenfun.minecraft.unwrittenblocks.client.gui

import net.minecraft.client.gui.inventory.GuiContainer
import net.minecraft.entity.player.InventoryPlayer
import unwrittenfun.minecraft.unwrittenblocks.blocks.tileentities.TileEntityBlockCutter
import unwrittenfun.minecraft.unwrittenblocks.gui.containers.ContainerBlockCutter
import net.minecraft.util.ResourceLocation
import unwrittenfun.minecraft.unwrittenblocks._
import net.minecraft.client.Minecraft
import org.lwjgl.opengl.GL11

/**
 * Mod: UnwrittenBlocks
 * Author: UnwrittenFun
 * License: Minecraft Mod Public License (Version 1.0.1)
 */
object GuiBlockCutter {
  var texture: ResourceLocation = new ResourceLocation(TEXTURE_LOCATION, "textures/gui/block_cutter_gui.png")
}

class GuiBlockCutter(invPlayer: InventoryPlayer, cutter: TileEntityBlockCutter) extends GuiContainer(new ContainerBlockCutter(invPlayer, cutter)) {
  xSize = 175
  ySize = 165

  protected def drawGuiContainerBackgroundLayer(f: Float, i: Int, j: Int) {
    GL11.glColor4f(1, 1, 1, 1)
    Minecraft.getMinecraft.renderEngine.bindTexture(GuiBlockCutter.texture)
    drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize)
  }

  protected override def drawGuiContainerForegroundLayer(par1: Int, par2: Int) {
    val titleWidth: Int = fontRenderer.getStringWidth(cutter.getInvName)
    fontRenderer.drawString(cutter.getInvName, 85 - (titleWidth / 2), 10, 0x404040)
  }
}
