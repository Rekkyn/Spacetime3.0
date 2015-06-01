package rekkyn.spacetime.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import rekkyn.spacetime.init.ModItems;

public class SpacetimeFluctuationEntity extends Entity {

    public int delayBeforeCanPickup;

    public SpacetimeFluctuationEntity(World world, double x, double y, double z) {
        super(world);
        setSize(0.5F, 0.5F);
        yOffset = height / 2.0F;
        setPosition(x, y, z);
    }

    public SpacetimeFluctuationEntity(World world) {
        super(world);
        setSize(0.5F, 0.5F);
        yOffset = height / 2.0F;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        if (delayBeforeCanPickup > 0) {
            --delayBeforeCanPickup;
        }

        double slow = 0.95;
        motionX *= slow;
        motionY *= slow;
        motionZ *= slow;
        moveEntity(motionX, motionY, motionZ);
    }

    public void onCollideWithPlayer(EntityPlayer player) {
        if (!worldObj.isRemote) {
            if (delayBeforeCanPickup > 0) {
                return;
            }

            if (player.inventory.addItemStackToInventory(new ItemStack(ModItems.spacetimeFluctuationItem))) {
                worldObj.playSoundAtEntity(player, "random.pop", 0.2F, ((rand.nextFloat() - rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
                player.onItemPickup(this, 1);
                setDead();
            }
        }
    }

    @Override
    protected void entityInit() {

    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound p_70037_1_) {

    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound p_70014_1_) {

    }
}
