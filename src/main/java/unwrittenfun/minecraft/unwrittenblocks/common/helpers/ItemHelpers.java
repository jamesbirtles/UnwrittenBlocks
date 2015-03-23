package unwrittenfun.minecraft.unwrittenblocks.common.helpers;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * Author: James Birtles
 */
public class ItemHelpers {
  public static void dropItemStack(ItemStack stack, World world, int x, int y, int z) {
    float spawnX = x + world.rand.nextFloat();
    float spawnY = y + world.rand.nextFloat();
    float spawnZ = z + world.rand.nextFloat();
    EntityItem droppedItem = new EntityItem(world, spawnX, spawnY, spawnZ, stack);
    droppedItem.motionX = (-0.5f + world.rand.nextFloat()) * 0.05F;
    droppedItem.motionY = (4f + world.rand.nextFloat()) * 0.05F;
    droppedItem.motionZ = (-0.5f + world.rand.nextFloat()) * 0.05F;
    world.spawnEntityInWorld(droppedItem);
  }
}
