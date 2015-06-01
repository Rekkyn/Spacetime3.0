package rekkyn.spacetime.block;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import rekkyn.spacetime.tileentity.TileBezierTest;

public class BezierTestBlock extends GenericBlock implements ITileEntityProvider {

    public BezierTestBlock() {
        super(Material.rock);
    }

    public String getName() {
        return "bezierTestBlock";
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileBezierTest();
    }
}
