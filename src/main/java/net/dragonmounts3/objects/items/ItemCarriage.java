package net.dragonmounts3.objects.items;

import net.dragonmounts3.objects.entity.carriage.CarriageType;
import net.dragonmounts3.objects.entity.carriage.EntityCarriage;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.function.Predicate;

public class ItemCarriage extends Item {
    private static final Predicate<Entity> ENTITY_PREDICATE = EntityPredicates.NO_SPECTATORS.and(Entity::isPickable);
    protected CarriageType type;

    public ItemCarriage(CarriageType type, Properties properties) {
        super(properties);
        this.type = type;
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> use(@Nonnull World level, PlayerEntity player, @Nonnull Hand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        RayTraceResult rayTraceResult = getPlayerPOVHitResult(level, player, RayTraceContext.FluidMode.NONE);
        if (rayTraceResult.getType() == RayTraceResult.Type.MISS) {
            return ActionResult.pass(itemstack);
        } else {
            Vector3d vector3d = player.getViewVector(1.0F);
            List<Entity> list = level.getEntities(player, player.getBoundingBox().expandTowards(vector3d.scale(5.0D)).inflate(1.0D), ENTITY_PREDICATE);
            if (!list.isEmpty()) {
                Vector3d vector3d1 = player.getEyePosition(1.0F);
                for (Entity entity : list) {
                    AxisAlignedBB axisalignedbb = entity.getBoundingBox().inflate(entity.getPickRadius());
                    if (axisalignedbb.contains(vector3d1)) {
                        return ActionResult.pass(itemstack);
                    }
                }
            }
            if (rayTraceResult.getType() == RayTraceResult.Type.BLOCK) {
                EntityCarriage entity = new EntityCarriage(level, rayTraceResult.getLocation().x, rayTraceResult.getLocation().y, rayTraceResult.getLocation().z);
                entity.setCarriageType(this.type);
                entity.yRot = player.yRot;
                if (!level.noCollision(entity, entity.getBoundingBox().inflate(-0.1D))) {
                    return ActionResult.fail(itemstack);
                } else {
                    if (!level.isClientSide) {
                        level.addFreshEntity(entity);
                        if (!player.abilities.instabuild) {
                            itemstack.shrink(1);
                        }
                    }
                    player.awardStat(Stats.ITEM_USED.get(this));
                    return ActionResult.sidedSuccess(itemstack, level.isClientSide());
                }
            } else {
                return ActionResult.pass(itemstack);
            }
        }
    }
}
