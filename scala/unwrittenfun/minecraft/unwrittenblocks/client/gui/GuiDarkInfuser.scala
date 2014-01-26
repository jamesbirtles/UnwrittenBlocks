package unwrittenfun.minecraft.unwrittenblocks.client.gui

import net.minecraft.entity.player.InventoryPlayer
import unwrittenfun.minecraft.unwrittenblocks.blocks.tileentities.TileEntityDarkInfuser
import net.minecraft.client.gui.inventory.GuiContainer
import unwrittenfun.minecraft.unwrittenblocks.gui.containers.ContainerDarkInfuser
import org.lwjgl.opengl.GL11
import net.minecraft.client.Minecraft
import java.util
import net.minecraft.client.settings.GameSettings

/**
 * Mod: UnwrittenBlocks
 * Author: UnwrittenFun
 * License: Minecraft Mod Public License (Version 1.0.1)
 */
object GuiDarkInfuser {
  var helpList: util.ArrayList[Array[Any]] = new util.ArrayList[Array[Any]]()

  //  0     1     2     3     4           5             6         7
  // Text, PosX, PosY, left, Bound Left, Bound Right, Bound Top, Bound Bottom
  helpList.add(Array("Item to be infused", 62, 34, false, 44, 60, 36, 52))
  helpList.add(Array("Infused item", 111, 34, true, 113, 135, 33, 55))
}

class GuiDarkInfuser(invPlayer: InventoryPlayer, infuser: TileEntityDarkInfuser) extends GuiContainer(new ContainerDarkInfuser(invPlayer, infuser)) {
  xSize = 176
  ySize = 166

  override protected def drawGuiContainerBackgroundLayer(f: Float, x: Int, y: Int) {
    GL11.glColor4f(1, 1, 1, 1)
    Minecraft.getMinecraft.renderEngine.bindTexture(GuiTextures.darkInfuser)
    drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize)
  }

  override def drawGuiContainerForegroundLayer(x: Int, y: Int) {
    fontRenderer.drawString("Dark Infuser", 8, 62, 0xFFFFFF)
    fontRenderer.drawString("Sneak for help", 8, 72, 0x27376b)

    GL11.glColor4f(1, 1, 1, 1)
    Minecraft.getMinecraft.renderEngine.bindTexture(GuiTextures.darkInfuser)
    drawTexturedModalRect(61, 18, 0, ySize, infuser.infuserProgress, 53)

    if (GameSettings isKeyDown Minecraft.getMinecraft.gameSettings.keyBindSneak) {
      val mX: Int = x - guiLeft
      val mY: Int = y - guiTop

      for (i <- 0 to (GuiDarkInfuser.helpList.size() - 1)) {
        val help: Array[Any] = GuiDarkInfuser.helpList.get(i)
        if (mX >= help(4).asInstanceOf[Int] && mX <= help(5).asInstanceOf[Int] && mY >= help(6).asInstanceOf[Int] && mY <= help(7).asInstanceOf[Int]) {
          drawHelpBox(help(0).asInstanceOf[String], help(1).asInstanceOf[Int], help(2).asInstanceOf[Int], left = help(3).asInstanceOf[Boolean])
        }
      }
    }

    def drawHelpBox(msg: String, x: Int, y: Int, left: Boolean) {
      val width: Int = fontRenderer getStringWidth msg
      val leftX: Int = if (left) x - width else x

      GL11.glColor4f(1, 1, 1, 1)
      Minecraft.getMinecraft.renderEngine.bindTexture(GuiTextures.gui)
      drawTexturedModalRect(leftX, y - 4, 0, 12, 4, 14)
      drawTexturedModalRect(leftX + 4, y - 4, 4, 12, width, 14)
      drawTexturedModalRect(leftX + 4 + width, y - 4, 252, 12, 4, 14)

      fontRenderer.drawString(msg, leftX + 4, y, 0xFFFFFF)
    }
  }
}
