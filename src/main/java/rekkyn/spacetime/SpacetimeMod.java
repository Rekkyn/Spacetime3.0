package rekkyn.spacetime;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import rekkyn.spacetime.entity.SpacetimeFluctuationEntity;
import rekkyn.spacetime.init.ModBlocks;
import rekkyn.spacetime.init.ModItems;
import rekkyn.spacetime.init.Network;
import rekkyn.spacetime.proxy.IProxy;
import rekkyn.spacetime.reference.Reference;
import rekkyn.spacetime.world.SpacetimeWorldGen;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_ID, version = Reference.VERSION)
public class SpacetimeMod {

    @Mod.Instance(Reference.MOD_ID)
    public static SpacetimeMod instance;

    public static SimpleNetworkWrapper network;

    @SidedProxy(clientSide = "rekkyn.spacetime.proxy.ClientProxy", serverSide = "rekkyn.spacetime.proxy.ServerProxy")
    public static IProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ModItems.init();
        ModBlocks.init();
        network = NetworkRegistry.INSTANCE.newSimpleChannel("Spacetime");
        Network.init(network);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.register();
        GameRegistry.registerWorldGenerator(new SpacetimeWorldGen(), 1);
        EntityRegistry.registerModEntity(SpacetimeFluctuationEntity.class, "spacetimeFluctuation", 0, this, 128, 1, true);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {

    }
}
