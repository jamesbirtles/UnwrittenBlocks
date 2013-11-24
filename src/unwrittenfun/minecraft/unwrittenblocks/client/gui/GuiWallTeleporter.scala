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

/**
 * Mod: UnwrittenBlocks
 * Author: UnwrittenFun
 * License: Minecraft Mod Public License (Version 1.0.1)
 */
object GuiWallTeleporter {
  var texture: ResourceLocation = new ResourceLocation(TEXTURE_LOCATION, "textures/gui/wall_teleporter_gui.png")
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
    val titleWidth: Int = fontRenderer.getStringWidth(multiblock.getInvName)
    fontRenderer.drawString(multiblock.getInvName, 85 - (titleWidth / 2), 10, 0x404040)
    if (multiblock.hasDestination) {
      val infoDy: Int = 25
      val infoSpacing: Int = 10
      val worldString: String = multiblock.destinationWorldName
      val coordString: String = "(" + multiblock.destinationX.toInt + ", " + multiblock.destinationY.toInt + ", " + multiblock.destinationZ.toInt + ")"
      val worldWidth: Int = fontRenderer getStringWidth worldString
      val coordWidth: Int = fontRenderer getStringWidth coordString
      fontRenderer.drawString(worldString, 85 - (worldWidth / 2), infoDy, 0x404040)
      fontRenderer.drawString(coordString, 85 - (coordWidth / 2), infoDy + infoSpacing, 0x404040)
    }
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
