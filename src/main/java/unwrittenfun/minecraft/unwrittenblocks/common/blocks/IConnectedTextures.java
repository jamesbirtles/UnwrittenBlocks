package unwrittenfun.minecraft.unwrittenblocks.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.util.IIcon;

public interface IConnectedTextures {
  /**
   * Allows re-using block classes in which you may not want all blocks to have connected textures.
   * @return If the block has connected textures
   */
  boolean hasConnectedTextures();

  /**
   * Gets the icon for the side when there is no connected block on said side.
   * @param side 0 - Down, 1 - Up, 2 - Right, 3 - Left, 4 - Top Left, 5 - Top Right, 6 - Bottom Right, 7 - Bottom Left
   * @return the icon
   */
  IIcon getIconForDisconnectedSide(int side);

  boolean canConnectToBlock(Block block);
}
