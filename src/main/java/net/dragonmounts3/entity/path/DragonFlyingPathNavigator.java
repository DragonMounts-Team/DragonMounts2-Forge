package net.dragonmounts3.entity.path;

import net.dragonmounts3.entity.dragon.TameableDragonEntity;
import net.minecraft.pathfinding.PathFinder;
import net.minecraft.pathfinding.SwimmerPathNavigator;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

/**
 * Based on {@link SwimmerPathNavigator} but for air blocks.
 *
 * @author Nico Bergemann <barracuda415 at yahoo.de>
 */
public class DragonFlyingPathNavigator extends SwimmerPathNavigator {
    public DragonFlyingPathNavigator(TameableDragonEntity dragon, World level) {
        super(dragon, level);
    }

    @Nonnull
    @Override
    protected PathFinder createPathFinder(int maxVisitedNodes) {
        return new PathFinder(new DragonFlyingNodeProcessor(), maxVisitedNodes);
    }

    @Override
    protected boolean canUpdatePath() {
        return true;
    }
}
