package unwrittenfun.minecraft.unwrittenblocks.worldgen

import cpw.mods.fml.common.IWorldGenerator
import java.util.Random
import net.minecraft.world.World
import net.minecraft.world.chunk.IChunkProvider
import net.minecraft.block.Block
import unwrittenfun.minecraft.unwrittenblocks.blocks.UBBlocks

/**
 * Mod: UnwrittenBlocks
 * Author: UnwrittenFun
 * License: Minecraft Mod Public License (Version 1.0.1)
 */
class GenPleather extends IWorldGenerator {
  def generate(random: Random, chunkX: Int, chunkZ: Int, world: World, chunkGenerator: IChunkProvider, chunkProvider: IChunkProvider) {
    if (random.nextInt(32) == 10) {
      val x = chunkX * 16 + random.nextInt(16)
      val z = chunkZ * 16 + random.nextInt(16)
      val y = world.getTopSolidOrLiquidBlock(x, z)
      for (dx <- -3 to 3; dz <- -3 to 3; if world.getBlockId(x + dx, y, z + dz) == Block.tallGrass.blockID) world.setBlock(x + dx, y, z + dz, UBBlocks.pleatherPlant.blockID, 7, 3)
    }
  }
}
