package unwrittenfun.minecraft.unwrittenblocks.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;

import java.util.List;

/**
 * Project: UnwrittenBlocks
 * Author: UnwrittenFun
 * Created: 06/11/2014.
 */
public abstract class GuiUnwrittenBlocks extends GuiContainer {
  public GuiUnwrittenBlocks(Container container) {
    super(container);
  }

  public void drawTooltip(List lines, int x, int y) {
    drawHoveringText(lines, x, y, fontRendererObj);
  }
}
