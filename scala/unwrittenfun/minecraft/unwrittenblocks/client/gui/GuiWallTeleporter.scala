package unwrittenfun.minecraft.unwrittenblocks.client.gui

import net.minecraft.client.gui.inventory.GuiContainer
import net.minecraft.util.ResourceLocation
import unwrittenfun.minecraft.unwrittenblocks._
import net.minecraft.entity.player.InventoryPlayer
import unwrittenfun.minecraft.unwrittenblocks.blocks.multiblocks.MultiblockWallTeleporter
import unwrittenfun.minecraft.unwrittenblocks.gui.containers.ContainerWallTeleporter
import org.lwjgl.opengl.GL11
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiButton
import unwrittenfun.minecraft.unwrittenblocks.handlers.PacketHandler
import java.util
import net.minecraft.client.settings.{KeyBinding, GameSettings}

/**
 * Mod: UnwrittenBlocks
 * Author: UnwrittenFun
 * License: Minecraft Mod Public License (Version 1.0.1)
 */
object GuiWallTeleporter {
  var texture: ResourceLocation = new ResourceLocation(TEXTURE_LOCATION, "textures/gui/wall_teleporter_gui.png")

  var helpList: util.ArrayList[Array[Any]] = new util.ArrayList[Array[Any]]()

  //  0     1     2     3     4           5             6         7
  // Text, PosX, PosY, left, Bound Left, Bound Right, Bound Top, Bound Bottom
  helpList.add(Array("Place linked GPS Chip here", 142, 13, true, 151, 168, 7, 24))
  helpList.add(Array("Take GPS Chip", 142, 58, true, 151, 168, 52, 69))
  helpList.add(Array("Clear Desination", 21, 10, false, 7, 18, 8, 18))
  helpList.add(Array("(Un)Lock mask", 21, 24, false, 7, 18, 22, 33))
  helpList.add(Array("(Un)Lock rotation", 21, 38, false, 7, 18, 36, 47))
}

class GuiWallTeleporter(invPlayer: InventoryPlayer, multiblock: MultiblockWallTeleporter) extends GuiContainer(new ContainerWallTeleporter(invPlayer, multiblock)) {
  xSize = 176
  ySize = 157

  override protected def drawGuiContainerBackgroundLayer(f: Float, x: Int, y: Int) {
    GL11.glColor4f(1, 1, 1, 1)
    Minecraft.getMinecraft.renderEngine.bindTexture(GuiWallTeleporter.texture)
    drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize)
  }

  override protected def drawGuiContainerForegroundLayer(x: Int, y: Int) {
    fontRenderer.drawString("Wall Teleporter", 8, 52, 0x404040)
    fontRenderer.drawString("Sneak for help", 8, 62, 0x877512)
    if (multiblock.hasDestination) {
      val infoDy: Int = 16
      val infoSpacing: Int = 10
      val worldString: String = multiblock.destinationWorldName
      val coordString: String = "(" + multiblock.destinationX.toInt + ", " + multiblock.destinationY.toInt + ", " + multiblock.destinationZ.toInt + ")"
      val worldWidth: Int = fontRenderer getStringWidth worldString
      val coordWidth: Int = fontRenderer getStringWidth coordString
      fontRenderer.drawString(worldString, 85 - (worldWidth / 2), infoDy, 0x404040)
      fontRenderer.drawString(coordString, 85 - (coordWidth / 2), infoDy + infoSpacing, 0x404040)
    }

    GL11.glPushMatrix()
      val tX = 116
      val tY = 44
      GL11.glTranslatef(tX / 4, tY / 4, 0)
      GL11.glScalef(0.75F, 0.75F, 1)
      fontRenderer.drawString("Fuel", tX, tY, 0xFFFFFF)
    GL11.glPopMatrix()

    GL11.glPushMatrix()
      val pX = 92
      val pY = 54
      GL11.glTranslatef(pX / 4, pY / 4, 0)
      GL11.glScalef(0.75F, 0.75F, 1)
      fontRenderer.drawString("1 pearl", pX, pY, 0xFFFFFF)
    GL11.glPopMatrix()

    GL11.glPushMatrix()
      val dX = 92
      val dY = 62
      GL11.glTranslatef(dX / 4, dY / 4, 0)
      GL11.glScalef(0.75F, 0.75F, 1)
      fontRenderer.drawString("16 trips", dX, dY, 0xFFFFFF)
    GL11.glPopMatrix()

    Minecraft.getMinecraft.renderEngine.bindTexture(GuiWallTeleporter.texture)
    drawTexturedModalRect(125, 53, xSize, 0, 2, 16)

    if (GameSettings isKeyDown Minecraft.getMinecraft.gameSettings.keyBindSneak) {

      val mX: Int = x - guiLeft
      val mY: Int = y - guiTop

      for (i <- 0 to (GuiWallTeleporter.helpList.size() - 1)) {
        val help: Array[Any] = GuiWallTeleporter.helpList.get(i);
        if (mX >= help(4).asInstanceOf[Int] && mX <= help(5).asInstanceOf[Int] && mY >= help(6).asInstanceOf[Int] && mY <= help(7).asInstanceOf[Int]) {
          drawHelpBox(help(0).asInstanceOf[String], help(1).asInstanceOf[Int], help(2).asInstanceOf[Int], left = help(3).asInstanceOf[Boolean])
        }
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

  override def initGui() {
    super.initGui()
    initButtons()
  }

  private var oldLocked: Boolean = false
  private var oldRot: Boolean = false

  private def initButtons() {
    val buttonBuffer: util.ArrayList[GuiButton] = buttonList.asInstanceOf[util.ArrayList[GuiButton]]
    buttonBuffer.clear()
    buttonBuffer.add(new GuiIconButton(0, guiLeft + 7, guiTop + 7, 0, 0))

    oldLocked = multiblock.locked
    if (oldLocked) buttonBuffer.add(new GuiIconButton(1, guiLeft + 7, guiTop + 21, 16, 0))
    else buttonBuffer.add(new GuiIconButton(2, guiLeft + 7, guiTop + 21, 32, 0))

    oldRot = multiblock.useRotation
    if (oldRot) buttonBuffer.add(new GuiIconButton(3, guiLeft + 7, guiTop + 35, 48, 0))
    else buttonBuffer.add(new GuiIconButton(4, guiLeft + 7, guiTop + 35, 64, 0))
  }

  override def updateScreen() {
    if (oldLocked != multiblock.locked) {
      initButtons()
    }
    if (oldRot != multiblock.useRotation) {
      initButtons()
    }
    super.updateScreen()
  }

  override protected def actionPerformed(button: GuiButton) {
    PacketHandler.sendButtonPacket(0, button.id.toByte)
  }
}
