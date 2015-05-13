package unwrittenfun.minecraft.unwrittenblocks.common.blocks;

import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * Author: James Birtles
 */
public interface IRefulgentBlock {
  IIcon getIconFromDirection(int direction);

  int getRGB(IBlockAccess world, int x, int y, int z);

  int getRGBForMeta(int metadata);
}
