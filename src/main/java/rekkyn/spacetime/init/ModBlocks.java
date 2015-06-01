package rekkyn.spacetime.init;

import cpw.mods.fml.common.registry.GameRegistry;
import rekkyn.spacetime.block.BezierTestBlock;
import rekkyn.spacetime.block.GenericBlock;
import rekkyn.spacetime.block.SpacetimeFluctuation;
import rekkyn.spacetime.reference.Reference;
import rekkyn.spacetime.tileentity.TileBezierTest;
import rekkyn.spacetime.tileentity.TileSpacetimeFluctuation;

@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class ModBlocks {

    public static final SpacetimeFluctuation spacetimeFluctuation = new SpacetimeFluctuation();
    public static final BezierTestBlock bezierTestBlock = new BezierTestBlock();

    public static void init() {
        GameRegistry.registerBlock(spacetimeFluctuation, "spacetimeFluctuation");
        GameRegistry.registerTileEntity(TileSpacetimeFluctuation.class, "spacetimeFluctuation");
        GameRegistry.registerBlock(bezierTestBlock, "bezierTestBlock");
        GameRegistry.registerTileEntity(TileBezierTest.class, "bezierTestBlock");
    }
}
