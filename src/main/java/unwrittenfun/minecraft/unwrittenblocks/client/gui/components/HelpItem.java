package unwrittenfun.minecraft.unwrittenblocks.client.gui.components;

import net.minecraft.util.StatCollector;
import scala.actors.threadpool.Arrays;
import unwrittenfun.minecraft.unwrittenblocks.client.gui.GuiUnwrittenBlocks;

/**
 * Project: UnwrittenBlocks
 * Author: UnwrittenFun
 * Created: 06/11/2014.
 */
public class HelpItem {
  public String[] text;
  public int      x, y, width, height;

  public HelpItem(String text, int x, int y, int width, int height) {
    this.text = StatCollector.translateToLocal(text).split("\\\\n");
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
  }

  public void renderHelp(GuiUnwrittenBlocks screen, int mX, int mY) {
    if (mX >= x && mX <= x + width && mY >= y && mY <= y + height) {
      screen.drawTooltip(Arrays.asList(text), mX, mY);
    }
  }
}
