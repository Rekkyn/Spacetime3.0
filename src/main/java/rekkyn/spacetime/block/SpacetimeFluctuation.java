package rekkyn.spacetime.block;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import rekkyn.spacetime.SpacetimeMod;
import rekkyn.spacetime.network.ParticlePacket;
import rekkyn.spacetime.particle.ParticleEffects;
import rekkyn.spacetime.reference.Reference;
import rekkyn.spacetime.tileentity.TileSpacetimeFluctuation;
import rekkyn.spacetime.utility.CreativeTabSpacetime;
import rekkyn.spacetime.utility.MathUtil;

import java.util.Random;

public class SpacetimeFluctuation extends BlockContainer {

    public SpacetimeFluctuation() {
        this(Material.rock);
    }

    protected SpacetimeFluctuation(Material material) {
        super(material);
        setBlockName("spacetimeFluctuation");
        setCreativeTab(CreativeTabSpacetime.SPACETIMETAB);
        setLightLevel(1);
        setBlockBounds(0.25F, 0.25F, 0.25F, 0.75F, 0.75F, 0.75F);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
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
    public boolean onBlockActivated(World worldObj, int xCoord, int yCoord, int zCoord, EntityPlayer entity, int p_149727_6_,
                                    float p_149727_7_, float p_149727_8_, float p_149727_9_) {
        double radius = 8;
        double spawnX;
        double spawnY;
        double spawnZ;
        while (true) {
            spawnX = MathUtil.NegOneToOne() * radius;
            spawnY = MathUtil.NegOneToOne() * radius;
            spawnZ = MathUtil.NegOneToOne() * radius;

            if (spawnX * spawnX + spawnY * spawnY + spawnZ * spawnZ < radius * radius) break;
        }

        ParticlePacket packet = new ParticlePacket(ParticleEffects.ParticleTypes.ORBIT, xCoord + spawnX + 0.5, yCoord + spawnY + 0.5,
                                                   zCoord + spawnZ + 0.5, MathUtil.NegOneToOne() * 0.3, MathUtil.NegOneToOne() * 0.3,
                                                   MathUtil.NegOneToOne() * 0.3, entity);
        SpacetimeMod.network.sendToAllAround(packet, new NetworkRegistry.TargetPoint(worldObj.provider.dimensionId, xCoord + spawnX + 0.5,
                                                                                     yCoord + spawnY + 0.5, zCoord + spawnZ + 0.5, 32));
        return true;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5) {
        return false;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4) {
        return null;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int p_149915_2_) {
        return new TileSpacetimeFluctuation();
    }

    @Override
    public String getUnlocalizedName() {
        return String.format("tile.%s%s", Reference.MOD_ID.toLowerCase() + ":", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        blockIcon = iconRegister.registerIcon(String.format("%s", getUnwrappedUnlocalizedName(this.getUnlocalizedName())));
    }

    protected String getUnwrappedUnlocalizedName(String unlocalizedName) {
        return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
    }

}
