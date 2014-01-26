package unwrittenfun.minecraft.unwrittenblocks.client.gui

import net.minecraft.entity.player.InventoryPlayer
import unwrittenfun.minecraft.unwrittenblocks.blocks.tileentities.TileEntityDarkInfuser
import net.minecraft.client.gui.inventory.GuiContainer
import unwrittenfun.minecraft.unwrittenblocks.gui.containers.ContainerDarkInfuser
import org.lwjgl.opengl.GL11
import net.minecraft.client.Minecraft

/**
 * Mod: UnwrittenBlocks
 * Author: UnwrittenFun
 * License: Minecraft Mod Public License (Version 1.0.1)
 */
class GuiDarkInfuser(invPlayer: InventoryPlayer, infuser: TileEntityDarkInfuser) extends GuiContainer(new ContainerDarkInfuser(invPlayer, infuser)) {
  xSize = 176
  ySize = 166

  override protected def drawGuiContainerBackgroundLayer(f: Float, x: Int, y: Int) {
    GL11.glColor4f(1, 1, 1, 1)
    Minecraft.getMinecraft.renderEngine.bindTexture(GuiTextures.darkInfuser)
    drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize)
  }

  override def drawGuiContainerForegroundLayer(par1: Int, par2: Int) {
    fontRenderer.drawString("Dark Infuser", 8, 62, 0xFFFFFF)
    fontRenderer.drawString("Sneak for help", 8, 72, 0x27376b)

    GL11.glColor4f(1, 1, 1, 1)
    Minecraft.getMinecraft.renderEngine.bindTexture(GuiTextures.darkInfuser)
    drawTexturedModalRect(61, 18, 0, ySize, infuser.infuserProgress, 53)
  }
}
