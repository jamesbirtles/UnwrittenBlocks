package unwrittenfun.minecraft.unwrittenblocks.client.gui

import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiButton

/**
 * Project: UnwrittenBlocks
 * Author: UnwrittenFun 
 * Created: 14/08/2014.
 */
class GuiSidesButton(id: Int, _side: Int, _io: Int, x: Int, y: Int) extends GuiButton(id, x, y, 16, 16, "") {
  var io: Int = _io
  var side: Int = _side

  override def drawButton(minecraft: Minecraft, mX: Int, mY: Int) {
//    super.drawButton(minecraft, mX, mY)

    Minecraft.getMinecraft.renderEngine.bindTexture(GuiTextures.darkInfuser)

    if ((io & 1) == 1) drawTexturedModalRect(x, y, 176, 25, 15, 15)
    if ((io & 2) == 2) drawTexturedModalRect(x + 1, y + 1, 176, 40, 15, 15)
  }

  def buttonClicked() {
    io += 1
    if (io > 3) io = 0
  }
}
