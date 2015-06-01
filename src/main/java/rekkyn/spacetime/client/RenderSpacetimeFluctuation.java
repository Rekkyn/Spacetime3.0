package rekkyn.spacetime.client;


import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;


@SideOnly(Side.CLIENT)
public class RenderSpacetimeFluctuation extends TileEntitySpecialRenderer {

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float partialTicks, int blockDamageProgress) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y + 0.5F, (float) z + 0.5F);
        bindTexture(new ResourceLocation("spacetime:textures/blocks/vortex.png"));
        RenderSFHelper.renderVortex(tileEntity.getWorld(), partialTicks);
        RenderSFHelper.renderBeams(tileEntity.getWorld(), partialTicks);
        GL11.glPopMatrix();
    }
}