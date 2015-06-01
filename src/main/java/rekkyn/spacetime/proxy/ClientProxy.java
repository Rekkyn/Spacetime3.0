package rekkyn.spacetime.proxy;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import rekkyn.spacetime.client.RenderSpacetimeFluctuation;
import rekkyn.spacetime.client.RenderSpacetimeFluctuationEntity;
import rekkyn.spacetime.entity.SpacetimeFluctuationEntity;
import rekkyn.spacetime.tileentity.TileSpacetimeFluctuation;

public class ClientProxy extends CommonProxy implements IProxy {
    @Override
    public void register() {
        ClientRegistry.bindTileEntitySpecialRenderer(TileSpacetimeFluctuation.class, new RenderSpacetimeFluctuation());
        RenderingRegistry.registerEntityRenderingHandler(SpacetimeFluctuationEntity.class, new RenderSpacetimeFluctuationEntity());
    }
}
