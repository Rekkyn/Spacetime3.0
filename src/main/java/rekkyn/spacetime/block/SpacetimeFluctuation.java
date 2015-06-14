package rekkyn.spacetime.block;


import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import rekkyn.spacetime.SpacetimeMod;
import rekkyn.spacetime.network.ParticlePacket;
import rekkyn.spacetime.particle.ParticleEffects;
import rekkyn.spacetime.tileentity.TileSpacetimeFluctuation;
import rekkyn.spacetime.utility.MathUtil;

import java.util.Random;

public class SpacetimeFluctuation extends GenericBlock implements ITileEntityProvider {

    public SpacetimeFluctuation() {
        this(Material.rock);
    }

    protected SpacetimeFluctuation(Material material) {
        super(material);
        setLightLevel(1);
        setBlockBounds(0.25F, 0.25F, 0.25F, 0.75F, 0.75F, 0.75F);
        setResistance(2000.0F);
    }

    @Override
    public void randomDisplayTick(World world, BlockPos pos, IBlockState state, Random rand) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        if (rand.nextInt(60) == 0) {
            world.playSound(x + 0.5D, y + 0.5D, z + 0.5D, "portal.portal", 0.5F, rand.nextFloat() * 0.4F + 0.8F, false);
        }

        double radius = 8;
        double spawnX = MathUtil.NegOneToOne() * radius;
        double spawnY = MathUtil.NegOneToOne() * radius;
        double spawnZ = MathUtil.NegOneToOne() * radius;

        if (spawnX * spawnX + spawnY * spawnY + spawnZ * spawnZ > radius * radius) return;

        ParticleEffects.spawnParticle(ParticleEffects.ParticleTypes.ORBIT, x + spawnX + 0.5, y + spawnY + 0.5, z + spawnZ + 0.5,
                                      MathUtil.NegOneToOne() * 0.3, MathUtil.NegOneToOne() * 0.3, MathUtil.NegOneToOne() * 0.3,
                                      new double[]{ x + 0.5, y + 0.5, z + 0.5 });
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean isFullCube() {
        return false;
    }

    @Override
    public int getRenderType() {
        return -1;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
        return null;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int p_149915_2_) {
        return new TileSpacetimeFluctuation();
    }

    public String getName() {
        return "spacetimeFluctuation";
    }
}
