package unwrittenfun.minecraft.unwrittenblocks.common.handlers;

import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import unwrittenfun.minecraft.unwrittenblocks.client.gui.GuiDarkInfuser;
import unwrittenfun.minecraft.unwrittenblocks.client.gui.GuiRefulgentFabricator;
import unwrittenfun.minecraft.unwrittenblocks.client.gui.GuiWallTeleporter;
import unwrittenfun.minecraft.unwrittenblocks.common.containers.ContainerDarkInfuser;
import unwrittenfun.minecraft.unwrittenblocks.common.containers.ContainerRefulgentInfuser;
import unwrittenfun.minecraft.unwrittenblocks.common.containers.ContainerWallTeleporter;
import unwrittenfun.minecraft.unwrittenblocks.common.tileEntities.TEDarkInfuser;
import unwrittenfun.minecraft.unwrittenblocks.common.tileEntities.TERefulgentFabricator;
import unwrittenfun.minecraft.unwrittenblocks.common.tileEntities.TEWallTeleporterBase;

/**
 * Project: UnwrittenBlocks
 * Author: UnwrittenFun
 * Created: 06/11/2014.
 */
public class GuiHandler implements IGuiHandler {
  @Override
  public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
    TileEntity tileEntity = world.getTileEntity(x, y, z);
    switch (id) {
      case 0: // Wall Teleporter
        if (tileEntity instanceof TEWallTeleporterBase) {
          return new ContainerWallTeleporter(player.inventory, (TEWallTeleporterBase) tileEntity);
        }
        break;
      case 1: // Dark Infuser
        if (tileEntity instanceof TEDarkInfuser) {
          return new ContainerDarkInfuser(player.inventory, (TEDarkInfuser) tileEntity);
        }
      case 2: // Refulgent Fabricator
        if (tileEntity instanceof TERefulgentFabricator) {
          return new ContainerRefulgentInfuser(player.inventory, (TERefulgentFabricator) tileEntity);
        }
        break;
    }
    return null;
  }

  @Override
  public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
    TileEntity tileEntity = world.getTileEntity(x, y, z);
    switch (id) {
      case 0: // Wall Teleporter
        if (tileEntity instanceof TEWallTeleporterBase) {
          return new GuiWallTeleporter(player.inventory, (TEWallTeleporterBase) tileEntity);
        }
        break;
      case 1: // Dark Infuser
        if (tileEntity instanceof TEDarkInfuser) {
          return new GuiDarkInfuser(player.inventory, (TEDarkInfuser) tileEntity);
        }
        break;
      case 2: // Refulgent Fabricator
        if (tileEntity instanceof TERefulgentFabricator) {
          return new GuiRefulgentFabricator(player.inventory, (TERefulgentFabricator) tileEntity);
        }
        break;
    }
    return null;
  }
}
