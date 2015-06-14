package rekkyn.spacetime.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;
import rekkyn.spacetime.reference.Reference;
import rekkyn.spacetime.utility.MathUtil;

@SideOnly(Side.CLIENT)
public class OrbitParticle extends EntityFX {

    public Object target;

    protected OrbitParticle(World world, double x, double y, double z, double motionX, double motionY, double motionZ, Object target) {
        super(world, x, y, z, motionX, motionY, motionZ);
        this.target = target;
        float rand = 0.01F;
        this.motionX = motionX + MathUtil.NegOneToOne() * rand;
        this.motionY = motionY + MathUtil.NegOneToOne() * rand;
        this.motionZ = motionZ + MathUtil.NegOneToOne() * rand;
        particleMaxAge = 200;
        float variance = 0.07F;
        particleRed = (float) (0.619607843F + MathUtil.NegOneToOne() * variance);
        particleGreen = (float) (0.745098039F + MathUtil.NegOneToOne() * variance);
        particleBlue = (float) (0.901960784F + MathUtil.NegOneToOne() * variance);
    }

    @Override
    public void onUpdate() {
        prevPosX = posX;
        prevPosY = posY;
        prevPosZ = posZ;

        if (particleAge++ >= particleMaxAge) {
            setDead();
        }

        if (worldObj != null) {
            moveEntity(motionX, motionY, motionZ);
        }
        double motionDecayRate = 0.99;
        motionX *= motionDecayRate;
        motionY *= motionDecayRate;
        motionZ *= motionDecayRate;

        if (getTargetPos() != null && getTargetPos().length == 3) {
            double xDist = getTargetPos()[0] - posX;
            double yDist = getTargetPos()[1] - posY;
            double zDist = getTargetPos()[2] - posZ;

            Vec3 dist = new Vec3(xDist, yDist, zDist);

            if (dist.lengthVector() < 0.3) {
                setDead();
                return;
            }

            double power = 0.07 / (dist.lengthVector() * dist.lengthVector());

            dist.normalize();

            motionX += dist.xCoord * power;
            motionY += dist.yCoord * power;
            motionZ += dist.zCoord * power;
        }

        setAlphaF(particleAge < particleMaxAge / 2 ? alphaFunction(particleAge) : alphaFunction(particleMaxAge - particleAge));
    }

    //renderParticle
    @Override
    @SideOnly(Side.CLIENT)
    public void func_180434_a(WorldRenderer worldrenderer, Entity e, float par2, float par3, float par4, float par5, float par6,
                              float par7) {

        Minecraft.getMinecraft().getTextureManager()
                 .bindTexture(new ResourceLocation(Reference.MOD_ID.toLowerCase() + ":textures/particles/particles.png"));
        worldrenderer.setBrightness(240);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);

        //Note U=X V=Y
        float minU = 0.0F;
        float maxU = 0.5F;
        float minV = 0F;
        float maxV = 0.5F;
        float drawScale = 0.1F * particleScale;

        if (particleIcon != null) {
            minU = particleIcon.getMinU();
            maxU = particleIcon.getMaxU();
            minV = particleIcon.getMinV();
            maxV = particleIcon.getMaxV();
        }

        float drawX = (float) (prevPosX + (posX - prevPosX) * (double) par2 - interpPosX);
        float drawY = (float) (prevPosY + (posY - prevPosY) * (double) par2 - interpPosY);
        float drawZ = (float) (prevPosZ + (posZ - prevPosZ) * (double) par2 - interpPosZ);

        worldrenderer.setColorRGBA_F(particleRed, particleGreen, particleBlue, particleAlpha);

        worldrenderer.addVertexWithUV((double) (drawX - par3 * drawScale - par6 * drawScale), (double) (drawY - par4 * drawScale),
                                      (double) (drawZ - par5 * drawScale - par7 * drawScale), (double) maxU, (double) maxV);
        worldrenderer.addVertexWithUV((double) (drawX - par3 * drawScale + par6 * drawScale), (double) (drawY + par4 * drawScale),
                                      (double) (drawZ - par5 * drawScale + par7 * drawScale), (double) maxU, (double) minV);
        worldrenderer.addVertexWithUV((double) (drawX + par3 * drawScale + par6 * drawScale), (double) (drawY + par4 * drawScale),
                                      (double) (drawZ + par5 * drawScale + par7 * drawScale), (double) minU, (double) minV);
        worldrenderer.addVertexWithUV((double) (drawX + par3 * drawScale - par6 * drawScale), (double) (drawY - par4 * drawScale),
                                      (double) (drawZ + par5 * drawScale - par7 * drawScale), (double) minU, (double) maxV);

        Tessellator.getInstance().draw();
        GL11.glBlendFunc(770, 771);
        worldrenderer.startDrawingQuads();
    }

    public double[] getTargetPos() {
        if (target instanceof double[]) {
            return (double[]) target;
        } else if (target instanceof Entity) {
            Entity e = (Entity) target;
            return new double[]{ e.posX, e.posY + e.getEyeHeight(), e.posZ };
        } else {
            return null;
        }
    }

    public float alphaFunction(int age) {
        float ramp = particleMaxAge / 3F;
        return ((age + ramp) * (age + ramp) - (ramp * ramp)) / ((age + ramp) * (age + ramp));
    }
}
