package unwrittenfun.minecraft.unwrittenblocks.client.gui

import java.util

import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiButton
import net.minecraft.client.gui.inventory.GuiContainer
import net.minecraft.client.renderer.RenderHelper
import net.minecraft.client.settings.GameSettings
import net.minecraft.entity.player.InventoryPlayer
import net.minecraftforge.common.ForgeDirection
import org.lwjgl.opengl.{GL12, GL11}
import unwrittenfun.minecraft.unwrittenblocks.blocks.tileentities.TileEntityDarkInfuser
import unwrittenfun.minecraft.unwrittenblocks.gui.containers.ContainerDarkInfuser
import unwrittenfun.minecraft.unwrittenblocks.handlers.PacketHandler

import scala.collection.JavaConverters._

/**
 * Mod: UnwrittenBlocks
 * Author: UnwrittenFun
 * License: Minecraft Mod Public License (Version 1.0.1)
 */
object GuiDarkInfuser {
  var helpList: util.ArrayList[Array[Any]] = new util.ArrayList[Array[Any]]()

  // Text, PosX, PosY, Bound Left, Bound Right, Bound Top, Bound Bottom
  helpList.add(Array(List("Input"), 44 + 10, 36 + 16, 44, 44 + 16, 36, 36 + 16))
  helpList.add(Array(List("Output"), 116 + 10, 36 + 16, 116, 116 + 16, 36, 36 + 16))
  helpList.add(Array(List("Upgrades"), 152 + 10, 8 + 16, 152, 152 + 16, 8, 8 + 16))

  // Sides
//  helpList.add(Array(List("Show Inputs", "and Outputs"), 61 + 10, 7 + 16, 61, 61 + 16, 7, 7 + 16))
  helpList.add(Array(List("Bottom"), 43 + 10, 7 + 16, 43, 43 + 16, 7, 7 + 16))
  helpList.add(Array(List("Top"), 25 + 10, 7 + 16, 25, 25 + 16, 7, 7 + 16))
  helpList.add(Array(List("North"), 7 + 10, 7 + 16, 7, 7 + 16, 7, 7 + 16))
  helpList.add(Array(List("East"), 7 + 10, 25 + 16, 7, 7 + 16, 25, 25 + 16))
  helpList.add(Array(List("South"), 7 + 10, 43 + 16, 7, 7 + 16, 43, 43 + 16))
  helpList.add(Array(List("West"), 7 + 10, 61 + 16, 7, 7 + 16, 61, 61 + 16))
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
    fontRenderer.drawString("Dark Infuser", 28, 62, 0x373737)

    if (GameSettings.isKeyDown(Minecraft.getMinecraft.gameSettings.keyBindSneak)) {
      fontRenderer.drawString("Mouse over a slot", 28, 72, 0x6a6a6a)
    } else {
      fontRenderer.drawString("Sneak for help", 28, 72, 0x6a6a6a)
    }

    GL11.glColor4f(1, 1, 1, 1)
    Minecraft.getMinecraft.renderEngine.bindTexture(GuiTextures.darkInfuser)

    // if (!infuser.show) {
//      drawTexturedModalRect(61, 7, xSize + 15, 25, 16, 16)
    // }

    // Temporarily remove show in/outs button
    drawTexturedModalRect(61, 7, 77, 7, 16, 16)

    drawTexturedModalRect(62, 31, xSize, 0, infuser.infuserProgress, 25)

    if (GameSettings isKeyDown Minecraft.getMinecraft.gameSettings.keyBindSneak) {
      val mX: Int = x - guiLeft
      val mY: Int = y - guiTop

      for (i <- 0 to (GuiDarkInfuser.helpList.size() - 1)) {
        val help: Array[Any] = GuiDarkInfuser.helpList.get(i)
        if (mX >= help(3).asInstanceOf[Int] && mX <= help(4).asInstanceOf[Int] && mY >= help(5).asInstanceOf[Int] && mY <= help(6).asInstanceOf[Int]) {
          drawHoveringText(help(0).asInstanceOf[List[String]].asJava, help(1).asInstanceOf[Int], help(2).asInstanceOf[Int], fontRenderer)
        }
      }

      RenderHelper.enableGUIStandardItemLighting()
    }
  }

  override def initGui() {
    super.initGui()

    initButtons()
  }

  def initButtons() {
    val buttonBuffer: util.ArrayList[GuiButton] = buttonList.asInstanceOf[util.ArrayList[GuiButton]]
    buttonBuffer.clear()

    buttonBuffer.add(new GuiSidesButton(0, ForgeDirection.DOWN.ordinal(), infuser.ioSides(ForgeDirection.DOWN.ordinal()), guiLeft + 43, guiTop + 7))
    buttonBuffer.add(new GuiSidesButton(1, ForgeDirection.UP.ordinal(), infuser.ioSides(ForgeDirection.UP.ordinal()), guiLeft + 25, guiTop + 7))
    buttonBuffer.add(new GuiSidesButton(2, ForgeDirection.NORTH.ordinal(), infuser.ioSides(ForgeDirection.NORTH.ordinal()), guiLeft + 7, guiTop + 7))
    buttonBuffer.add(new GuiSidesButton(3, ForgeDirection.EAST.ordinal(), infuser.ioSides(ForgeDirection.EAST.ordinal()), guiLeft + 7, guiTop + 25))
    buttonBuffer.add(new GuiSidesButton(4, ForgeDirection.SOUTH.ordinal(), infuser.ioSides(ForgeDirection.SOUTH.ordinal()), guiLeft + 7, guiTop + 43))
    buttonBuffer.add(new GuiSidesButton(5, ForgeDirection.WEST.ordinal(), infuser.ioSides(ForgeDirection.WEST.ordinal()), guiLeft + 7, guiTop + 61))
  }

  override def actionPerformed(button: GuiButton) {
    button match {
      case sidesButton: GuiSidesButton =>
        sidesButton.buttonClicked()
        infuser.setSideIO(sidesButton.side, sidesButton.io)
      case _ => PacketHandler.sendButtonPacket (button.id)
    }
  }

  override def updateScreen() {
    super.updateScreen()

    var updateButtons = false
    for (side <- 0 until infuser.ioSides.length) {
      updateButtons = infuser.ioSides(side) != buttonList.get(side).asInstanceOf[GuiSidesButton].io
    }
    if (updateButtons) initButtons()
  }
}
