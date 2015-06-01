package rekkyn.spacetime.item;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
import rekkyn.spacetime.utility.CreativeTabSpacetime;

public class GenericItem extends Item {

    public GenericItem() {
        super();
        GameRegistry.registerItem(this, getName());
        setUnlocalizedName(getName());
        setCreativeTab(CreativeTabSpacetime.SPACETIMETAB);
    }

    public String getName() {
        return "";
    }
}
