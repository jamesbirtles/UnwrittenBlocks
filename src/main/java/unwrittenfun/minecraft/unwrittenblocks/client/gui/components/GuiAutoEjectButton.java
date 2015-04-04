package unwrittenfun.minecraft.unwrittenblocks.client.gui.components;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import unwrittenfun.minecraft.unwrittenblocks.client.gui.GuiRefulgentFabricator;
import unwrittenfun.minecraft.unwrittenblocks.common.tileEntities.TEConfigurableIO;

/**
 * Author: James Birtles
 */
public class GuiAutoEjectButton extends GuiButton {
  public int io, side;

  public GuiAutoEjectButton(int id, int side, int io, int x, int y) {
    super(id, x, y, 16, 16, "");

    this.io = io;
    this.side = side;
  }

  @Override
  public void drawButton(Minecraft minecraft, int mX, int mY) {
    minecraft.renderEngine.bindTexture(GuiRefulgentFabricator.texture);

    if ((io & 2) == 2) drawTexturedModalRect(xPosition, yPosition, 198, 0, 16, 16);
  }

  public void buttonClicked(TEConfigurableIO configurableIO) {
    if (io == 0) {
      io = 2;
    } else {
      io = 0;
    }

    configurableIO.setSideIO(side, io);
  }
}
