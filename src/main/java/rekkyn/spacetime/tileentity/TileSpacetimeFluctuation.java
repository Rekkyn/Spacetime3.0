package rekkyn.spacetime.tileentity;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import rekkyn.spacetime.SpacetimeMod;
import rekkyn.spacetime.network.ParticlePacket;
import rekkyn.spacetime.particle.ParticleEffects;
import rekkyn.spacetime.utility.MathUtil;

import java.util.Iterator;
import java.util.List;

public class TileSpacetimeFluctuation extends TileEntity {

    @Override
    public void updateEntity() {
        List list = worldObj.getEntitiesWithinAABBExcludingEntity(null, AxisAlignedBB
                .getBoundingBox(xCoord - 10, yCoord - 10, zCoord - 10, xCoord + 10, yCoord + 10, zCoord + 10));
        Entity entity;
        for (Iterator iterator = list.iterator(); iterator.hasNext(); pullEntities(entity)) {
            entity = (Entity) iterator.next();
        }
    }

    public void pullEntities(Entity entity) {
        double xDist = xCoord + 0.5 - entity.posX;
        double yDist = yCoord + 0.5 - entity.posY;
        double zDist = zCoord + 0.5 - entity.posZ;
        Vec3 dist = Vec3.createVectorHelper(xDist, yDist, zDist);
        if (dist.lengthVector() > 10) return;

        if (worldObj.rand.nextInt(400) == 0) {
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
            SpacetimeMod.network.sendToAllAround(packet,
                                                 new NetworkRegistry.TargetPoint(worldObj.provider.dimensionId, xCoord + spawnX + 0.5,
                                                                                 yCoord + spawnY + 0.5, zCoord + spawnZ + 0.5, 32));
        }

        if (entity instanceof EntityPlayer) {
            if (((EntityPlayer) entity).capabilities.isFlying) return;
        }

        double power = 0.4 / (dist.lengthVector() * dist.lengthVector());

        dist.normalize();

        entity.addVelocity(dist.xCoord * power, dist.yCoord * power, dist.zCoord * power);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public double getMaxRenderDistanceSquared() {
        return 65536.0D;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
        return AxisAlignedBB
                .getBoundingBox(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY,
                                Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
    }
}
