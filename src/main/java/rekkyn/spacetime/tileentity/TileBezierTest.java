package rekkyn.spacetime.tileentity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import rekkyn.spacetime.SpacetimeMod;
import rekkyn.spacetime.network.ParticlePacket;
import rekkyn.spacetime.particle.ParticleEffects;

import java.util.Iterator;
import java.util.List;

public class TileBezierTest extends TileEntity implements IUpdatePlayerListBox {

    @Override
    public void update() {
        int x = getPos().getX();
        int y = getPos().getY();
        int z = getPos().getZ();
        List list = worldObj
                .getEntitiesWithinAABBExcludingEntity(null, AxisAlignedBB.fromBounds(x - 10, y - 10, z - 10, x + 10, y + 10, z + 10));
        Entity entity;
        for (Iterator iterator = list.iterator(); iterator.hasNext(); ) {
            entity = (Entity) iterator.next();
            if (entity instanceof EntityLivingBase) go((EntityLivingBase) entity);
        }
    }

    private void go(EntityLivingBase entity) {
        if (!worldObj.isRemote) {
            int x = getPos().getX();
            int y = getPos().getY();
            int z = getPos().getZ();

            for (int i = 0; i < 3; i++) {
                ParticlePacket packet = new ParticlePacket(ParticleEffects.ParticleTypes.BEZIER, x + 0.5, y + 0.5, z + 0.5, 0, 0, 0,
                                                           entity);

                SpacetimeMod.network.sendToAllAround(packet,
                                                     new NetworkRegistry.TargetPoint(worldObj.provider.getDimensionId(), x + 0.5, y + 0.5,
                                                                                     z + 0.5, 32));
            }
        }
    }
}
