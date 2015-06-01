package rekkyn.spacetime.particle;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.world.World;
import rekkyn.spacetime.utility.Bezier;

public class ParticleEffects {
    private static Minecraft mc = Minecraft.getMinecraft();
    private static World world = mc.theWorld;

    @SideOnly(Side.CLIENT)
    public static EntityFX spawnParticle(ParticleTypes type, double x, double y, double z, double motionX, double motionY, double motionZ,
                                         Object data) {
        if (mc != null && mc.renderViewEntity != null && mc.effectRenderer != null) {
            int particleSetting = mc.gameSettings.particleSetting;

            if (particleSetting == 1 && world.rand.nextInt(3) == 0) {
                particleSetting = 2;
            }

            double xdist = mc.renderViewEntity.posX - x;
            double ydist = mc.renderViewEntity.posY - y;
            double zdist = mc.renderViewEntity.posZ - z;
            EntityFX effect = null;
            double maxDist = 16.0D;

            if (xdist * xdist + ydist * ydist + zdist * zdist > maxDist * maxDist) {
                return null;
            } else if (particleSetting > 1) {
                return null;
            } else {
                if (type == ParticleTypes.ORBIT) {
                    effect = new OrbitParticle(world, x, y, z, motionX, motionY, motionZ, data);
                }
                if (type == ParticleTypes.BEZIER) {
                    effect = new BezierParticle(world, x, y, z, data);
                }

                if (effect != null) {
                    mc.effectRenderer.addEffect(effect);
                }
                return effect;
            }
        }

        return null;
    }

    public enum ParticleTypes {
        ORBIT, BEZIER;
    }
}
