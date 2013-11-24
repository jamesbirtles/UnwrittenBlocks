package unwrittenfun.minecraft.unwrittenblocks.client.gui

import net.minecraft.client.gui.GuiButton
import net.minecraft.util.ResourceLocation
import unwrittenfun.minecraft.unwrittenblocks.TEXTURE_LOCATION
import net.minecraft.client.Minecraft
import org.lwjgl.opengl.GL11;

/**
 * Mod: UnwrittenBlocks
 * Author: UnwrittenFun
 * License: Minecraft Mod Public License (Version 1.0.1)
 */
object GuiIconButton {
  var texture: ResourceLocation = new ResourceLocation(TEXTURE_LOCATION, "textures/gui/gui.png")
  var iconSheet: ResourceLocation = new ResourceLocation(TEXTURE_LOCATION, "textures/gui/icons.png")
}

class GuiIconButton(id: Int, x: Int, y: Int, uX: Int, uY: Int) extends GuiButton(id, x, y, 12, 12, "") {
  @Override override def drawButton(minecraft: Minecraft, mX: Int, mY: Int) {
    if (drawButton) {
      minecraft.renderEngine.bindTexture(GuiIconButton.texture)
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F)
      drawTexturedModalRect(xPosition, yPosition, 0, 0, 12, 12)

      GL11.glPushMatrix()
        GL11.glScalef(0.5F, 0.5F, 1F)
        minecraft.renderEngine.bindTexture(GuiIconButton.iconSheet)
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F)
        drawTexturedModalRect((xPosition + 2) * 2, (yPosition + 2) * 2, uX, uY, 16, 16)
      GL11.glPopMatrix()
    }
  }
}
