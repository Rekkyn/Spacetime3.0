package rekkyn.spacetime.particle;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;
import rekkyn.spacetime.reference.Reference;
import rekkyn.spacetime.utility.Bezier;
import rekkyn.spacetime.utility.MathUtil;

public class BezierParticle extends EntityFX {

    Bezier bezier;
    EntityLivingBase target;

    public BezierParticle(World world, double x, double y, double z, Object data) {
        super(world, x, y, z);
        if (data instanceof Bezier) bezier = (Bezier) data;
        else if (data instanceof EntityLivingBase) target = (EntityLivingBase) data;
        //particleScale = 0.5F;
        float variance = 0.07F;
        particleRed = (float) (0.619607843F + MathUtil.NegOneToOne() * variance);
        particleGreen = (float) (0.745098039F + MathUtil.NegOneToOne() * variance);
        particleBlue = (float) (0.901960784F + MathUtil.NegOneToOne() * variance);
        setParticleTextureIndex(65);
    }

    @Override
    public void onUpdate() {
        prevPosX = posX;
        prevPosY = posY;
        prevPosZ = posZ;

        if (particleAge++ >= particleMaxAge) {
            setDead();
        }

        if (bezier == null && target != null) {
            bezier = new Bezier(Vec3.createVectorHelper(posX, posY, posZ), Vec3.createVectorHelper(0, 0, 0), Vec3.createVectorHelper(4, 0, 0), Vec3.createVectorHelper(target.posX, target.posY, target.posZ));
        }

        if (target != null) {
            bezier.p3 = Vec3.createVectorHelper(target.posX, target.posY + target.getEyeHeight(), target.posZ);
            float f = 7F;
            double x = (double)(-MathHelper.sin(target.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(target.rotationPitch / 180.0F * (float)Math.PI) * f);
            double z = (double)(MathHelper.cos(target.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(target.rotationPitch / 180.0F * (float)Math.PI) * f);
            double y = (double)(-MathHelper.sin(target.rotationPitch / 180.0F * (float)Math.PI) * f);
            bezier.p2 = Vec3.createVectorHelper(x, y, z);
        }

        if (bezier != null) {
            Vec3 pos = bezier.getPointAtTime((float) particleAge / (float) particleMaxAge);
            posX = pos.xCoord;
            posY = pos.yCoord;
            posZ = pos.zCoord;
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderParticle(Tessellator tessellator, float par2, float par3, float par4, float par5, float par6, float par7) {

        tessellator.draw();
        Minecraft.getMinecraft().getTextureManager()
                 .bindTexture(new ResourceLocation(Reference.MOD_ID.toLowerCase() + ":textures/particles/particles.png"));
        tessellator.startDrawingQuads();
        tessellator.setBrightness(240);
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

        tessellator.setColorRGBA_F(particleRed, particleGreen, particleBlue, particleAlpha);

        tessellator.addVertexWithUV((double) (drawX - par3 * drawScale - par6 * drawScale), (double) (drawY - par4 * drawScale),
                                    (double) (drawZ - par5 * drawScale - par7 * drawScale), (double) maxU, (double) maxV);
        tessellator.addVertexWithUV((double) (drawX - par3 * drawScale + par6 * drawScale), (double) (drawY + par4 * drawScale),
                                    (double) (drawZ - par5 * drawScale + par7 * drawScale), (double) maxU, (double) minV);
        tessellator.addVertexWithUV((double) (drawX + par3 * drawScale + par6 * drawScale), (double) (drawY + par4 * drawScale),
                                    (double) (drawZ + par5 * drawScale + par7 * drawScale), (double) minU, (double) minV);
        tessellator.addVertexWithUV((double) (drawX + par3 * drawScale - par6 * drawScale), (double) (drawY - par4 * drawScale),
                                    (double) (drawZ + par5 * drawScale - par7 * drawScale), (double) minU, (double) maxV);

        tessellator.draw();
        Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("textures/particle/particles.png"));
        tessellator.startDrawingQuads();
    }

}
