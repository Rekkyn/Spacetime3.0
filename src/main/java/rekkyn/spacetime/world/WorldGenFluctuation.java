package rekkyn.spacetime.world;

import net.minecraft.init.Blocks;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import rekkyn.spacetime.init.ModBlocks;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class WorldGenFluctuation extends WorldGenerator {

    float radius;

    public WorldGenFluctuation(float radius) {
        this.radius = radius;
    }

    @Override
    public boolean generate(World world, Random random, int x, int y, int z) {
        if (y > world.getPrecipitationHeight(x, z) + 25) { return false; }

        for (int q = 0; q < 25; q++) {
            if (!world.isAirBlock(x, y - q, z)) {
                if (world.getBlock(x, y - q, z).equals(Blocks.water)) return false;
                break;
            }
        }

        for (int lmnop = -1; lmnop <= 1; lmnop++) {
            for (int qrst = -1; qrst <= 1; qrst++) {
                for (int wxyz = -1; wxyz <= 1; wxyz++) {
                    world.setBlockToAir(x + lmnop, y + qrst, z + wxyz);
                }
            }
        }
        Explosion explosion = new Explosion(world, null, x, y, z, radius);
        explosion.doExplosionA();
        List affectedBlockPositions = explosion.affectedBlockPositions;
        Iterator iterator;
        ChunkPosition chunkposition;
        int i;
        int j;
        int k;
        iterator = affectedBlockPositions.iterator();
        while (iterator.hasNext()) {
            chunkposition = (ChunkPosition) iterator.next();
            i = chunkposition.chunkPosX;
            j = chunkposition.chunkPosY;
            k = chunkposition.chunkPosZ;
            if (!world.isAirBlock(i, j, k)) {
                world.setBlockToAir(i, j, k);
            }
        }
        world.setBlock(x, y, z, ModBlocks.spacetimeFluctuation);
        return true;
    }

}
