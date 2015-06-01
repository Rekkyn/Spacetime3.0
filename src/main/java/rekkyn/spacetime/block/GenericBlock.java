package rekkyn.spacetime.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.common.registry.GameRegistry;
import rekkyn.spacetime.utility.CreativeTabSpacetime;

public class GenericBlock extends Block {

    public GenericBlock(Material material) {
        super(material);
        GameRegistry.registerBlock(this, getName());
        setUnlocalizedName(getName());
        setCreativeTab(CreativeTabSpacetime.SPACETIMETAB);
    }

    public GenericBlock() {
        this(Material.rock);
    }

    public String getName() {
        return "";
    }
}