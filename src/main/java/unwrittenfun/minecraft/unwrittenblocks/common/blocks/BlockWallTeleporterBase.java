package unwrittenfun.minecraft.unwrittenblocks.common.blocks;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import unwrittenfun.minecraft.unwrittenblocks.common.ModInfo;
import unwrittenfun.minecraft.unwrittenblocks.common.UnwrittenBlocks;
import unwrittenfun.minecraft.unwrittenblocks.common.tileEntities.IWallTeleporterBlock;
import unwrittenfun.minecraft.unwrittenblocks.common.tileEntities.TEWallTeleporterBase;

/**
 * Author: James Birtles
 */
public class BlockWallTeleporterBase extends BlockContainer {
  public BlockWallTeleporterBase(String key) {
    super(Material.iron);
    setCreativeTab(UnwrittenBlocks.creativeTabUB);
    setBlockName(key);
    setBlockTextureName(ModInfo.RESOURCE_LOCATION + ":" + key);
    setHardness(2F);
  }

  @Override
  public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
    TEWallTeleporterBase teleporterBase = (TEWallTeleporterBase) world.getTileEntity(x, y, z);
    if (teleporterBase != null && teleporterBase.getWTNetwork().hasDestination()) return AxisAlignedBB.getBoundingBox(0, 0, 0, 0, 0, 0);
    return super.getCollisionBoundingBoxFromPool(world, x, y, z);
//    return AxisAlignedBB.getBoundingBox(0, 0, 0, 0, 0, 0);
  }

  @Override
  public TileEntity createNewTileEntity(World world, int idk) {
    return new TEWallTeleporterBase();
  }

  @Override
  public boolean canPlaceBlockAt(World world, int x, int y, int z) {
    for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
      TileEntity tileEntity = world.getTileEntity(x + direction.offsetX, y + direction.offsetY, z + direction.offsetZ);
      if (tileEntity instanceof IWallTeleporterBlock) {
        IWallTeleporterBlock teleporter = (IWallTeleporterBlock) tileEntity;
        if (teleporter.hasWTNetwork()) {
          return false;
        }
      }
    }
    return super.canPlaceBlockAt(world, x, y, z);
  }

  @Override
  public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
    FMLNetworkHandler.openGui(player, UnwrittenBlocks.instance, 0, world, x, y, z);
    return true;
  }

  @Override
  public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
    if (!world.isRemote) {
      if (entity instanceof EntityPlayerMP) {
        TEWallTeleporterBase teleporterBase = (TEWallTeleporterBase) world.getTileEntity(x, y, z);
        if (teleporterBase.hasWTNetwork()) teleporterBase.getWTNetwork().playerCollided(world, (EntityPlayerMP) entity);
      }
    }
    super.onEntityCollidedWithBlock(world, x, y, z, entity);
  }
}
