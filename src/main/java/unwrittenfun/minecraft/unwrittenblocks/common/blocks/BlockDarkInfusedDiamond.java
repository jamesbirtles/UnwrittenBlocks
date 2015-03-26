package unwrittenfun.minecraft.unwrittenblocks.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import unwrittenfun.minecraft.unwrittenblocks.common.ModInfo;
import unwrittenfun.minecraft.unwrittenblocks.common.UnwrittenBlocks;

/**
 * Author: James Birtles
 */
public class BlockDarkInfusedDiamond extends Block {
  public BlockDarkInfusedDiamond(String key) {
    super(Material.iron);
    setCreativeTab(UnwrittenBlocks.creativeTabUB);
    setBlockName(key);
    setBlockTextureName(ModInfo.RESOURCE_LOCATION + ":" + key);
    setHardness(2F);
  }
}
