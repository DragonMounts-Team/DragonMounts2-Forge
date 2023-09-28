package net.dragonmounts3.api;

import net.dragonmounts3.registry.DragonType;

public interface IMutableDragonTypified extends IDragonTypified {
    void setDragonType(DragonType type);
}
