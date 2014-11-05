package unwrittenfun.minecraft.unwrittenblocks.common.worldgen;

import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import unwrittenfun.minecraft.unwrittenblocks.common.blocks.BlockRegister;

import java.util.Random;

/**
 * Project: UnwrittenBlocks
 * Author: UnwrittenFun
 * Created: 04/11/2014.
 */
public class WorldGenPleather implements IWorldGenerator {
    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
        if (random.nextInt(32) == 10) {
            int x = chunkX * 16 + random.nextInt(16);
            int z = chunkZ * 16 + random.nextInt(16);
            for (int dx = -3; dx <= 3; dx++) {
                for (int dz = -3; dz <= 3; dz++) {
                    int y = world.getTopSolidOrLiquidBlock(x + dx, z + dz);
                    if (world.getBlock(x + dx, y, z + dz) == Blocks.tallgrass) {
                        world.setBlock(x + dx, y, z + dz, BlockRegister.pleatherPlant, 7, 3);
                    }
                }
            }
        }
    }
}
