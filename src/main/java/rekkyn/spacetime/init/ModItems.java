package rekkyn.spacetime.init;

import cpw.mods.fml.common.registry.GameRegistry;
import rekkyn.spacetime.item.GenericItem;
import rekkyn.spacetime.item.QuantumRay;
import rekkyn.spacetime.item.SpacetimeFluctuationItem;
import rekkyn.spacetime.reference.Reference;

@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class ModItems {

    public static final GenericItem spacetimeFluctuationItem = new SpacetimeFluctuationItem();
    public static final GenericItem quantumRay = new QuantumRay();

    public static void init() {
        GameRegistry.registerItem(spacetimeFluctuationItem, "spacetimeFluctuationItem");
        GameRegistry.registerItem(quantumRay, "quantumRay");
    }
}
