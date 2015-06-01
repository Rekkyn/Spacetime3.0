package rekkyn.spacetime.world;

import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

public class SpacetimeWorldGen implements IWorldGenerator {

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
        switch (world.provider.getDimensionId()) {
            case -1:
                generateNether(world, random, chunkX * 16, chunkZ * 16);
                break;
            case 0:
                generateSurface(world, random, chunkX * 16, chunkZ * 16);
                break;
            case 1:
                generateEnd(world, random, chunkX * 16, chunkZ * 16);
                break;
        }
    }

    private void generateSurface(World world, Random rand, int chunkX, int chunkZ) {
        if (rand.nextInt(5) == 0) {
            int posX = chunkX + rand.nextInt(16);
            int posY = rand.nextInt(128);
            int posZ = chunkZ + rand.nextInt(16);

            new WorldGenFluctuation(5).generate(world, rand, new BlockPos(posX, posY, posZ));
        }
    }

    private void generateNether(World world, Random rand, int chunkX, int chunkZ) {
    }

    private void generateEnd(World world, Random rand, int chunkX, int chunkZ) {
    }
}
