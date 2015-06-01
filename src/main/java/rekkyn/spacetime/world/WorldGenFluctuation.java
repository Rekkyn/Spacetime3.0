package rekkyn.spacetime.world;

import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
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
    public boolean generate(World world, Random rand, BlockPos pos) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        if (y > world.getPrecipitationHeight(pos).getY() + 25) { return false; }

        for (int q = 0; q < 25; q++) {
            if (!world.isAirBlock(pos.add(0, -q, 0))) {
                if (world.getBlockState(pos.add(0, -q, 0)).getBlock().equals(Blocks.water)) return false;
                break;
            }
        }

        for (int lmnop = -1; lmnop <= 1; lmnop++) {
            for (int qrst = -1; qrst <= 1; qrst++) {
                for (int wxyz = -1; wxyz <= 1; wxyz++) {
                    world.setBlockToAir(pos.add(lmnop, qrst, wxyz));
                }
            }
        }
        Explosion explosion = new Explosion(world, null, x, y, z, radius, false, false);
        explosion.doExplosionA();
        List affectedBlockPositions = explosion.func_180343_e(); //getAffectedBlockPositions
        Iterator iterator;
        BlockPos blockPos;
        int i;
        int j;
        int k;
        iterator = affectedBlockPositions.iterator();
        while (iterator.hasNext()) {
            blockPos = (BlockPos) iterator.next();
            if (!world.isAirBlock(pos)) {
                world.setBlockToAir(blockPos);
            }
        }
        world.setBlockState(pos, ModBlocks.spacetimeFluctuation.getDefaultState());
        return true;
    }
}
