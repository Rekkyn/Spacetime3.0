package rekkyn.spacetime.item;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import rekkyn.spacetime.entity.SpacetimeFluctuationEntity;

public class SpacetimeFluctuationItem extends GenericItem {

    public SpacetimeFluctuationItem() {
        super();
        setMaxStackSize(1);
    }

    @Override
    public String getName() {
        return "spacetimeFluctuationItem";
    }

    @Override
    public Entity createEntity(World world, Entity location, ItemStack itemstack) {
        SpacetimeFluctuationEntity entity = new SpacetimeFluctuationEntity(world, location.posX, location.posY, location.posZ);
        entity.motionX = location.motionX;
        entity.motionY = location.motionY;
        entity.motionZ = location.motionZ;
        entity.delayBeforeCanPickup = 20;

        return entity;
    }

    @Override
    public boolean hasCustomEntity(ItemStack stack) {
        return true;
    }
}
