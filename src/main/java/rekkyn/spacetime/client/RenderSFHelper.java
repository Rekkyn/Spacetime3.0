package rekkyn.spacetime.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL12;

import java.util.Random;

import static org.lwjgl.opengl.GL11.*;

public class RenderSFHelper {

    public static void renderVortex(World world, float partialTicks) {
        RenderHelper.disableStandardItemLighting();
        glShadeModel(GL_SMOOTH);
        glEnable(GL_BLEND);
        glBlendFunc(770, 1);
        glDisable(GL_ALPHA_TEST);
        glDisable(GL_CULL_FACE);
        glDepthMask(false);

        glPushMatrix();
        glRotatef(180.0F - Minecraft.getMinecraft().getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
        glRotatef(-Minecraft.getMinecraft().getRenderManager().playerViewX, 1.0F, 0.0F, 0.0F);

        float time = world.getTotalWorldTime() + partialTicks;

        glRotatef(time * 2, 0.0F, 0.0F, 1.0F);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.startDrawingQuads();
        worldrenderer.setNormal(0.0F, 1.0F, 0.0F);
        //worldrenderer.setBrightness(240);
        float size = 3F;
        worldrenderer.addVertexWithUV(-size, size, 0.0D, 0, 1);
        worldrenderer.addVertexWithUV(size, size, 0.0D, 1, 1);
        worldrenderer.addVertexWithUV(size, -size, 0.0D, 1, 0);
        worldrenderer.addVertexWithUV(-size, -size, 0.0D, 0, 0);
        tessellator.draw();
        glDisable(GL12.GL_RESCALE_NORMAL);
        glPopMatrix();

        glDepthMask(true);
        glDisable(GL_BLEND);
        glShadeModel(GL_FLAT);
        glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        glEnable(GL_ALPHA_TEST);
        RenderHelper.enableStandardItemLighting();
    }

    public static void renderBeams(World world, float partialTicks) {
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();

        float time = world.getTotalWorldTime() + partialTicks;

        if (time > 0) {
            RenderHelper.disableStandardItemLighting();
            float f1 = time / 200.0F;

            Random random = new Random(432L);
            glDisable(GL_TEXTURE_2D);
            glShadeModel(GL_SMOOTH);
            glEnable(GL_BLEND);
            glBlendFunc(GL_SRC_ALPHA, GL_ONE);
            glDisable(GL_ALPHA_TEST);
            glEnable(GL_CULL_FACE);
            glDepthMask(false);
            glPushMatrix();

            for (int i = 0; (float) i < 15; ++i) {
                glRotatef(random.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
                glRotatef(random.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
                glRotatef(random.nextFloat() * 360.0F, 0.0F, 0.0F, 1.0F);
                glRotatef(random.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
                glRotatef(random.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
                glRotatef(random.nextFloat() * 360.0F + f1 * 90.0F, 0.0F, 0.0F, 1.0F);
                worldrenderer.startDrawing(6);
                float f3 = random.nextFloat() * 10.0F + 5.0F;
                float f4 = random.nextFloat() * 2.0F + 2.0F;
                //float a = entity.damage / 100.0F;
                worldrenderer.setColorRGBA_I(13158655, 64);//(int) (127 * a));
                worldrenderer.addVertex(0.0D, 0.0D, 0.0D);
                worldrenderer.setColorRGBA_I(255, 0);
                worldrenderer.addVertex(-0.866D * f4, f3, -0.5F * f4);
                worldrenderer.addVertex(0.866D * f4, f3, -0.5F * f4);
                worldrenderer.addVertex(0.0D, f3, 1.0F * f4);
                tessellator.draw();
            }

            glPopMatrix();
            glDepthMask(true);
            glDisable(GL_CULL_FACE);
            glDisable(GL_BLEND);
            glShadeModel(GL_FLAT);
            glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            glEnable(GL_TEXTURE_2D);
            glEnable(GL_ALPHA_TEST);
            RenderHelper.enableStandardItemLighting();
        }
    }
}
