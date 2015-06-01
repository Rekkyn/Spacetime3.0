package rekkyn.spacetime.tileentity;

import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import rekkyn.spacetime.SpacetimeMod;
import rekkyn.spacetime.network.ParticlePacket;
import rekkyn.spacetime.particle.ParticleEffects;
import rekkyn.spacetime.utility.Bezier;

import java.util.Iterator;
import java.util.List;

public class TileBezierTest extends TileEntity {

    @Override
    public void updateEntity() {
        List list = worldObj.getEntitiesWithinAABBExcludingEntity(null, AxisAlignedBB
                .getBoundingBox(xCoord - 10, yCoord - 10, zCoord - 10, xCoord + 10, yCoord + 10, zCoord + 10));
        Entity entity;
        for (Iterator iterator = list.iterator(); iterator.hasNext(); ) {
            entity = (Entity) iterator.next();
            if (entity instanceof EntityLivingBase)
                go((EntityLivingBase) entity);
        }
    }

    private void go(EntityLivingBase entity) {
        if (!worldObj.isRemote) {

            for (int i = 0; i < 3; i++) {
                ParticlePacket packet = new ParticlePacket(ParticleEffects.ParticleTypes.BEZIER, xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, 0, 0,
                                                           0, entity);

                SpacetimeMod.network.sendToAllAround(packet,
                                                     new NetworkRegistry.TargetPoint(worldObj.provider.dimensionId, xCoord + 0.5, yCoord + 0.5,
                                                                                     zCoord + 0.5, 32));
            }
        }
    }

}
