package rekkyn.spacetime.tileentity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import rekkyn.spacetime.SpacetimeMod;
import rekkyn.spacetime.network.ParticlePacket;
import rekkyn.spacetime.particle.ParticleEffects;
import rekkyn.spacetime.utility.MathUtil;

import java.util.Iterator;
import java.util.List;

public class TileSpacetimeFluctuation extends TileEntity implements IUpdatePlayerListBox {

    @Override
    public void update() {
        int x = getPos().getX();
        int y = getPos().getY();
        int z = getPos().getZ();
        List list = worldObj
                .getEntitiesWithinAABBExcludingEntity(null, AxisAlignedBB.fromBounds(x - 10, y - 10, z - 10, x + 10, y + 10, z + 10));
        Entity entity;
        for (Iterator iterator = list.iterator(); iterator.hasNext(); pullEntities(entity)) {
            entity = (Entity) iterator.next();
        }
    }

    public void pullEntities(Entity entity) {
        int x = getPos().getX();
        int y = getPos().getY();
        int z = getPos().getZ();

        double xDist = x + 0.5 - entity.posX;
        double yDist = y + 0.5 - entity.posY;
        double zDist = z + 0.5 - entity.posZ;
        Vec3 dist = new Vec3(xDist, yDist, zDist);
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

            ParticlePacket packet = new ParticlePacket(ParticleEffects.ParticleTypes.ORBIT, x + spawnX + 0.5, y + spawnY + 0.5,
                                                       z + spawnZ + 0.5, MathUtil.NegOneToOne() * 0.3, MathUtil.NegOneToOne() * 0.3,
                                                       MathUtil.NegOneToOne() * 0.3, entity);
            SpacetimeMod.network.sendToAllAround(packet,
                                                 new NetworkRegistry.TargetPoint(worldObj.provider.getDimensionId(), x + spawnX + 0.5,
                                                                                 y + spawnY + 0.5, z + spawnZ + 0.5, 32));
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
                .fromBounds(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY,
                            Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
    }
}
