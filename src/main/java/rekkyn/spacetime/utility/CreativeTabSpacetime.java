package rekkyn.spacetime.utility;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import rekkyn.spacetime.init.ModItems;
import rekkyn.spacetime.reference.Reference;

public class CreativeTabSpacetime {

    public static final CreativeTabs SPACETIMETAB = new CreativeTabs(Reference.MOD_ID.toLowerCase()) {
        @Override
        public Item getTabIconItem() {
            return ModItems.spacetimeFluctuationItem;
        }
    };
}
