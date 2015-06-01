package rekkyn.spacetime.init;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import rekkyn.spacetime.item.GenericItem;
import rekkyn.spacetime.item.QuantumRay;
import rekkyn.spacetime.item.SpacetimeFluctuationItem;
import rekkyn.spacetime.reference.Reference;

public class ModItems {

    public static final GenericItem spacetimeFluctuationItem = new SpacetimeFluctuationItem();
    public static final GenericItem quantumRay = new QuantumRay();

    public static final void registerItemIcons(FMLInitializationEvent event) {
        if (event.getSide() == Side.CLIENT) {
            registerIcon(quantumRay);
            registerIcon(spacetimeFluctuationItem);
        }
    }

    private static void registerIcon(GenericItem item) {
        RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
        renderItem.getItemModelMesher().register(item, 0, new ModelResourceLocation(Reference.MOD_ID + ":" + item.getName(), "inventory"));
    }
}
