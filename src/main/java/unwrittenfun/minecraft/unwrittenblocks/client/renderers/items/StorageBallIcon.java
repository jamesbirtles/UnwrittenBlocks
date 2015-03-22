package unwrittenfun.minecraft.unwrittenblocks.client.renderers.items;

import net.minecraft.util.IIcon;

/**
 * Author: James Birtles
 */
public class StorageBallIcon implements IIcon {
  @Override
  public int getIconWidth() {
    return 16;
  }

  @Override
  public int getIconHeight() {
    return 16;
  }

  @Override
  public float getMinU() {
    return 0;
  }

  @Override
  public float getMaxU() {
    return 1;
  }

  @Override
  public float getInterpolatedU(double p_94214_1_) {
    return 0;
  }

  @Override
  public float getMinV() {
    return 0;
  }

  @Override
  public float getMaxV() {
    return 1;
  }

  @Override
  public float getInterpolatedV(double p_94207_1_) {
    return 0;
  }

  @Override
  public String getIconName() {
    return "storageBall";
  }
}
