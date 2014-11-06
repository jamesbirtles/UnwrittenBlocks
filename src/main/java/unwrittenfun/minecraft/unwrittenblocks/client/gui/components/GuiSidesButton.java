package unwrittenfun.minecraft.unwrittenblocks.client.gui.components;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import unwrittenfun.minecraft.unwrittenblocks.client.gui.GuiDarkInfuser;
import unwrittenfun.minecraft.unwrittenblocks.common.tileEntities.TEConfigurableIO;

/**
 * Project: UnwrittenBlocks
 * Author: UnwrittenFun
 * Created: 06/11/2014.
 */
public class GuiSidesButton extends GuiButton {
  public int io, side;

  public GuiSidesButton(int id, int side, int io, int x, int y) {
    super(id, x, y, 16, 16, "");

    this.io = io;
    this.side = side;
  }

  @Override
  public void drawButton(Minecraft minecraft, int mX, int mY) {
    minecraft.renderEngine.bindTexture(GuiDarkInfuser.texture); //TODO: Generify texture

    if ((io & 1) == 1) drawTexturedModalRect(xPosition, yPosition, 176, 25, 15, 15);
    if ((io & 2) == 2) drawTexturedModalRect(xPosition + 1, yPosition + 1, 176, 40, 15, 15);
  }

  public void buttonClicked(TEConfigurableIO configurableIO) {
    io += 1;
    if (io > 3) io = 0;

    configurableIO.setSideIO(side, io);
  }
}
