package unwrittenfun.minecraft.unwrittenblocks.common.multiblock;

import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.S07PacketRespawn;
import net.minecraft.network.play.server.S1DPacketEntityEffect;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldServer;
import unwrittenfun.minecraft.unwrittenblocks.common.items.ItemRegister;
import unwrittenfun.minecraft.unwrittenblocks.common.network.NetworkRegister;
import unwrittenfun.minecraft.unwrittenblocks.common.network.messages.TeleporterDestinationMessage;
import unwrittenfun.minecraft.unwrittenblocks.common.network.messages.TileEntityIntegerMessage;
import unwrittenfun.minecraft.unwrittenblocks.common.tileEntities.TEWallTeleporter;
import unwrittenfun.minecraft.unwrittenblocks.common.tileEntities.TEWallTeleporterBase;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Author: James Birtles
 */
public class WallTeleporterNetwork {
  public ArrayList<TEWallTeleporter> walls = new ArrayList<TEWallTeleporter>();
  public TEWallTeleporterBase base;
  public String destinationName;
  public int destinationWorldId;
  public float[] destinationData;
  public int fuel = 0;
  public int cooldown = 0;

  public WallTeleporterNetwork(TEWallTeleporterBase base) {
    this.base = base;
  }

  public void add(TEWallTeleporter wallTeleporterBlock) {
    wallTeleporterBlock.setWTNetwork(this);
    if (!walls.contains(wallTeleporterBlock)) {
      walls.add(wallTeleporterBlock);
    }
  }

  public void refreshNetwork() {
    base.setWTNetwork(null);
    if (!base.isInvalid()) {
      WallTeleporterNetwork network = new WallTeleporterNetwork(base);
      network.destinationData = destinationData;
      network.destinationName = destinationName;
      network.destinationWorldId = destinationWorldId;
      network.fuel = fuel;
      base.setWTNetwork(network);
    }

    for (TEWallTeleporter wall : walls) {
      wall.setWTNetwork(null);
    }

    for (TEWallTeleporter wall : walls) {
      if (!wall.isInvalid()) {
        wall.connectToWallsAround();
      }
    }
  }

  public boolean hasDestination() {
    return destinationData != null;
  }

  public void playerCollided(World world, EntityPlayerMP player) {
    if (hasDestination() && fuel > 0 && cooldown == 0) {
      if (destinationWorldId != player.worldObj.provider.dimensionId)
        transferPlayerToDimension(player, destinationWorldId);
      player.playerNetServerHandler.setPlayerLocation(destinationData[0], destinationData[1] + 0.5f, destinationData[2], destinationData[3], player.rotationPitch);
      setFuel(fuel - 1);

      if (fuel == 0) base.onInventoryChanged();
      cooldown = 10;
    }
  }

  public void handleButton(int id) {
    switch (id) {
      case 0:
        setDestinationFromChip();
        break;
    }
  }

  private void setDestinationFromChip() {
    if (!base.getWorldObj().isRemote) {
      ItemStack chip = base.getStackInSlot(0);
      if (chip == null) {
        destinationName = null;
        destinationData = null;
        destinationWorldId = 0;
      } else if (chip.isItemEqual(new ItemStack(ItemRegister.gpsChip, 1, 1))) {
        NBTTagCompound locationData = chip.getTagCompound().getCompoundTag("LocationData");
        destinationName = locationData.getString("worldName");
        destinationWorldId = locationData.getInteger("worldId");
        destinationData = new float[4];
        destinationData[0] = locationData.getFloat("locationX");
        destinationData[1] = locationData.getFloat("locationY");
        destinationData[2] = locationData.getFloat("locationZ");
        destinationData[3] = locationData.getFloat("rotationYaw");
      }
      NetworkRegister.wrapper.sendToDimension(getDestinationPacket(), base.getWorldObj().provider.dimensionId);
    }
  }

  public void requestDestinationData(EntityPlayerMP player) {
    NetworkRegister.wrapper.sendTo(getDestinationPacket(), player);
  }

  public TeleporterDestinationMessage getDestinationPacket() {
    return TeleporterDestinationMessage.messageFrom(base.getWorldObj(), base.xCoord, base.yCoord, base.zCoord, destinationName, destinationWorldId, destinationData);
  }

  public void requestFuel(EntityPlayerMP player) {
    NetworkRegister.wrapper.sendTo(getFuelPacket(), player);
  }

  public TileEntityIntegerMessage getFuelPacket() {
    return TileEntityIntegerMessage.messageFrom(base.getWorldObj(), base.xCoord, base.yCoord, base.zCoord, 1, fuel);
  }

  public void writeToNBT(NBTTagCompound compound) {
    if (destinationData != null) {
      compound.setString("destinationName", destinationName);
      compound.setInteger("destinationWorldId", destinationWorldId);

      compound.setFloat("destinationData0", destinationData[0]);
      compound.setFloat("destinationData1", destinationData[1]);
      compound.setFloat("destinationData2", destinationData[2]);
      compound.setFloat("destinationData3", destinationData[3]);
    }

    compound.setInteger("fuel", fuel);
  }

  public void readFromNBT(NBTTagCompound compound) {
    if (compound.hasKey("destinationData0")) {
      destinationName = compound.getString("destinationName");
      destinationWorldId = compound.getInteger("destinationWorldId");
      destinationData = new float[4];
      destinationData[0] = compound.getFloat("destinationData0");
      destinationData[1] = compound.getFloat("destinationData1");
      destinationData[2] = compound.getFloat("destinationData2");
      destinationData[3] = compound.getFloat("destinationData3");
    }
    fuel = compound.getInteger("fuel");
  }

  public void setFuel(int fuel) {
    this.fuel = fuel;
    if (base.hasWorldObj() && !base.getWorldObj().isRemote) {
      NetworkRegister.wrapper.sendToDimension(getFuelPacket(), base.getWorldObj().provider.dimensionId);
    }
  }

  public void fillFuel() {
    setFuel(16);
  }

  // Copy of vanilla code I needed to fix
  public void transferPlayerToDimension(EntityPlayerMP p_72356_1_, int p_72356_2_) {
    int j = p_72356_1_.dimension;
    WorldServer worldserver = MinecraftServer.getServer().worldServerForDimension(p_72356_1_.dimension);
    p_72356_1_.dimension = p_72356_2_;
    WorldServer worldserver1 = MinecraftServer.getServer().worldServerForDimension(p_72356_1_.dimension);
    p_72356_1_.playerNetServerHandler.sendPacket(new S07PacketRespawn(p_72356_1_.dimension, worldserver1.difficultySetting, worldserver1.getWorldInfo().getTerrainType(), p_72356_1_.theItemInWorldManager.getGameType())); // Forge: Use new dimensions information
    worldserver.removePlayerEntityDangerously(p_72356_1_);
    p_72356_1_.isDead = false;
    transferEntityToWorld(p_72356_1_, j, worldserver, worldserver1);
    MinecraftServer.getServer().getConfigurationManager().func_72375_a(p_72356_1_, worldserver);
    p_72356_1_.playerNetServerHandler.setPlayerLocation(p_72356_1_.posX, p_72356_1_.posY, p_72356_1_.posZ, p_72356_1_.rotationYaw, p_72356_1_.rotationPitch);
    p_72356_1_.theItemInWorldManager.setWorld(worldserver1);
    MinecraftServer.getServer().getConfigurationManager().updateTimeAndWeatherForPlayer(p_72356_1_, worldserver1);
    MinecraftServer.getServer().getConfigurationManager().syncPlayerInventory(p_72356_1_);
    Iterator iterator = p_72356_1_.getActivePotionEffects().iterator();

    while (iterator.hasNext()) {
      PotionEffect potioneffect = (PotionEffect) iterator.next();
      p_72356_1_.playerNetServerHandler.sendPacket(new S1DPacketEntityEffect(p_72356_1_.getEntityId(), potioneffect));
    }
    FMLCommonHandler.instance().firePlayerChangedDimensionEvent(p_72356_1_, j, p_72356_2_);
  }

  public void transferEntityToWorld(Entity p_82448_1_, int p_82448_2_, WorldServer p_82448_3_, WorldServer p_82448_4_) {
    WorldProvider pOld = p_82448_3_.provider;
    WorldProvider pNew = p_82448_4_.provider;
    double moveFactor = pOld.getMovementFactor() / pNew.getMovementFactor();
    double d0 = p_82448_1_.posX * moveFactor;
    double d1 = p_82448_1_.posZ * moveFactor;
    double d3 = p_82448_1_.posX;
    double d4 = p_82448_1_.posY;
    double d5 = p_82448_1_.posZ;
    float f = p_82448_1_.rotationYaw;
    p_82448_3_.theProfiler.startSection("moving");

    p_82448_3_.theProfiler.endSection();

    p_82448_3_.theProfiler.startSection("placing");
    d0 = (double) MathHelper.clamp_int((int) d0, -29999872, 29999872);
    d1 = (double) MathHelper.clamp_int((int) d1, -29999872, 29999872);

    if (p_82448_1_.isEntityAlive()) {
      p_82448_1_.setLocationAndAngles(d0, p_82448_1_.posY, d1, p_82448_1_.rotationYaw, p_82448_1_.rotationPitch);
      p_82448_4_.spawnEntityInWorld(p_82448_1_);
      p_82448_4_.updateEntityWithOptionalForce(p_82448_1_, false);
    }

    p_82448_3_.theProfiler.endSection();

    p_82448_1_.setWorld(p_82448_4_);
  }
}
