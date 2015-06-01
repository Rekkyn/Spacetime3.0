package rekkyn.spacetime.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import rekkyn.spacetime.reference.Reference;
import rekkyn.spacetime.utility.CreativeTabSpacetime;

public class GenericBlock extends Block {

    public GenericBlock(Material material) {
        super(material);
        setCreativeTab(CreativeTabSpacetime.SPACETIMETAB);
    }

    public GenericBlock() {
        super(Material.rock);
        setCreativeTab(CreativeTabSpacetime.SPACETIMETAB);
    }

    @Override
    public String getUnlocalizedName() {
        return String.format("tile.%s%s", Reference.MOD_ID.toLowerCase() + ":", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        blockIcon = iconRegister.registerIcon(String.format("%s", getUnwrappedUnlocalizedName(this.getUnlocalizedName())));
    }

    protected String getUnwrappedUnlocalizedName(String unlocalizedName) {
        return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
    }
}