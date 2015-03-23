package unwrittenfun.minecraft.unwrittenblocks.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import unwrittenfun.minecraft.unwrittenblocks.common.ModInfo;
import unwrittenfun.minecraft.unwrittenblocks.common.UnwrittenBlocks;
import unwrittenfun.minecraft.unwrittenblocks.common.multiblock.WallTeleporterNetwork;
import unwrittenfun.minecraft.unwrittenblocks.common.tileEntities.IWallTeleporterBlock;
import unwrittenfun.minecraft.unwrittenblocks.common.tileEntities.TEWallTeleporterBase;
import unwrittenfun.minecraft.unwrittenblocks.common.tileEntities.TEWallTeleporterWall;

/**
 * Author: James Birtles
 */
public class BlockWallTeleporterWall extends BlockContainer {
  public BlockWallTeleporterWall(String key) {
    super(Material.iron);
    setCreativeTab(UnwrittenBlocks.creativeTabUB);
    setBlockName(key);
    setBlockTextureName(ModInfo.RESOURCE_LOCATION + ":" + key);
    setHardness(2F);
  }

  @Override
  public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
    return AxisAlignedBB.getBoundingBox(0, 0, 0, 0, 0, 0);
  }

  @Override
  public TileEntity createNewTileEntity(World world, int idk) {
    return new TEWallTeleporterWall();
  }

  @Override
  public boolean canPlaceBlockAt(World world, int x, int y, int z) {
    WallTeleporterNetwork networkTemp = null;
    for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
      TileEntity tileEntity = world.getTileEntity(x + direction.offsetX, y + direction.offsetY, z + direction.offsetZ);
      if (tileEntity instanceof IWallTeleporterBlock) {
        IWallTeleporterBlock teleporter = (IWallTeleporterBlock) tileEntity;
        if (teleporter.hasWTNetwork()) {
          if (networkTemp == null) {
            networkTemp = teleporter.getWTNetwork();
          } else if (networkTemp != teleporter.getWTNetwork()) {
            return false;
          }
        }
      }
    }
    return super.canPlaceBlockAt(world, x, y, z);
  }

  @Override
  public void onBlockAdded(World world, int x, int y, int z) {
    // TODO: Should only do this on the server & sync connection stuff over.
    TEWallTeleporterWall wallTeleporterWall = (TEWallTeleporterWall) world.getTileEntity(x, y, z);
    wallTeleporterWall.connectToWallsAround();
  }

  @Override
  public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
    if (!world.isRemote) {
      TEWallTeleporterWall wallTeleporterWall = (TEWallTeleporterWall) world.getTileEntity(x, y, z);
      if (wallTeleporterWall.hasWTNetwork()) {
        WallTeleporterNetwork network = wallTeleporterWall.getWTNetwork();
        player.addChatComponentMessage(new ChatComponentText(""));
        player.addChatComponentMessage(new ChatComponentText("Connected Network: " + network.hashCode()));
        player.addChatComponentMessage(new ChatComponentText("Number of walls: " + network.walls.size()));
        player.addChatComponentMessage(new ChatComponentText("Base location: (" + network.base.xCoord + ", " + network.base.yCoord + ", " + network.base.zCoord + ")"));
      } else {
        player.addChatComponentMessage(new ChatComponentText("No network connected."));
      }
    }
    return false;
  }

  @Override
  public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
    TEWallTeleporterWall teleporterWall = (TEWallTeleporterWall) world.getTileEntity(x, y, z);
    teleporterWall.setIgnoreWT(true);
    if (teleporterWall.hasWTNetwork()) teleporterWall.getWTNetwork().refreshNetwork();
    super.breakBlock(world, x, y, z, block, meta);
  }
}
