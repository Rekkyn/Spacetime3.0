package rekkyn.spacetime.init;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import rekkyn.spacetime.block.BezierTestBlock;
import rekkyn.spacetime.block.GenericBlock;
import rekkyn.spacetime.block.SpacetimeFluctuation;
import rekkyn.spacetime.reference.Reference;
import rekkyn.spacetime.tileentity.TileBezierTest;
import rekkyn.spacetime.tileentity.TileSpacetimeFluctuation;

public class ModBlocks {

    public static final SpacetimeFluctuation spacetimeFluctuation = new SpacetimeFluctuation();
    public static final BezierTestBlock bezierTestBlock = new BezierTestBlock();

    public static void init() {
        GameRegistry.registerTileEntity(TileSpacetimeFluctuation.class, "spacetimeFluctuation");
        GameRegistry.registerTileEntity(TileBezierTest.class, "bezierTestBlock");
    }

    public static final void registerBlockIcons(FMLInitializationEvent event) {
        if (event.getSide() == Side.CLIENT) {
            registerBlock(spacetimeFluctuation);
            registerBlock(bezierTestBlock);
        }
    }

    private static void registerBlock(GenericBlock block) {
        RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
        renderItem.getItemModelMesher().register(Item.getItemFromBlock(block), 0,
                                                 new ModelResourceLocation(Reference.MOD_ID + ":" + block.getName(), "inventory"));
    }
}
