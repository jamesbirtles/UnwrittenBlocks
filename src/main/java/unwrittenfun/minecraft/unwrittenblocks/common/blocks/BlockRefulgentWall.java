package unwrittenfun.minecraft.unwrittenblocks.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;
import unwrittenfun.minecraft.unwrittenblocks.common.ModInfo;
import unwrittenfun.minecraft.unwrittenblocks.common.UnwrittenBlocks;

import java.util.List;

/**
 * Author: James Birtles
 */
public class BlockRefulgentWall extends Block implements IRefulgentBlock {
  public BlockRefulgentWall(String key) {
    super(Material.rock);
    setCreativeTab(UnwrittenBlocks.creativeTabUB);
    setBlockName(key);
    setBlockTextureName(ModInfo.RESOURCE_LOCATION + ":" + key);
    setHardness(1f);
  }

  @Override
  public IIcon getIconFromDirection(ForgeDirection direction) {
    return blockIcon;
  }

  @Override
  public int getRGB(IBlockAccess world, int x, int y, int z) {
    return ItemDye.field_150922_c[world.getBlockMetadata(x, y, z)];
  }

  @Override
  public int getRGBForMeta(int metadata) {
    return ItemDye.field_150922_c[metadata];
  }

  @Override
  public int getRenderType() {
    return UnwrittenBlocks.proxy.refulgentRenderer.getRenderId();
  }

  @SuppressWarnings("unchecked")
  @Override
  public void getSubBlocks(Item item, CreativeTabs tab, List stacks) {
    for (int i = 0; i < 16; i++) {
      stacks.add(new ItemStack(this, 1, i));
    }
  }

  @Override
  public int damageDropped(int meta) {
    return meta;
  }
}
