package rekkyn.spacetime.init;

import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import rekkyn.spacetime.network.ParticlePacket;

public class Network {

    public static void init(SimpleNetworkWrapper network) {
        int i = 0;
        network.registerMessage(ParticlePacket.Handler.class, ParticlePacket.class, i++, Side.CLIENT);
    }
}
