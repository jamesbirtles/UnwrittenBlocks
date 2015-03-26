package unwrittenfun.minecraft.unwrittenblocks.common.items;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import unwrittenfun.minecraft.unwrittenblocks.common.blocks.BlockRegister;

import java.util.Collections;
import java.util.List;

/**
 * Project: UnwrittenBlocks Author: UnwrittenFun Created: 04/11/2014.
 */
public class ItemPleatherBulb extends ItemUB implements IPlantable {
  public ItemPleatherBulb(String key) {
    super(key);
  }

  @Override
  public EnumPlantType getPlantType(IBlockAccess world, int x, int y, int z) {
    return EnumPlantType.Plains;
  }

  @Override
  public Block getPlant(IBlockAccess world, int x, int y, int z) {
    return BlockRegister.pleatherPlant;
  }

  @Override
  public int getPlantMetadata(IBlockAccess world, int x, int y, int z) {
    return 0;
  }

  @Override
  public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
    Block block = world.getBlock(x, y, z);
    if (side == 1 && world.isAirBlock(x, y + 1, z) && (block == Blocks.grass || block == Blocks.dirt)) {
      stack.stackSize--;
      world.setBlock(x, y + 1, z, BlockRegister.pleatherPlant);

      return true;
    }
    return false;
  }

  @SuppressWarnings("unchecked")
  @Override
  public void addInformation(ItemStack stack, EntityPlayer player, List lines,
                             boolean bool) {
    Collections.addAll(lines, getLinesFromLang("unwrittenblocks.text.pleatherBulb"));

    super.addInformation(stack, player, lines, bool);
  }
}
