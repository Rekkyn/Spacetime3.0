package rekkyn.spacetime.network;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import rekkyn.spacetime.particle.ParticleEffects;
import rekkyn.spacetime.utility.Bezier;

public class ParticlePacket implements IMessage {

    ParticleEffects.ParticleTypes type;
    double x, y, z, motionX, motionY, motionZ;
    Object data;

    public static final int ENTITY = 0;
    public static final int COORDS = 1;
    public static final int BEZIER = 2;


    public ParticlePacket() {}

    public ParticlePacket(ParticleEffects.ParticleTypes type, double x, double y, double z, double motionX, double motionY, double motionZ,
                          Object data) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.z = z;
        this.motionX = motionX;
        this.motionY = motionY;
        this.motionZ = motionZ;
        this.data = data;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        type = ParticleEffects.ParticleTypes.valueOf(ByteBufUtils.readUTF8String(buf));
        x = buf.readDouble();
        y = buf.readDouble();
        z = buf.readDouble();
        motionX = buf.readDouble();
        motionY = buf.readDouble();
        motionZ = buf.readDouble();
        int type = buf.readInt();
        if (type == ENTITY) {
            data = Minecraft.getMinecraft().theWorld.getEntityByID(buf.readInt());
        } else if (type == COORDS) {
            data = new double[]{ buf.readDouble(), buf.readDouble(), buf.readDouble() };
        } else if (type == BEZIER) {
            data = Bezier.readFromPacket(buf);
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, type.toString());
        buf.writeDouble(x);
        buf.writeDouble(y);
        buf.writeDouble(z);
        buf.writeDouble(motionX);
        buf.writeDouble(motionY);
        buf.writeDouble(motionZ);
        if (data instanceof Entity) {
            buf.writeInt(ENTITY);
            buf.writeInt(((Entity) data).getEntityId());
        } else if (data instanceof double[] && ((double[]) data).length == 3) {
            double[] coords = (double[]) data;
            buf.writeInt(COORDS);
            buf.writeDouble(coords[0]);
            buf.writeDouble(coords[1]);
            buf.writeDouble(coords[2]);
        } else if (data instanceof Bezier) {
            buf.writeInt(BEZIER);
            ((Bezier) data).writeToPacket(buf);
        }
    }

    public static class Handler implements IMessageHandler<ParticlePacket, IMessage> {

        @Override
        public IMessage onMessage(ParticlePacket message, MessageContext ctx) {
            ParticleEffects.spawnParticle(message.type, message.x, message.y, message.z, message.motionX, message.motionY, message.motionZ,
                                          message.data);
            return null; // no response in this case
        }
    }
}
