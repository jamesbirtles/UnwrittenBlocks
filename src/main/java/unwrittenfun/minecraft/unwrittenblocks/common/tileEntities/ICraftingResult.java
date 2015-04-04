package unwrittenfun.minecraft.unwrittenblocks.common.tileEntities;

/**
 * Author: James Birtles
 */
public interface ICraftingResult {
  void onResultTaken();

  boolean canTakeResult();
}
