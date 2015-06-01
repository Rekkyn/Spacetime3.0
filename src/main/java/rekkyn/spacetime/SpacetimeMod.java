package rekkyn.spacetime;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
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
        ModBlocks.init();
        network = NetworkRegistry.INSTANCE.newSimpleChannel("Spacetime");
        Network.init(network);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        ModItems.registerItemIcons(event);
        ModBlocks.registerBlockIcons(event);
        proxy.register();
        GameRegistry.registerWorldGenerator(new SpacetimeWorldGen(), 1);
        EntityRegistry.registerModEntity(SpacetimeFluctuationEntity.class, "spacetimeFluctuation", 0, this, 128, 1, true);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {

    }
}
