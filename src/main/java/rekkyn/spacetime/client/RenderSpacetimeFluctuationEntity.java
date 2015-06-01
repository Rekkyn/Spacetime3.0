package rekkyn.spacetime.client;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderSpacetimeFluctuationEntity extends Render {
    public RenderSpacetimeFluctuationEntity(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public void doRender(Entity entity, double x, double y, double z, float p_76986_8_, float partialTicks) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x, (float) y, (float) z);
        GL11.glScalef(0.2F, 0.2F, 0.2F);
        bindTexture(new ResourceLocation("spacetime", "textures/blocks/vortex.png"));
        RenderSFHelper.renderVortex(entity.worldObj, partialTicks);
        RenderSFHelper.renderBeams(entity.worldObj, partialTicks);
        GL11.glPopMatrix();
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
        return null;
    }
}
