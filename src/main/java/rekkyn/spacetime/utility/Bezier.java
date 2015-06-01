package rekkyn.spacetime.utility;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.Vec3;

public class Bezier {
    public Vec3 p0;
    public Vec3 p1;
    public Vec3 p2;
    public Vec3 p3;

    public float ti = 0f;

    private Vec3 b0 = new Vec3(0, 0, 0);
    private Vec3 b1 = new Vec3(0, 0, 0);
    private Vec3 b2 = new Vec3(0, 0, 0);
    private Vec3 b3 = new Vec3(0, 0, 0);

    private double Ax;
    private double Ay;
    private double Az;

    private double Bx;
    private double By;
    private double Bz;

    private double Cx;
    private double Cy;
    private double Cz;

    // Init function v0 = 1st point, v1 = handle of the 1st point , v2 = handle of the 2nd point, v3 = 2nd point
    // handle1 = v0 + v1
    // handle2 = v3 + v2
    public Bezier(Vec3 v0, Vec3 v1, Vec3 v2, Vec3 v3) {
        p0 = v0;
        p1 = v1;
        p2 = v2;
        p3 = v3;
    }

    // 0.0 >= t <= 1.0
    public Vec3 getPointAtTime(float t) {
        checkConstant();
        float t2 = t * t;
        float t3 = t * t * t;
        double x = Ax * t3 + Bx * t2 + Cx * t + p0.xCoord;
        double y = Ay * t3 + By * t2 + Cy * t + p0.yCoord;
        double z = Az * t3 + Bz * t2 + Cz * t + p0.zCoord;
        return new Vec3(x, y, z);
    }

    private void setConstant() {
        Cx = 3f * ((p0.xCoord + p1.xCoord) - p0.xCoord);
        Bx = 3f * ((p3.xCoord + p2.xCoord) - (p0.xCoord + p1.xCoord)) - Cx;
        Ax = p3.xCoord - p0.xCoord - Cx - Bx;

        Cy = 3f * ((p0.yCoord + p1.yCoord) - p0.yCoord);
        By = 3f * ((p3.yCoord + p2.yCoord) - (p0.yCoord + p1.yCoord)) - Cy;
        Ay = p3.yCoord - p0.yCoord - Cy - By;

        Cz = 3f * ((p0.zCoord + p1.zCoord) - p0.zCoord);
        Bz = 3f * ((p3.zCoord + p2.zCoord) - (p0.zCoord + p1.zCoord)) - Cz;
        Az = p3.zCoord - p0.zCoord - Cz - Bz;

    }

    // Check if p0, p1, p2 or p3 have changed
    private void checkConstant() {
        if (p0 != b0 || p1 != b1 || p2 != b2 || p3 != b3) {
            setConstant();
            b0 = p0;
            b1 = p1;
            b2 = p2;
            b3 = p3;
        }
    }

    public void writeToPacket(ByteBuf buf) {
        buf.writeDouble(p0.xCoord);
        buf.writeDouble(p0.yCoord);
        buf.writeDouble(p0.zCoord);
        buf.writeDouble(p1.xCoord);
        buf.writeDouble(p1.yCoord);
        buf.writeDouble(p1.zCoord);
        buf.writeDouble(p2.xCoord);
        buf.writeDouble(p2.yCoord);
        buf.writeDouble(p2.zCoord);
        buf.writeDouble(p3.xCoord);
        buf.writeDouble(p3.yCoord);
        buf.writeDouble(p3.zCoord);
    }

    public static Bezier readFromPacket(ByteBuf buf) {
        Vec3 v0 = new Vec3(buf.readDouble(), buf.readDouble(), buf.readDouble());
        Vec3 v1 = new Vec3(buf.readDouble(), buf.readDouble(), buf.readDouble());
        Vec3 v2 = new Vec3(buf.readDouble(), buf.readDouble(), buf.readDouble());
        Vec3 v3 = new Vec3(buf.readDouble(), buf.readDouble(), buf.readDouble());

        return new Bezier(v0, v1, v2, v3);
    }

    @Override
    public String toString() {
        return "p0: " + p0 + ", p1: " + p1 + ", p2: " + p2 + ", p3: " + p3;
    }
}
