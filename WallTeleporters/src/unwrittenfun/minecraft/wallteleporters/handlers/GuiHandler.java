package unwrittenfun.minecraft.wallteleporters.handlers;

import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import unwrittenfun.minecraft.wallteleporters.WallTeleporters;
import unwrittenfun.minecraft.wallteleporters.blocks.tileentities.TileEntityWallTeleporter;
import unwrittenfun.minecraft.wallteleporters.client.gui.GuiWallTeleporter;
import unwrittenfun.minecraft.wallteleporters.gui.containers.ContainerWallTeleporter;

/**
 * Mod: Wall Teleporters
 * Author: UnwrittenFun
 * License: Minecraft Mod Public License (Version 1.0.1)
 */
public class GuiHandler implements IGuiHandler {
    public GuiHandler() {
        NetworkRegistry.instance().registerGuiHandler(WallTeleporters.instance, this);
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
        switch (ID) {
            case 0:
                if (tileEntity instanceof TileEntityWallTeleporter) {
                    return new ContainerWallTeleporter(player.inventory,
                            ((TileEntityWallTeleporter) tileEntity).multiblock);
                }
                break;
        }

        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
        switch (ID) {
            case 0:
                if (tileEntity instanceof TileEntityWallTeleporter) {
                    return new GuiWallTeleporter(player.inventory, ((TileEntityWallTeleporter) tileEntity).multiblock);
                }
                break;
        }

        return null;
    }
}
