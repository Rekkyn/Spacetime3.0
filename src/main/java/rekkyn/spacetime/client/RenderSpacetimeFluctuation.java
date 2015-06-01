package rekkyn.spacetime.client;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;


@SideOnly(Side.CLIENT)
public class RenderSpacetimeFluctuation extends TileEntitySpecialRenderer {
    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float partialTicks) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y + 0.5F, (float) z + 0.5F);
        bindTexture(new ResourceLocation("spacetime", "textures/blocks/vortex.png"));
        RenderSFHelper.renderVortex(tileEntity.getWorldObj(), partialTicks);
        RenderSFHelper.renderBeams(tileEntity.getWorldObj(), partialTicks);
        GL11.glPopMatrix();
    }
}